package com.fastproject.mall.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.mall.domain.MallCart;
import com.fastproject.mall.mapper.MallCartMapper;
import com.fastproject.mall.repository.db.MallCartRepository;
import com.fastproject.mall.service.MallCartService;
import com.fastproject.mall.vo.cart.MallCartCreate;
import com.fastproject.mall.vo.cart.MallCartQuery;
import com.fastproject.mall.vo.cart.MallCartUpdate;
import com.fastproject.mall.vo.cart.MallCartVo;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MallCartServiceImpl implements MallCartService {

    private final MallCartRepository mallCartRepository;
    private final QueryHelp<MallCart> queryHelp;
    private final MallCartMapper mallCartMapper;

    @Override
    public Long save(MallCartCreate create) {
        log.info("保存购物车：{}", create);
        MallCart cart = mallCartMapper.toCart(create);
        mallCartRepository.save(cart);
        return cart.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MallCartUpdate update) {
        log.info("修改购物车：{}", update);
        MallCart cart = mallCartRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("购物车不存在"));
        mallCartMapper.updateCartFromDto(update, cart);
        mallCartRepository.save(cart);
    }

    @Override
    public void delete(Long id) {
        log.info("删除购物车：{}", id);
        mallCartRepository.findById(id).orElseThrow(() -> new BusinessException("购物车不存在"));
        mallCartRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除购物车：{}", ids);
        mallCartRepository.deleteAllById(ids);
    }

    @Override
    public MallCartVo findById(Long id) {
        log.info("根据ID查询购物车：{}", id);
        MallCart cart = mallCartRepository.findById(id).orElse(null);
        return cart != null ? mallCartMapper.toVo(cart) : null;
    }

    @Override
    public PageVo<List<MallCartVo>> findPage(MallCartQuery query) {
        log.info("分页查询购物车：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Specification<MallCart> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query.getUserId() != null) predicates.add(cb.equal(root.get("userId"), query.getUserId()));
            if (query.getShopId() != null) predicates.add(cb.equal(root.get("shopId"), query.getShopId()));
            if (query.getProductId() != null) predicates.add(cb.equal(root.get("productId"), query.getProductId()));
            if (query.getSkuId() != null) predicates.add(cb.equal(root.get("skuId"), query.getSkuId()));
            if (query.getSelected() != null) predicates.add(cb.equal(root.get("selected"), query.getSelected()));
            return predicates;
        });
        Page<MallCart> page = mallCartRepository.findAll(spec, pageable);
        return PageVo.of(page.getTotalElements(), mallCartMapper.toVo(page.getContent()));
    }

    @Override
    public List<MallCartVo> findAll() {
        return mallCartMapper.toVo(mallCartRepository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<MallCartVo> findByUserId(Long userId) {
        return mallCartMapper.toVo(mallCartRepository.findByUserId(userId));
    }
}
