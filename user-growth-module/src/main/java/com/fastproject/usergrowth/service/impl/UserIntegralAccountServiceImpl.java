package com.fastproject.usergrowth.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.jedis.JedisTemplate;
import com.fastproject.usergrowth.domain.UserIntegralAccount;
import com.fastproject.usergrowth.mapper.UserIntegralAccountMapper;
import com.fastproject.usergrowth.repository.db.UserIntegralAccountRepository;
import com.fastproject.usergrowth.service.UserIntegralAccountService;
import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountCreate;
import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountQuery;
import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountUpdate;
import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.enums.StatusEnum;
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

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserIntegralAccountServiceImpl implements UserIntegralAccountService {

    private final UserIntegralAccountRepository repository;
    private final QueryHelp<UserIntegralAccount> queryHelp;
    private final UserIntegralAccountMapper mapper;
    private final JedisTemplate jedisTemplate;

    @Override
    public Long save(UserIntegralAccountCreate create) {
        log.info("保存信息：{}", create);
        UserIntegralAccount entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserIntegralAccountUpdate update) {
        log.info("修改信息：{}", update);
        UserIntegralAccount entity = repository.findById(update.getId())
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
    public UserIntegralAccountVo findById(Long id) {
        log.info("根据ID查询信息：{}", id);
        return repository.findById(id).map(mapper::toVo).orElse(null);
    }

    @Override
    public PageVo<List<UserIntegralAccountVo>> findPage(UserIntegralAccountQuery query) {
        log.info("分页查询信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<UserIntegralAccount> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), query.getUserId()));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            return predicates;
        });

        Page<UserIntegralAccount> page = repository.findAll(spec, pageable);
        List<UserIntegralAccountVo> list = mapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public UserIntegralAccountVo getAccountByUserId(Long userId) {
        UserIntegralAccount byUserId = repository.findByUserId(userId);
        if (byUserId != null) {
            return mapper.toVo(byUserId);
        }

        // 加入积分账户的锁
        String lockKey = "lock:user_integral_account:" + userId;
        String requestId = java.util.UUID.randomUUID().toString();

        int retryTimes = 3;
        while (retryTimes > 0) {
            boolean locked = jedisTemplate.tryGetDistributedLock(lockKey, requestId, 5000);
            if (locked) {
                try {
                    // 双重检查
                    byUserId = repository.findByUserId(userId);
                    if (byUserId != null) {
                        return mapper.toVo(byUserId);
                    }

                    UserIntegralAccount account = new UserIntegralAccount();
                    account.setUserId(userId);
                    account.setStatus(StatusEnum.NORMAL.getCode());
                    account.setIntegral(0L);
                    repository.save(account);

                    return mapper.toVo(account);
                } finally {
                    jedisTemplate.releaseDistributedLock(lockKey, requestId);
                }
            } else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new BusinessException("系统繁忙，请稍后再试");
                }
                byUserId = repository.findByUserId(userId);
                if (byUserId != null) {
                    return mapper.toVo(byUserId);
                }
                retryTimes--;
            }
        }

        throw new BusinessException("系统繁忙，请稍后再试");
    }
}
