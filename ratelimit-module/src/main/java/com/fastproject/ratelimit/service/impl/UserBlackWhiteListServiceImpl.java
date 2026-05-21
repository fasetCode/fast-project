package com.fastproject.ratelimit.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.ratelimit.domain.UserBlackWhiteList;
import com.fastproject.ratelimit.mapper.UserBlackWhiteListMapper;
import com.fastproject.ratelimit.repository.db.UserBlackWhiteListRepository;
import com.fastproject.ratelimit.service.UserBlackWhiteListService;
import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListCreate;
import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListQuery;
import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListUpdate;
import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListVo;
import com.fastproject.utils.vo.PageVo;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户黑白名单配置 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserBlackWhiteListServiceImpl implements UserBlackWhiteListService {

    private final UserBlackWhiteListRepository userBlackWhiteListRepository;
    private final QueryHelp<UserBlackWhiteList> queryHelp;
    private final UserBlackWhiteListMapper userBlackWhiteListMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(UserBlackWhiteListCreate create) {
        log.info("保存用户黑白名单配置信息：{}", create);

        if (create.getUserId() == null) {
            throw new BusinessException("用户ID不能为空");
        }

        UserBlackWhiteList existing = userBlackWhiteListRepository.findByAppCodeAndUserIdAndListType(
                create.getAppCode(), create.getUserId(), create.getListType());
        if (existing != null) {
            throw new BusinessException("该应用的此用户名单配置已存在");
        }

        UserBlackWhiteList config = userBlackWhiteListMapper.toConfig(create);
        userBlackWhiteListRepository.save(config);
        return config.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserBlackWhiteListUpdate update) {
        log.info("修改用户黑白名单配置信息：{}", update);
        UserBlackWhiteList config = userBlackWhiteListRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("配置不存在"));

        if (update.getUserId() == null) {
            throw new BusinessException("用户ID不能为空");
        }

        UserBlackWhiteList existing = userBlackWhiteListRepository.findByAppCodeAndUserIdAndListType(
                update.getAppCode(), update.getUserId(), update.getListType());
        if (existing != null && !existing.getId().equals(update.getId())) {
            throw new BusinessException("该应用的此用户名单配置已存在");
        }

        userBlackWhiteListMapper.updateConfigFromDto(update, config);
        userBlackWhiteListRepository.save(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除用户黑白名单配置信息：{}", id);
        userBlackWhiteListRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除用户黑白名单配置信息：{}", ids);
        userBlackWhiteListRepository.deleteAllById(ids);
    }

    @Override
    public UserBlackWhiteListVo findById(Long id) {
        log.info("根据ID查询用户黑白名单配置信息：{}", id);
        UserBlackWhiteList config = userBlackWhiteListRepository.findById(id).orElse(null);
        if (config != null) {
            return userBlackWhiteListMapper.toVo(config);
        }
        return null;
    }

    @Override
    public PageVo<List<UserBlackWhiteListVo>> findPage(UserBlackWhiteListQuery query) {
        log.info("分页查询用户黑白名单配置信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<UserBlackWhiteList> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getAppCode())) {
                predicates.add(cb.equal(root.get("appCode"), query.getAppCode()));
            }
            if (query.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), query.getUserId()));
            }
            if (query.getListType() != null) {
                predicates.add(cb.equal(root.get("listType"), query.getListType()));
            }
            if (query.getEnabled() != null) {
                predicates.add(cb.equal(root.get("enabled"), query.getEnabled()));
            }
            return predicates;
        });

        Page<UserBlackWhiteList> page = userBlackWhiteListRepository.findAll(spec, pageable);
        List<UserBlackWhiteListVo> list = userBlackWhiteListMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public UserBlackWhiteListVo checkUser(String appCode, Long userId) {
        UserBlackWhiteListQuery query = new UserBlackWhiteListQuery();
        query.setAppCode(appCode);
        query.setEnabled(true);
        query.setPage(0);
        query.setPageSize(100);
        
        Specification<UserBlackWhiteList> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("appCode"), appCode));
            predicates.add(cb.equal(root.get("userId"), userId));
            predicates.add(cb.equal(root.get("enabled"), true));
            return predicates;
        });
        
        List<UserBlackWhiteList> list = userBlackWhiteListRepository.findAll(spec);
        if (list != null && !list.isEmpty()) {
            return userBlackWhiteListMapper.toVo(list.get(0));
        }
        
        return null;
    }
}
