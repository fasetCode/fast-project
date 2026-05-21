package com.fastproject.system.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.system.domain.SysPermissions;
import com.fastproject.system.domain.SysRole;
import com.fastproject.system.mapper.SysRoleMapper;
import com.fastproject.system.repository.db.SysPermissionsRepository;
import com.fastproject.system.repository.db.SysRoleRepository;
import com.fastproject.system.service.SysRoleService;
import com.fastproject.system.tenant.TenantAccessSupport;
import com.fastproject.system.vo.role.SysRoleUpdate;
import com.fastproject.system.vo.role.SysRoleCreate;
import com.fastproject.system.vo.role.SysRoleQuery;
import com.fastproject.system.vo.role.SysRoleVo;
import com.fastproject.utils.enums.StatusEnum;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.SelectVo;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleRepository sysRoleRepository;
    private final SysPermissionsRepository sysPermissionsRepository;
    private final QueryHelp<SysRole> queryHelp;
    private final SysRoleMapper sysRoleMapper;
    private final TenantAccessSupport tenantAccessSupport;

    @Override
    public List<SelectVo> selectAll() {
        Specification<SysRole> spec = (Root<SysRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), StatusEnum.NORMAL.getCode()));
            tenantAccessSupport.applyTenantPredicate(predicates, root, cb);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        List<SysRole> roles = sysRoleRepository.findAll(spec, Sort.by("id"));
        return roles.stream()
                .map(role -> new SelectVo(role.getId(), role.getTitle()))
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SysRoleCreate create) {
        log.info("保存角色信息：{}", create);
        Long tenantId = tenantAccessSupport.requireCurrentTenantId();
        if (tenantId != null ? sysRoleRepository.existsByTitleAndTenantId(create.getTitle(), tenantId)
                : sysRoleRepository.existsByTitle(create.getTitle())) {
            throw new BusinessException("角色名称已存在");
        }
        if (tenantId != null ? sysRoleRepository.existsByCodeAndTenantId(create.getCode(), tenantId)
                : sysRoleRepository.existsByCode(create.getCode())) {
            throw new BusinessException("角色编码已存在");
        }
        SysRole role = sysRoleMapper.toRole(create);
        tenantAccessSupport.bindCurrentTenant(role);
        
        if (create.getPermissionIds() != null && !create.getPermissionIds().isEmpty()) {
            Set<SysPermissions> permissions = new HashSet<>(sysPermissionsRepository.findAllById(create.getPermissionIds()));
            role.setPermissions(permissions);
        }
        
        sysRoleRepository.save(role);
        return role.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleUpdate sysRoleUpdate) {
        log.info("修改角色信息：{}", sysRoleUpdate);
        SysRole role = sysRoleRepository.findById(sysRoleUpdate.getId())
                .orElseThrow(() -> new BusinessException("角色不存在"));
        tenantAccessSupport.checkEntityAccess(role, "无权修改当前租户角色");
        Long tenantId = tenantAccessSupport.requireCurrentTenantId();

        if (tenantId != null ? sysRoleRepository.existsByTitleAndIdNotAndTenantId(sysRoleUpdate.getTitle(), sysRoleUpdate.getId(), tenantId)
                : sysRoleRepository.existsByTitleAndIdNot(sysRoleUpdate.getTitle(), sysRoleUpdate.getId())) {
            throw new BusinessException("角色名称已存在");
        }
        if (tenantId != null ? sysRoleRepository.existsByCodeAndIdNotAndTenantId(sysRoleUpdate.getCode(), sysRoleUpdate.getId(), tenantId)
                : sysRoleRepository.existsByCodeAndIdNot(sysRoleUpdate.getCode(), sysRoleUpdate.getId())) {
            throw new BusinessException("角色编码已存在");
        }

        sysRoleMapper.updateRoleFromDto(sysRoleUpdate, role);
        
        if (sysRoleUpdate.getPermissionIds() != null) {
            Set<SysPermissions> permissions = new HashSet<>(sysPermissionsRepository.findAllById(sysRoleUpdate.getPermissionIds()));
            role.setPermissions(permissions);
        } else {
            role.setPermissions(new HashSet<>());
        }
        
        sysRoleRepository.save(role);
    }

    @Override
    public void delete(Long id) {
        log.info("删除角色信息：{}", id);
        SysRole role = sysRoleRepository.findById(id)
                .orElseThrow(() -> new BusinessException("角色不存在"));
        tenantAccessSupport.checkEntityAccess(role, "无权删除当前租户角色");
        sysRoleRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除角色信息：{}", ids);
        List<SysRole> roles = sysRoleRepository.findAllById(ids);
        for (SysRole role : roles) {
            tenantAccessSupport.checkEntityAccess(role, "无权删除当前租户角色");
        }
        sysRoleRepository.deleteAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public SysRoleVo findById(Long id) {
        log.info("根据ID查询角色信息：{}", id);
        SysRole role = sysRoleRepository.findById(id).orElse(null);
        if (role != null) {
            tenantAccessSupport.checkEntityAccess(role, "无权查看当前租户角色");
            SysRoleVo vo = sysRoleMapper.toVo(role);
            if (role.getPermissions() != null) {
                List<Long> permissionIds = role.getPermissions().stream()
                        .map(SysPermissions::getId)
                        .toList();
                vo.setPermissionIds(permissionIds);
            }
            return vo;
        }
        return null;
    }

    @Override
    public PageVo<List<SysRoleVo>> findPage(SysRoleQuery sysRoleQuery) {
        log.info("分页查询角色信息：{}", sysRoleQuery);
        Pageable pageable = PageRequest.of(sysRoleQuery.getPage(), sysRoleQuery.getPageSize(), Sort.by("id").descending());

        Specification<SysRole> spec = queryHelp.getWhere(sysRoleQuery, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            tenantAccessSupport.applyTenantPredicate(predicates, root, cb);
            if (StringUtils.hasText(sysRoleQuery.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + sysRoleQuery.getTitle() + "%"));
            }
            if (StringUtils.hasText(sysRoleQuery.getCode())) {
                predicates.add(cb.equal(root.get("code"), sysRoleQuery.getCode()));
            }
            if (sysRoleQuery.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), sysRoleQuery.getStatus()));
            }
            return predicates;
        });

        Page<SysRole> page = sysRoleRepository.findAll(spec, pageable);
        List<SysRoleVo> list = sysRoleMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }



}
