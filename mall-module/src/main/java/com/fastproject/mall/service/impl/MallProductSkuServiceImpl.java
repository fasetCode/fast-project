package com.fastproject.mall.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.mall.domain.MallProductSku;
import com.fastproject.mall.mapper.MallProductSkuMapper;
import com.fastproject.mall.repository.db.MallProductSkuRepository;
import com.fastproject.mall.service.MallProductSkuService;
import com.fastproject.mall.vo.sku.MallProductSkuCreate;
import com.fastproject.mall.vo.sku.MallProductSkuQuery;
import com.fastproject.mall.vo.sku.MallProductSkuUpdate;
import com.fastproject.mall.vo.sku.MallProductSkuVo;
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
 * 商品SKU Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MallProductSkuServiceImpl implements MallProductSkuService {

    private final MallProductSkuRepository mallProductSkuRepository;
    private final QueryHelp<MallProductSku> queryHelp;
    private final MallProductSkuMapper mallProductSkuMapper;

    @Override
    public Long save(MallProductSkuCreate create) {
        log.info("保存商品SKU：{}", create);
        if (StringUtils.hasText(create.getSkuSn()) && mallProductSkuRepository.existsBySkuSn(create.getSkuSn())) {
            throw new BusinessException("SKU编码已存在");
        }
        MallProductSku entity = mallProductSkuMapper.toSku(create);
        mallProductSkuRepository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MallProductSkuUpdate update) {
        log.info("修改商品SKU：{}", update);
        MallProductSku entity = mallProductSkuRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("商品SKU不存在"));
        if (StringUtils.hasText(update.getSkuSn())
                && mallProductSkuRepository.existsBySkuSnAndIdNot(update.getSkuSn(), update.getId())) {
            throw new BusinessException("SKU编码已存在");
        }
        mallProductSkuMapper.updateSkuFromDto(update, entity);
        mallProductSkuRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        log.info("删除商品SKU：{}", id);
        mallProductSkuRepository.findById(id).orElseThrow(() -> new BusinessException("商品SKU不存在"));
        mallProductSkuRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除商品SKU：{}", ids);
        mallProductSkuRepository.deleteAllById(ids);
    }

    @Override
    public MallProductSkuVo findById(Long id) {
        log.info("根据ID查询商品SKU：{}", id);
        MallProductSku entity = mallProductSkuRepository.findById(id).orElse(null);
        return entity != null ? mallProductSkuMapper.toVo(entity) : null;
    }

    @Override
    public List<MallProductSkuVo> findByProductId(Long productId) {
        log.info("根据商品ID查询SKU列表：{}", productId);
        return mallProductSkuMapper.toVo(mallProductSkuRepository.findByProductId(productId));
    }

    @Override
    public List<MallProductSkuVo> findByShopId(Long shopId) {
        log.info("根据店铺ID查询SKU列表：{}", shopId);
        return mallProductSkuMapper.toVo(mallProductSkuRepository.findByShopId(shopId));
    }

    @Override
    public PageVo<List<MallProductSkuVo>> findPage(MallProductSkuQuery query) {
        log.info("分页查询商品SKU：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Specification<MallProductSku> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query.getProductId() != null) predicates.add(cb.equal(root.get("productId"), query.getProductId()));
            if (query.getShopId() != null) predicates.add(cb.equal(root.get("shopId"), query.getShopId()));
            if (StringUtils.hasText(query.getSkuSn())) predicates.add(cb.equal(root.get("skuSn"), query.getSkuSn()));
            if (query.getStatus() != null) predicates.add(cb.equal(root.get("status"), query.getStatus()));
            return predicates;
        });
        Page<MallProductSku> page = mallProductSkuRepository.findAll(spec, pageable);
        return PageVo.of(page.getTotalElements(), mallProductSkuMapper.toVo(page.getContent()));
    }

    @Override
    public List<MallProductSkuVo> findAll() {
        return mallProductSkuMapper.toVo(mallProductSkuRepository.findAll(Sort.by("id").descending()));
    }
}
