package com.fastproject.usergrowth.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.file.api.FileHandle;
import com.fastproject.file.vo.FileUrlVo;
import com.fastproject.jedis.JedisTemplate;
import com.fastproject.usergrowth.domain.UserLevelAccount;
import com.fastproject.usergrowth.mapper.UserLevelAccountMapper;
import com.fastproject.usergrowth.repository.db.UserLevelAccountRepository;
import com.fastproject.usergrowth.service.UserLevelAccountService;
import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountCreate;
import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountQuery;
import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountUpdate;
import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountVo;
import com.fastproject.utils.enums.StatusEnum;
import com.fastproject.utils.vo.PageVo;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserLevelAccountServiceImpl implements UserLevelAccountService {

    private final UserLevelAccountRepository repository;
    private final QueryHelp<UserLevelAccount> queryHelp;
    private final UserLevelAccountMapper mapper;
    private final JedisTemplate jedisTemplate;
    private final FileHandle fileHandle;

    @Override
    public Long save(UserLevelAccountCreate create) {
        log.info("保存信息：{}", create);
        UserLevelAccount entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserLevelAccountUpdate update) {
        log.info("修改信息：{}", update);
        UserLevelAccount entity = repository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("数据不存在"));
        mapper.updateFromDto(update, entity);
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        log.info("删除信息：{}", id);
        repository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除信息：{}", ids);
        repository.deleteAllById(ids);
    }

    @Override
    public UserLevelAccountVo findById(Long id) {
        log.info("根据ID查询信息：{}", id);
        return repository.findById(id)
                .map(mapper::toVo)
                .map(this::fillFileUrls) // 抽方法
                .orElse(null);
    }


    @Override
    public PageVo<List<UserLevelAccountVo>> findPage(UserLevelAccountQuery query) {
        log.info("分页查询信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<UserLevelAccount> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), query.getUserId()));
            }
            if (query.getGrowthValue() != null) {
                predicates.add(cb.equal(root.get("growthValue"), query.getGrowthValue()));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            if (org.springframework.util.StringUtils.hasText(query.getAvatar())) {
                predicates.add(cb.like(root.get("avatar"), "%" + query.getAvatar() + "%"));
            }
            if (org.springframework.util.StringUtils.hasText(query.getAvatarFrame())) {
                predicates.add(cb.like(root.get("avatarFrame"), "%" + query.getAvatarFrame() + "%"));
            }
            return predicates;
        });

        Page<UserLevelAccount> page = repository.findAll(spec, pageable);
        List<UserLevelAccountVo> list = mapper.toVo(page.getContent());
        fillFileUrls(list);// 调用文件系统
        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public UserLevelAccountVo getAccountByUserId(Long userId) {

        UserLevelAccount byUserId = repository.findByUserId(userId);
        if (byUserId!=null) {
            return this.fillFileUrls(mapper.toVo(byUserId));
        }
        // 加入成长值账户的锁 
        String lockKey = "lock:user_level_account:" + userId;
        String requestId = java.util.UUID.randomUUID().toString();

        int retryTimes = 3;
        while (retryTimes > 0) {
            boolean locked = jedisTemplate.tryGetDistributedLock(lockKey, requestId, 5000);
            if (locked) {
                try {
                    // 双重检查，防止在等待获取锁期间其他线程已经创建
                    byUserId = repository.findByUserId(userId);
                    if (byUserId != null) {
                        return this.fillFileUrls(mapper.toVo(byUserId));
                    }
                    UserLevelAccount account = new UserLevelAccount();
                    account.setUserId(userId);
                    account.setStatus(StatusEnum.NORMAL.getCode());
                    account.setGrowthValue(0L);
                    repository.save(account);
                    
                    return mapper.toVo(account);
                } finally {
                    jedisTemplate.releaseDistributedLock(lockKey, requestId);
                }
            } else {
                // 如果没有获取到锁，等待 200 毫秒后再次尝试
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new BusinessException("系统繁忙，请稍后再试");
                }

                // 等待后再次查询，可能其他线程已经创建成功
                byUserId = repository.findByUserId(userId);
                if (byUserId != null) {
                    return this.fillFileUrls(mapper.toVo(byUserId));
                }
                retryTimes--;
            }
        }
        throw new BusinessException("系统繁忙，请稍后再试");
    }

    /**
     * 文件装配
     * @param vo 数据
     * @return 结果
     */
    private UserLevelAccountVo fillFileUrls(UserLevelAccountVo vo) {
        if (vo == null) {
            return null;
        }
        String avatar = vo.getAvatar();
        String avatarFrame = vo.getAvatarFrame();
        Set<String> ids = Stream.of(avatar, avatarFrame)
                .filter(StringUtils::hasText)
                .collect(Collectors.toSet());
        if (ids.isEmpty()) {
            return vo;
        }
        Map<String, String> urlMap = fileHandle.getUrls(ids).stream()
                .collect(Collectors.toMap(FileUrlVo::getId, FileUrlVo::getUrl));
        vo.setAvatar(urlMap.getOrDefault(avatar, avatar));
        vo.setAvatarFrame(urlMap.getOrDefault(avatarFrame, avatarFrame));

        return vo;
    }
    private void fillFileUrls(List<UserLevelAccountVo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        Set<String> ids = list.stream()
                .flatMap(vo -> Stream.of(vo.getAvatar(), vo.getAvatarFrame()))
                .filter(StringUtils::hasText)
                .collect(Collectors.toSet());

        if (ids.isEmpty()) {
            return;
        }

        Map<String, String> urlMap = fileHandle.getUrls(ids).stream()
                .collect(Collectors.toMap(FileUrlVo::getId, FileUrlVo::getUrl, (a, b) -> a));

        list.forEach(vo -> {
            vo.setAvatar(urlMap.getOrDefault(vo.getAvatar(), vo.getAvatar()));
            vo.setAvatarFrame(urlMap.getOrDefault(vo.getAvatarFrame(), vo.getAvatarFrame()));
        });
    }
}
