package com.fastproject.mall.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.mall.domain.MallPickupShop;
import com.fastproject.mall.mapper.MallPickupShopMapper;
import com.fastproject.mall.repository.db.MallPickupShopRepository;
import com.fastproject.mall.service.MallPickupShopService;
import com.fastproject.mall.vo.pickupshop.MallPickupShopCreate;
import com.fastproject.mall.vo.pickupshop.MallPickupShopQuery;
import com.fastproject.mall.vo.pickupshop.MallPickupShopUpdate;
import com.fastproject.mall.vo.pickupshop.MallPickupShopVo;
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

/**
 * 自提门店 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MallPickupShopServiceImpl implements MallPickupShopService {

    private final MallPickupShopRepository mallPickupShopRepository;
    private final QueryHelp<MallPickupShop> queryHelp;
    private final MallPickupShopMapper mallPickupShopMapper;

    @Override
    public Long save(MallPickupShopCreate create) {
        log.info("保存自提门店：{}", create);
        if (StringUtils.hasText(create.getName()) && mallPickupShopRepository.existsByName(create.getName())) {
            throw new BusinessException("自提门店名称已存在");
        }
        if (StringUtils.hasText(create.getCode()) && mallPickupShopRepository.existsByCode(create.getCode())) {
            throw new BusinessException("自提门店编码已存在");
        }
        MallPickupShop entity = mallPickupShopMapper.toPickupShop(create);
        mallPickupShopRepository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MallPickupShopUpdate update) {
        log.info("修改自提门店：{}", update);
        MallPickupShop entity = mallPickupShopRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("自提门店不存在"));
        if (StringUtils.hasText(update.getName())
                && mallPickupShopRepository.existsByNameAndIdNot(update.getName(), update.getId())) {
            throw new BusinessException("自提门店名称已存在");
        }
        if (StringUtils.hasText(update.getCode())
                && mallPickupShopRepository.existsByCodeAndIdNot(update.getCode(), update.getId())) {
            throw new BusinessException("自提门店编码已存在");
        }
        mallPickupShopMapper.updatePickupShopFromDto(update, entity);
        mallPickupShopRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        log.info("删除自提门店：{}", id);
        mallPickupShopRepository.findById(id).orElseThrow(() -> new BusinessException("自提门店不存在"));
        mallPickupShopRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除自提门店：{}", ids);
        mallPickupShopRepository.deleteAllById(ids);
    }

    @Override
    public MallPickupShopVo findById(Long id) {
        log.info("根据ID查询自提门店：{}", id);
        MallPickupShop entity = mallPickupShopRepository.findById(id).orElse(null);
        return entity != null ? mallPickupShopMapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<MallPickupShopVo>> findPage(MallPickupShopQuery query) {
        log.info("分页查询自提门店：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("sort").ascending());
        Specification<MallPickupShop> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getName())) predicates.add(cb.like(root.get("name"), "%" + query.getName() + "%"));
            if (query.getShopId() != null) predicates.add(cb.equal(root.get("shopId"), query.getShopId()));
            if (StringUtils.hasText(query.getProvince())) predicates.add(cb.equal(root.get("province"), query.getProvince()));
            if (StringUtils.hasText(query.getCity())) predicates.add(cb.equal(root.get("city"), query.getCity()));
            if (query.getStatus() != null) predicates.add(cb.equal(root.get("status"), query.getStatus()));
            return predicates;
        });
        Page<MallPickupShop> page = mallPickupShopRepository.findAll(spec, pageable);
        return PageVo.of(page.getTotalElements(), mallPickupShopMapper.toVo(page.getContent()));
    }

    @Override
    public List<MallPickupShopVo> findAll() {
        return mallPickupShopMapper.toVo(mallPickupShopRepository.findAll(Sort.by("sort").ascending()));
    }

    @Override
    public List<MallPickupShopVo> selectAll() {
        Specification<MallPickupShop> spec = (root, query, cb) -> cb.equal(root.get("status"), StatusEnum.NORMAL.getCode());
        return mallPickupShopMapper.toVo(mallPickupShopRepository.findAll(spec, Sort.by("sort").ascending()));
    }
}
