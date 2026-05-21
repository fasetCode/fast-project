package com.fastproject.system.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.system.domain.SysPermissions;
import com.fastproject.system.domain.SysRole;
import com.fastproject.system.mapper.SysPermissionsMapper;
import com.fastproject.system.repository.db.SysPermissionsRepository;
import com.fastproject.system.repository.db.SysUsersRepository;
import com.fastproject.system.service.SysPermissionsService;
import com.fastproject.system.vo.permissions.SysPermissionsUpdate;
import com.fastproject.system.vo.permissions.SysPermissionsCreate;
import com.fastproject.system.vo.permissions.SysPermissionsQuery;
import com.fastproject.system.vo.permissions.SysPermissionsVo;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysPermissionsServiceImpl implements SysPermissionsService {

    private final SysPermissionsRepository sysPermissionsRepository;
    private final QueryHelp<SysPermissions> queryHelp;
    private final SysPermissionsMapper sysPermissionsMapper;
    private final SysUsersRepository sysUsersRepository;

    @Override
    public Long save(SysPermissionsCreate create) {
        log.info("保存权限信息：{}", create);

        SysPermissions permissions = sysPermissionsMapper.toPermissions(create);
        sysPermissionsRepository.save(permissions);
        return permissions.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysPermissionsUpdate sysPermissionsUpdate) {
        log.info("修改权限信息：{}", sysPermissionsUpdate);
        SysPermissions permissions = sysPermissionsRepository.findById(sysPermissionsUpdate.getId())
                .orElseThrow(() -> new BusinessException("权限不存在"));
        sysPermissionsMapper.updatePermissionsFromDto(sysPermissionsUpdate, permissions);
        sysPermissionsRepository.save(permissions);
    }

    @Override
    public void delete(Long id) {
        log.info("删除权限信息：{}", id);
        sysPermissionsRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除权限信息：{}", ids);
        sysPermissionsRepository.deleteAllById(ids);
    }

    @Override
    public SysPermissionsVo findById(Long id) {
        log.info("根据ID查询权限信息：{}", id);
        SysPermissions permissions = sysPermissionsRepository.findById(id).orElse(null);
        if (permissions != null) {
            return sysPermissionsMapper.toVo(permissions);
        }
        return null;
    }

    @Override
    public PageVo<List<SysPermissionsVo>> findPage(SysPermissionsQuery sysPermissionsQuery) {
        log.info("分页查询权限信息：{}", sysPermissionsQuery);
        Pageable pageable = PageRequest.of(sysPermissionsQuery.getPage(), sysPermissionsQuery.getPageSize(), Sort.by("sort").ascending());

        Specification<SysPermissions> spec = queryHelp.getWhere(sysPermissionsQuery, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(sysPermissionsQuery.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + sysPermissionsQuery.getTitle() + "%"));
            }
            if (StringUtils.hasText(sysPermissionsQuery.getCode())) {
                predicates.add(cb.equal(root.get("code"), sysPermissionsQuery.getCode()));
            }
            if (sysPermissionsQuery.getType() != null) {
                predicates.add(cb.equal(root.get("type"), sysPermissionsQuery.getType()));
            }
            if (sysPermissionsQuery.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), sysPermissionsQuery.getStatus()));
            }
            if (sysPermissionsQuery.getParentId() != null) {
                predicates.add(cb.equal(root.get("parentId"), sysPermissionsQuery.getParentId()));
            }
            if (sysPermissionsQuery.getApplicationId() != null) {
                predicates.add(cb.equal(root.get("applicationId"), sysPermissionsQuery.getApplicationId()));
            }
            return predicates;
        });

        Page<SysPermissions> page = sysPermissionsRepository.findAll(spec, pageable);
        List<SysPermissionsVo> list = sysPermissionsMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public List<SysPermissionsVo> findTree() {
        List<SysPermissions> all = sysPermissionsRepository.findAll(Sort.by("sort").ascending());
        return buildTree(sysPermissionsMapper.toVo(all));
    }

    private List<SysPermissionsVo> buildTree(List<SysPermissionsVo> allVo) {
        Map<Long, List<SysPermissionsVo>> parentMap = allVo.stream()
                .collect(Collectors.groupingBy(vo -> vo.getParentId() == null ? 0L : vo.getParentId()));

        List<SysPermissionsVo> roots = parentMap.getOrDefault(0L, List.of());
        allVo.forEach(vo -> vo.setChildren(parentMap.get(vo.getId())));

        return roots;
    }

    @Override
    public List<String> findButtonPermissionsByUserId(Long userId) {
        return sysUsersRepository.findByIdWithRoles(userId)
                .map(user -> user.getRoles().stream()
                        .map(SysRole::getPermissions)
                        .flatMap(Set::stream)
                        .filter(p -> p.getType() != null && p.getType() == 2)
                        .map(SysPermissions::getCode)
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    @Override
    public List<String> findButtonPermissionsByALL() {
        return sysPermissionsRepository.findAll().stream()
                .filter(p -> p.getType() != null && p.getType() == 2)
                .map(SysPermissions::getCode)
                .filter(Objects::nonNull)                           // 过滤空码
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllPermissionsByUserId(Long userId) {
        return sysUsersRepository.findByIdWithRoles(userId)
                .map(user -> user.getRoles().stream()
                        .map(SysRole::getPermissions)
                        .flatMap(Set::stream)
                        .map(SysPermissions::getCode)
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }


    @Override
    public List<String> findAllPermissionsByAll() {
        return sysPermissionsRepository.findAll().stream()
                .map(SysPermissions::getCode)                       // 提取权限码
                .filter(Objects::nonNull)                           // 过滤空码
                .distinct()                                         // 去重
                .collect(Collectors.toList());                      // 收集为List
    }

    @Override
    public List<SysPermissionsVo> findTreeByUserId(Long userId) {
        List<SysPermissions> userPermissions = sysUsersRepository.findByIdWithRoles(userId)
                .map(user -> user.getRoles().stream()
                        .map(SysRole::getPermissions)
                        .flatMap(Set::stream)
                        .distinct()
                        .sorted(Comparator.comparing(SysPermissions::getSort, Comparator.nullsLast(Comparator.naturalOrder())))
                        .collect(Collectors.toList()))
                .orElse(List.of());

        if (userPermissions.isEmpty()) {
            return List.of();
        }

        return buildTree(sysPermissionsMapper.toVo(userPermissions));
    }

}
