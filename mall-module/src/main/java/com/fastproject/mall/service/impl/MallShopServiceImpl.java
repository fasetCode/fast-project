package com.fastproject.mall.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.mall.domain.MallShop;
import com.fastproject.mall.mapper.MallShopMapper;
import com.fastproject.mall.repository.db.MallShopRepository;
import com.fastproject.mall.service.MallShopService;
import com.fastproject.mall.vo.shop.MallShopCreate;
import com.fastproject.mall.vo.shop.MallShopQuery;
import com.fastproject.mall.vo.shop.MallShopUpdate;
import com.fastproject.mall.vo.shop.MallShopVo;
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
 * 商城店铺 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MallShopServiceImpl implements MallShopService {

    private final MallShopRepository mallShopRepository;
    private final QueryHelp<MallShop> queryHelp;
    private final MallShopMapper mallShopMapper;

    @Override
    public Long save(MallShopCreate create) {
        log.info("保存店铺信息：{}", create);
        if (StringUtils.hasText(create.getName()) && mallShopRepository.existsByName(create.getName())) {
            throw new BusinessException("店铺名称已存在");
        }
        if (StringUtils.hasText(create.getCode()) && mallShopRepository.existsByCode(create.getCode())) {
            throw new BusinessException("店铺编码已存在");
        }
        MallShop shop = mallShopMapper.toShop(create);
        mallShopRepository.save(shop);
        return shop.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MallShopUpdate update) {
        log.info("修改店铺信息：{}", update);
        MallShop shop = mallShopRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("店铺不存在"));
        if (StringUtils.hasText(update.getName()) && mallShopRepository.existsByNameAndIdNot(update.getName(), update.getId())) {
            throw new BusinessException("店铺名称已存在");
        }
        if (StringUtils.hasText(update.getCode()) && mallShopRepository.existsByCodeAndIdNot(update.getCode(), update.getId())) {
            throw new BusinessException("店铺编码已存在");
        }
        mallShopMapper.updateShopFromDto(update, shop);
        mallShopRepository.save(shop);
    }

    @Override
    public void delete(Long id) {
        log.info("删除店铺信息：{}", id);
        mallShopRepository.findById(id)
                .orElseThrow(() -> new BusinessException("店铺不存在"));
        mallShopRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除店铺信息：{}", ids);
        mallShopRepository.deleteAllById(ids);
    }

    @Override
    public MallShopVo findById(Long id) {
        log.info("根据ID查询店铺信息：{}", id);
        MallShop shop = mallShopRepository.findById(id).orElse(null);
        return shop != null ? mallShopMapper.toVo(shop) : null;
    }

    @Override
    public PageVo<List<MallShopVo>> findPage(MallShopQuery query) {
        log.info("分页查询店铺信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("sort").ascending());

        Specification<MallShop> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + query.getName() + "%"));
            }
            if (StringUtils.hasText(query.getCode())) {
                predicates.add(cb.equal(root.get("code"), query.getCode()));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            return predicates;
        });

        Page<MallShop> page = mallShopRepository.findAll(spec, pageable);
        List<MallShopVo> list = mallShopMapper.toVo(page.getContent());
        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public List<MallShopVo> findAll() {
        List<MallShop> all = mallShopRepository.findAll(Sort.by("sort").ascending());
        return mallShopMapper.toVo(all);
    }

    @Override
    public List<MallShopVo> selectAll() {
        Specification<MallShop> spec = (root, query, cb) -> cb.equal(root.get("status"), StatusEnum.NORMAL.getCode());
        List<MallShop> all = mallShopRepository.findAll(spec, Sort.by("sort").ascending());
        return mallShopMapper.toVo(all);
    }
}
