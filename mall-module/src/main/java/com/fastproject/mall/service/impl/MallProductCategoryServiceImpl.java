package com.fastproject.mall.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.mall.domain.MallProductCategory;
import com.fastproject.mall.mapper.MallProductCategoryMapper;
import com.fastproject.mall.repository.db.MallProductCategoryRepository;
import com.fastproject.mall.service.MallProductCategoryService;
import com.fastproject.mall.vo.category.MallProductCategoryCreate;
import com.fastproject.mall.vo.category.MallProductCategoryQuery;
import com.fastproject.mall.vo.category.MallProductCategoryUpdate;
import com.fastproject.mall.vo.category.MallProductCategoryVo;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品分类 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MallProductCategoryServiceImpl implements MallProductCategoryService {

    private final MallProductCategoryRepository mallProductCategoryRepository;
    private final QueryHelp<MallProductCategory> queryHelp;
    private final MallProductCategoryMapper mallProductCategoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(MallProductCategoryCreate create) {
        log.info("保存商品分类：{}", create);
        MallProductCategory category = mallProductCategoryMapper.toCategory(create);
        applyParent(category, create.getParentId());
        if (category.getStatus() == null) {
            category.setStatus(StatusEnum.NORMAL.getCode());
        }
        if (category.getSort() == null) {
            category.setSort(0);
        }
        if (category.getShowInHome() == null) {
            category.setShowInHome(0);
        }
        mallProductCategoryRepository.save(category);
        return category.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MallProductCategoryUpdate update) {
        log.info("修改商品分类：{}", update);
        MallProductCategory category = mallProductCategoryRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("分类不存在"));

        Long newParentId = update.getParentId() == null ? 0L : update.getParentId();
        if (newParentId.equals(update.getId())) {
            throw new BusinessException("不能将自身设为父级分类");
        }
        if (newParentId > 0 && isDescendant(update.getId(), newParentId)) {
            throw new BusinessException("不能将子分类设为父级分类");
        }

        mallProductCategoryMapper.updateCategoryFromDto(update, category);
        applyParent(category, newParentId);
        mallProductCategoryRepository.save(category);
    }

    /**
     * 设置父级并自动计算 level。parentId 为 null 或 0 表示顶级分类。
     */
    private void applyParent(MallProductCategory category, Long parentId) {
        if (parentId == null || parentId == 0L) {
            category.setParentId(0L);
            category.setLevel(1);
            return;
        }
        MallProductCategory parent = mallProductCategoryRepository.findById(parentId)
                .orElseThrow(() -> new BusinessException("父级分类不存在"));
        category.setParentId(parent.getId());
        category.setLevel((parent.getLevel() == null ? 1 : parent.getLevel()) + 1);
    }

    /**
     * 判断 targetId 是否为 sourceId 的后代（防止循环引用）。
     */
    private boolean isDescendant(Long sourceId, Long targetId) {
        Long currentParent = targetId;
        int depth = 0;
        while (currentParent != null && currentParent > 0 && depth < 32) {
            MallProductCategory node = mallProductCategoryRepository.findById(currentParent).orElse(null);
            if (node == null) {
                return false;
            }
            if (sourceId.equals(node.getParentId())) {
                return true;
            }
            if (sourceId.equals(node.getId())) {
                return true;
            }
            currentParent = node.getParentId();
            depth++;
        }
        return false;
    }

    @Override
    public void delete(Long id) {
        log.info("删除商品分类：{}", id);
        mallProductCategoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("分类不存在"));
        mallProductCategoryRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除商品分类：{}", ids);
        mallProductCategoryRepository.deleteAllById(ids);
    }

    @Override
    public MallProductCategoryVo findById(Long id) {
        log.info("根据ID查询分类：{}", id);
        MallProductCategory category = mallProductCategoryRepository.findById(id).orElse(null);
        return category != null ? mallProductCategoryMapper.toVo(category) : null;
    }

    @Override
    public PageVo<List<MallProductCategoryVo>> findPage(MallProductCategoryQuery query) {
        log.info("分页查询分类：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("sort").ascending());

        Specification<MallProductCategory> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + query.getName() + "%"));
            }
            if (query.getParentId() != null) {
                predicates.add(cb.equal(root.get("parentId"), query.getParentId()));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            return predicates;
        });

        Page<MallProductCategory> page = mallProductCategoryRepository.findAll(spec, pageable);
        List<MallProductCategoryVo> list = mallProductCategoryMapper.toVo(page.getContent());
        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public List<MallProductCategoryVo> findTree() {
        List<MallProductCategory> all = mallProductCategoryRepository.findAll(Sort.by("sort").ascending());
        return buildTree(mallProductCategoryMapper.toVo(all));
    }

    @Override
    public List<MallProductCategoryVo> findAll() {
        List<MallProductCategory> all = mallProductCategoryRepository.findAll(Sort.by("sort").ascending());
        return mallProductCategoryMapper.toVo(all);
    }

    @Override
    public List<MallProductCategoryVo> selectAll() {
        Specification<MallProductCategory> spec = (root, query, cb) -> cb.equal(root.get("status"), StatusEnum.NORMAL.getCode());
        List<MallProductCategory> all = mallProductCategoryRepository.findAll(spec, Sort.by("sort").ascending());
        return buildTree(mallProductCategoryMapper.toVo(all));
    }

    private List<MallProductCategoryVo> buildTree(List<MallProductCategoryVo> allVo) {
        Map<Long, List<MallProductCategoryVo>> parentMap = allVo.stream()
                .collect(Collectors.groupingBy(vo -> vo.getParentId() == null ? 0L : vo.getParentId()));

        allVo.forEach(vo -> vo.setChildren(parentMap.get(vo.getId())));

        return parentMap.getOrDefault(0L, new ArrayList<>());
    }
}
