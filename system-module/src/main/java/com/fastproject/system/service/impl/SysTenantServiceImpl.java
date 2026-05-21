package com.fastproject.system.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.system.config.TenantFeatureGuard;
import com.fastproject.system.domain.SysTenant;
import com.fastproject.system.mapper.SysTenantMapper;
import com.fastproject.system.repository.db.SysTenantRepository;
import com.fastproject.system.service.SysTenantService;
import com.fastproject.system.vo.tenant.SysTenantCreate;
import com.fastproject.system.vo.tenant.SysTenantQuery;
import com.fastproject.system.vo.tenant.SysTenantUpdate;
import com.fastproject.system.vo.tenant.SysTenantVo;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 租户 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysTenantServiceImpl implements SysTenantService {

    private final SysTenantRepository sysTenantRepository;
    private final QueryHelp<SysTenant> queryHelp;
    private final SysTenantMapper sysTenantMapper;
    private final TenantFeatureGuard tenantFeatureGuard;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SysTenantCreate create) {
        tenantFeatureGuard.checkEnabled();
        log.info("保存租户信息：{}", create);

        // 检查名称是否已存在
        if (sysTenantRepository.existsByName(create.getName())) {
            throw new BusinessException("租户名称已存在");
        }

        // 检查域名是否已存在
        if (StringUtils.hasText(create.getDomain()) && sysTenantRepository.existsByDomain(create.getDomain())) {
            throw new BusinessException("租户域名已存在");
        }

        SysTenant tenant = sysTenantMapper.toTenant(create);
        sysTenantRepository.save(tenant);
        return tenant.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysTenantUpdate sysTenantUpdate) {
        tenantFeatureGuard.checkEnabled();
        log.info("修改租户信息：{}", sysTenantUpdate);
        SysTenant tenant = sysTenantRepository.findById(sysTenantUpdate.getId())
                .orElseThrow(() -> new BusinessException("租户不存在"));

        // 检查名称是否已存在（排除自身）
        if (sysTenantRepository.existsByNameAndIdNot(sysTenantUpdate.getName(), sysTenantUpdate.getId())) {
            throw new BusinessException("租户名称已存在");
        }

        // 检查域名是否已存在（排除自身）
        if (StringUtils.hasText(sysTenantUpdate.getDomain()) && 
            sysTenantRepository.existsByDomainAndIdNot(sysTenantUpdate.getDomain(), sysTenantUpdate.getId())) {
            throw new BusinessException("租户域名已存在");
        }

        sysTenantMapper.updateTenantFromDto(sysTenantUpdate, tenant);
        sysTenantRepository.save(tenant);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        tenantFeatureGuard.checkEnabled();
        log.info("删除租户信息：{}", id);
        sysTenantRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        tenantFeatureGuard.checkEnabled();
        log.info("批量删除租户信息：{}", ids);
        sysTenantRepository.deleteAllById(ids);
    }

    @Override
    public SysTenantVo findById(Long id) {
        tenantFeatureGuard.checkEnabled();
        log.info("根据ID查询租户信息：{}", id);
        SysTenant tenant = sysTenantRepository.findById(id).orElse(null);
        if (tenant != null) {
            return sysTenantMapper.toVo(tenant);
        }
        return null;
    }

    @Override
    public PageVo<List<SysTenantVo>> findPage(SysTenantQuery query) {
        tenantFeatureGuard.checkEnabled();
        log.info("分页查询租户信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<SysTenant> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + query.getName() + "%"));
            }
            if (StringUtils.hasText(query.getContactName())) {
                predicates.add(cb.like(root.get("contactName"), "%" + query.getContactName() + "%"));
            }
            if (StringUtils.hasText(query.getDomain())) {
                predicates.add(cb.like(root.get("domain"), "%" + query.getDomain() + "%"));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            return predicates;
        });

        Page<SysTenant> page = sysTenantRepository.findAll(spec, pageable);
        List<SysTenantVo> list = sysTenantMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }
}
