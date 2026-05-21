package com.fastproject.mall.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.mall.domain.MallProduct;
import com.fastproject.mall.mapper.MallProductMapper;
import com.fastproject.mall.repository.db.MallProductRepository;
import com.fastproject.mall.service.MallProductService;
import com.fastproject.mall.vo.product.MallProductCreate;
import com.fastproject.mall.vo.product.MallProductQuery;
import com.fastproject.mall.vo.product.MallProductUpdate;
import com.fastproject.mall.vo.product.MallProductVo;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class MallProductServiceImpl implements MallProductService {

    private final MallProductRepository mallProductRepository;
    private final QueryHelp<MallProduct> queryHelp;
    private final MallProductMapper mallProductMapper;

    @Override
    public Long save(MallProductCreate create) {
        log.info("保存商品：{}", create);
        if (StringUtils.hasText(create.getProductSn()) && mallProductRepository.existsByProductSn(create.getProductSn())) {
            throw new BusinessException("商品货号已存在");
        }
        MallProduct product = mallProductMapper.toProduct(create);
        mallProductRepository.save(product);
        return product.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MallProductUpdate update) {
        log.info("修改商品：{}", update);
        MallProduct product = mallProductRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("商品不存在"));
        if (StringUtils.hasText(update.getProductSn()) && mallProductRepository.existsByProductSnAndIdNot(update.getProductSn(), update.getId())) {
            throw new BusinessException("商品货号已存在");
        }
        mallProductMapper.updateProductFromDto(update, product);
        mallProductRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        log.info("删除商品：{}", id);
        mallProductRepository.findById(id).orElseThrow(() -> new BusinessException("商品不存在"));
        mallProductRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除商品：{}", ids);
        mallProductRepository.deleteAllById(ids);
    }

    @Override
    public MallProductVo findById(Long id) {
        log.info("根据ID查询商品：{}", id);
        MallProduct product = mallProductRepository.findById(id).orElse(null);
        return product != null ? mallProductMapper.toVo(product) : null;
    }

    @Override
    public PageVo<List<MallProductVo>> findPage(MallProductQuery query) {
        log.info("分页查询商品：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("sort").ascending());
        Specification<MallProduct> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getName())) predicates.add(cb.like(root.get("name"), "%" + query.getName() + "%"));
            if (query.getShopId() != null) predicates.add(cb.equal(root.get("shopId"), query.getShopId()));
            if (query.getCategoryId() != null) predicates.add(cb.equal(root.get("categoryId"), query.getCategoryId()));
            if (query.getBrandId() != null) predicates.add(cb.equal(root.get("brandId"), query.getBrandId()));
            if (query.getStatus() != null) predicates.add(cb.equal(root.get("status"), query.getStatus()));
            if (query.getIsNew() != null) predicates.add(cb.equal(root.get("isNew"), query.getIsNew()));
            if (query.getIsHot() != null) predicates.add(cb.equal(root.get("isHot"), query.getIsHot()));
            if (query.getIsRecommend() != null) predicates.add(cb.equal(root.get("isRecommend"), query.getIsRecommend()));
            return predicates;
        });
        Page<MallProduct> page = mallProductRepository.findAll(spec, pageable);
        return PageVo.of(page.getTotalElements(), mallProductMapper.toVo(page.getContent()));
    }

    @Override
    public List<MallProductVo> findAll() {
        return mallProductMapper.toVo(mallProductRepository.findAll(Sort.by("sort").ascending()));
    }

    @Override
    public List<MallProductVo> selectAll() {
        Specification<MallProduct> spec = (root, query, cb) -> cb.equal(root.get("status"), StatusEnum.NORMAL.getCode());
        return mallProductMapper.toVo(mallProductRepository.findAll(spec, Sort.by("sort").ascending()));
    }
}
