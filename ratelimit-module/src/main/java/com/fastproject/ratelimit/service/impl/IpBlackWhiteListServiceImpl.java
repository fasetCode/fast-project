package com.fastproject.ratelimit.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.ratelimit.domain.IpBlackWhiteList;
import com.fastproject.ratelimit.mapper.IpBlackWhiteListMapper;
import com.fastproject.ratelimit.repository.db.IpBlackWhiteListRepository;
import com.fastproject.ratelimit.service.IpBlackWhiteListService;
import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListCreate;
import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListQuery;
import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListUpdate;
import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListVo;
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
 * IP黑白名单配置 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class IpBlackWhiteListServiceImpl implements IpBlackWhiteListService {

    private final IpBlackWhiteListRepository ipBlackWhiteListRepository;
    private final QueryHelp<IpBlackWhiteList> queryHelp;
    private final IpBlackWhiteListMapper ipBlackWhiteListMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(IpBlackWhiteListCreate create) {
        log.info("保存IP黑白名单配置信息：{}", create);

        if (!StringUtils.hasText(create.getIpAddress())) {
            throw new BusinessException("IP地址不能为空");
        }

        IpBlackWhiteList existing = ipBlackWhiteListRepository.findByAppCodeAndIpAddressAndListType(
                create.getAppCode(), create.getIpAddress(), create.getListType());
        if (existing != null) {
            throw new BusinessException("该应用的此IP名单配置已存在");
        }

        IpBlackWhiteList config = ipBlackWhiteListMapper.toConfig(create);
        ipBlackWhiteListRepository.save(config);
        return config.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(IpBlackWhiteListUpdate update) {
        log.info("修改IP黑白名单配置信息：{}", update);
        IpBlackWhiteList config = ipBlackWhiteListRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("配置不存在"));

        if (!StringUtils.hasText(update.getIpAddress())) {
            throw new BusinessException("IP地址不能为空");
        }

        IpBlackWhiteList existing = ipBlackWhiteListRepository.findByAppCodeAndIpAddressAndListType(
                update.getAppCode(), update.getIpAddress(), update.getListType());
        if (existing != null && !existing.getId().equals(update.getId())) {
            throw new BusinessException("该应用的此IP名单配置已存在");
        }

        ipBlackWhiteListMapper.updateConfigFromDto(update, config);
        ipBlackWhiteListRepository.save(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除IP黑白名单配置信息：{}", id);
        ipBlackWhiteListRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除IP黑白名单配置信息：{}", ids);
        ipBlackWhiteListRepository.deleteAllById(ids);
    }

    @Override
    public IpBlackWhiteListVo findById(Long id) {
        log.info("根据ID查询IP黑白名单配置信息：{}", id);
        IpBlackWhiteList config = ipBlackWhiteListRepository.findById(id).orElse(null);
        if (config != null) {
            return ipBlackWhiteListMapper.toVo(config);
        }
        return null;
    }

    @Override
    public PageVo<List<IpBlackWhiteListVo>> findPage(IpBlackWhiteListQuery query) {
        log.info("分页查询IP黑白名单配置信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<IpBlackWhiteList> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getAppCode())) {
                predicates.add(cb.equal(root.get("appCode"), query.getAppCode()));
            }
            if (StringUtils.hasText(query.getIpAddress())) {
                predicates.add(cb.like(root.get("ipAddress"), "%" + query.getIpAddress() + "%"));
            }
            if (query.getListType() != null) {
                predicates.add(cb.equal(root.get("listType"), query.getListType()));
            }
            if (query.getEnabled() != null) {
                predicates.add(cb.equal(root.get("enabled"), query.getEnabled()));
            }
            return predicates;
        });

        Page<IpBlackWhiteList> page = ipBlackWhiteListRepository.findAll(spec, pageable);
        List<IpBlackWhiteListVo> list = ipBlackWhiteListMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public IpBlackWhiteListVo checkIp(String appCode, String ip) {
        // 先精确匹配
        IpBlackWhiteListQuery query = new IpBlackWhiteListQuery();
        query.setAppCode(appCode);
        query.setEnabled(true);
        query.setPage(0);
        query.setPageSize(1000);
        
        Specification<IpBlackWhiteList> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("appCode"), appCode));
            predicates.add(cb.equal(root.get("enabled"), true));
            return predicates;
        });
        
        List<IpBlackWhiteList> allConfigs = ipBlackWhiteListRepository.findAll(spec);
        if (allConfigs.isEmpty()) {
            return null;
        }
        
        for (IpBlackWhiteList config : allConfigs) {
            // 简单的IP段或者精确IP匹配 (借用IpUtils能力, 或者这里做简化匹配)
            if (ip.equals(config.getIpAddress())) {
                return ipBlackWhiteListMapper.toVo(config);
            }
        }
        
        return null;
    }
}
