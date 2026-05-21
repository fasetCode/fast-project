package com.fastproject.system.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.system.domain.SysDepartment;
import com.fastproject.system.mapper.SysDepartmentMapper;
import com.fastproject.system.repository.db.SysDepartmentRepository;
import com.fastproject.system.service.SysDepartmentService;
import com.fastproject.system.tenant.TenantAccessSupport;
import com.fastproject.system.vo.department.SysDepartmentUpdate;
import com.fastproject.system.vo.department.SysDepartmentCreate;
import com.fastproject.system.vo.department.SysDepartmentQuery;
import com.fastproject.system.vo.department.SysDepartmentVo;
import com.fastproject.utils.enums.StatusEnum;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysDepartmentServiceImpl implements SysDepartmentService {

    private final SysDepartmentRepository sysDepartmentRepository;
    private final QueryHelp<SysDepartment> queryHelp;
    private final SysDepartmentMapper sysDepartmentMapper;
    private final TenantAccessSupport tenantAccessSupport;

    @Override
    public Long save(SysDepartmentCreate create) {
        log.info("保存部门信息：{}", create);
        Long tenantId = tenantAccessSupport.requireCurrentTenantId();
        if (tenantId != null ? sysDepartmentRepository.existsByNameAndTenantId(create.getName(), tenantId)
                : sysDepartmentRepository.existsByName(create.getName())) {
            throw new BusinessException("部门名称已存在");
        }

        SysDepartment department = sysDepartmentMapper.toDepartment(create);
        tenantAccessSupport.bindCurrentTenant(department);
        sysDepartmentRepository.save(department);
        return department.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDepartmentUpdate sysDepartmentUpdate) {
        log.info("修改部门信息：{}", sysDepartmentUpdate);
        SysDepartment department = sysDepartmentRepository.findById(sysDepartmentUpdate.getId())
                .orElseThrow(() -> new BusinessException("部门不存在"));
        tenantAccessSupport.checkEntityAccess(department, "无权修改当前租户部门");
        Long tenantId = tenantAccessSupport.requireCurrentTenantId();

        if (tenantId != null ? sysDepartmentRepository.existsByNameAndIdNotAndTenantId(sysDepartmentUpdate.getName(), sysDepartmentUpdate.getId(), tenantId)
                : sysDepartmentRepository.existsByNameAndIdNot(sysDepartmentUpdate.getName(), sysDepartmentUpdate.getId())) {
            throw new BusinessException("部门名称已存在");
        }

        sysDepartmentMapper.updateDepartmentFromDto(sysDepartmentUpdate, department);
        sysDepartmentRepository.save(department);
    }

    @Override
    public void delete(Long id) {
        log.info("删除部门信息：{}", id);
        SysDepartment department = sysDepartmentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("部门不存在"));
        tenantAccessSupport.checkEntityAccess(department, "无权删除当前租户部门");
        // 检查是否有子部门
        Long tenantId = tenantAccessSupport.requireCurrentTenantId();
        boolean hasChildren = tenantId != null
                ? sysDepartmentRepository.existsByParentIdAndTenantId(id, tenantId)
                : sysDepartmentRepository.existsByParentId(id);
        if (hasChildren) {
            throw new BusinessException("该部门下存在子部门，无法删除");
        }
        sysDepartmentRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除部门信息：{}", ids);
        List<SysDepartment> departments = sysDepartmentRepository.findAllById(ids);
        Long tenantId = tenantAccessSupport.requireCurrentTenantId();
        for (Long id : ids) {
            boolean hasChildren = tenantId != null
                    ? sysDepartmentRepository.existsByParentIdAndTenantId(id, tenantId)
                    : sysDepartmentRepository.existsByParentId(id);
            if (hasChildren) {
                throw new BusinessException("部门ID " + id + " 下存在子部门，无法删除");
            }
        }
        for (SysDepartment department : departments) {
            tenantAccessSupport.checkEntityAccess(department, "无权删除当前租户部门");
        }
        sysDepartmentRepository.deleteAllById(ids);
    }

    @Override
    public SysDepartmentVo findById(Long id) {
        log.info("根据ID查询部门信息：{}", id);
        SysDepartment department = sysDepartmentRepository.findById(id).orElse(null);
        if (department != null) {
            tenantAccessSupport.checkEntityAccess(department, "无权查看当前租户部门");
            return sysDepartmentMapper.toVo(department);
        }
        return null;
    }

    @Override
    public PageVo<List<SysDepartmentVo>> findPage(SysDepartmentQuery sysDepartmentQuery) {
        log.info("分页查询部门信息：{}", sysDepartmentQuery);
        Pageable pageable = PageRequest.of(sysDepartmentQuery.getPage(), sysDepartmentQuery.getPageSize(), Sort.by("sort").ascending());

        Specification<SysDepartment> spec = queryHelp.getWhere(sysDepartmentQuery, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            tenantAccessSupport.applyTenantPredicate(predicates, root, cb);
            if (StringUtils.hasText(sysDepartmentQuery.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + sysDepartmentQuery.getName() + "%"));
            }
            if (sysDepartmentQuery.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), sysDepartmentQuery.getStatus()));
            }
            if (sysDepartmentQuery.getParentId() != null) {
                predicates.add(cb.equal(root.get("parentId"), sysDepartmentQuery.getParentId()));
            }
            return predicates;
        });

        Page<SysDepartment> page = sysDepartmentRepository.findAll(spec, pageable);
        List<SysDepartmentVo> list = sysDepartmentMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public List<SysDepartmentVo> findTree() {
        Specification<SysDepartment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            tenantAccessSupport.applyTenantPredicate(predicates, root, cb);
            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
        List<SysDepartment> all = sysDepartmentRepository.findAll(spec, Sort.by("sort").ascending());
        return buildTree(sysDepartmentMapper.toVo(all));
    }

    @Override
    public List<SysDepartmentVo> selectAll() {
        // 查询所有正常状态的部门
        Specification<SysDepartment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), StatusEnum.NORMAL.getCode()));
            tenantAccessSupport.applyTenantPredicate(predicates, root, cb);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        List<SysDepartment> all = sysDepartmentRepository.findAll(spec, Sort.by("sort").ascending());
        return buildTree(sysDepartmentMapper.toVo(all));
    }

    /**
     * 构建树形数据
     */
    private List<SysDepartmentVo> buildTree(List<SysDepartmentVo> allVo) {
        Map<Long, List<SysDepartmentVo>> parentMap = allVo.stream()
                .collect(Collectors.groupingBy(vo -> vo.getParentId() == null ? 0L : vo.getParentId()));

        allVo.forEach(vo -> vo.setChildren(parentMap.get(vo.getId())));

        return parentMap.getOrDefault(0L, new ArrayList<>());
    }
}
