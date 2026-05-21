package com.fastproject.system.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.system.domain.SysPost;
import com.fastproject.system.mapper.SysPostMapper;
import com.fastproject.system.repository.db.SysPostRepository;
import com.fastproject.system.service.SysPostService;
import com.fastproject.system.tenant.TenantAccessSupport;
import com.fastproject.system.vo.post.SysPostUpdate;
import com.fastproject.system.vo.post.SysPostCreate;
import com.fastproject.system.vo.post.SysPostQuery;
import com.fastproject.system.vo.post.SysPostVo;
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

/**
 * 岗位 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysPostServiceImpl implements SysPostService {

    private final SysPostRepository sysPostRepository;
    private final QueryHelp<SysPost> queryHelp;
    private final SysPostMapper sysPostMapper;
    private final TenantAccessSupport tenantAccessSupport;

    @Override
    public Long save(SysPostCreate create) {
        log.info("保存岗位信息：{}", create);
        Long tenantId = tenantAccessSupport.requireCurrentTenantId();

        // 检查岗位编码是否已存在
        if (tenantId != null ? sysPostRepository.existsByCodeAndTenantId(create.getCode(), tenantId)
                : sysPostRepository.existsByCode(create.getCode())) {
            throw new BusinessException("岗位编码已存在");
        }

        SysPost post = sysPostMapper.toPost(create);
        tenantAccessSupport.bindCurrentTenant(post);
        sysPostRepository.save(post);
        return post.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysPostUpdate sysPostUpdate) {
        log.info("修改岗位信息：{}", sysPostUpdate);
        SysPost post = sysPostRepository.findById(sysPostUpdate.getId())
                .orElseThrow(() -> new BusinessException("岗位不存在"));
        tenantAccessSupport.checkEntityAccess(post, "无权修改当前租户岗位");
        Long tenantId = tenantAccessSupport.requireCurrentTenantId();

        // 检查岗位编码是否已存在（排除自身）
        if (tenantId != null ? sysPostRepository.existsByCodeAndIdNotAndTenantId(sysPostUpdate.getCode(), sysPostUpdate.getId(), tenantId)
                : sysPostRepository.existsByCodeAndIdNot(sysPostUpdate.getCode(), sysPostUpdate.getId())) {
            throw new BusinessException("岗位编码已存在");
        }

        sysPostMapper.updatePostFromDto(sysPostUpdate, post);
        sysPostRepository.save(post);
    }

    @Override
    public void delete(Long id) {
        log.info("删除岗位信息：{}", id);
        SysPost post = sysPostRepository.findById(id)
                .orElseThrow(() -> new BusinessException("岗位不存在"));
        tenantAccessSupport.checkEntityAccess(post, "无权删除当前租户岗位");
        sysPostRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除岗位信息：{}", ids);
        List<SysPost> posts = sysPostRepository.findAllById(ids);
        for (SysPost post : posts) {
            tenantAccessSupport.checkEntityAccess(post, "无权删除当前租户岗位");
        }
        sysPostRepository.deleteAllById(ids);
    }

    @Override
    public SysPostVo findById(Long id) {
        log.info("根据ID查询岗位信息：{}", id);
        SysPost post = sysPostRepository.findById(id).orElse(null);
        if (post != null) {
            tenantAccessSupport.checkEntityAccess(post, "无权查看当前租户岗位");
            return sysPostMapper.toVo(post);
        }
        return null;
    }

    @Override
    public PageVo<List<SysPostVo>> findPage(SysPostQuery sysPostQuery) {
        log.info("分页查询岗位信息：{}", sysPostQuery);
        Pageable pageable = PageRequest.of(sysPostQuery.getPage(), sysPostQuery.getPageSize(), Sort.by("sort").ascending());

        Specification<SysPost> spec = queryHelp.getWhere(sysPostQuery, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            tenantAccessSupport.applyTenantPredicate(predicates, root, cb);
            if (StringUtils.hasText(sysPostQuery.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + sysPostQuery.getName() + "%"));
            }
            if (StringUtils.hasText(sysPostQuery.getCode())) {
                predicates.add(cb.equal(root.get("code"), sysPostQuery.getCode()));
            }
            if (sysPostQuery.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), sysPostQuery.getStatus()));
            }
            return predicates;
        });

        Page<SysPost> page = sysPostRepository.findAll(spec, pageable);
        List<SysPostVo> list = sysPostMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public List<SysPostVo> findAll() {
        Specification<SysPost> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            tenantAccessSupport.applyTenantPredicate(predicates, root, cb);
            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
        List<SysPost> all = sysPostRepository.findAll(spec, Sort.by("sort").ascending());
        return sysPostMapper.toVo(all);
    }

    @Override
    public List<SysPostVo> selectAll() {
        // 查询所有正常状态的岗位
        Specification<SysPost> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), StatusEnum.NORMAL.getCode()));
            tenantAccessSupport.applyTenantPredicate(predicates, root, cb);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        List<SysPost> all = sysPostRepository.findAll(spec, Sort.by("sort").ascending());
        return sysPostMapper.toVo(all);
    }
}
