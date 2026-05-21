package com.fastproject.mall.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.mall.domain.MallProductBrand;
import com.fastproject.mall.mapper.MallProductBrandMapper;
import com.fastproject.mall.repository.db.MallProductBrandRepository;
import com.fastproject.mall.service.MallProductBrandService;
import com.fastproject.mall.vo.brand.MallProductBrandCreate;
import com.fastproject.mall.vo.brand.MallProductBrandQuery;
import com.fastproject.mall.vo.brand.MallProductBrandUpdate;
import com.fastproject.mall.vo.brand.MallProductBrandVo;
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
 * 商品品牌 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MallProductBrandServiceImpl implements MallProductBrandService {

    private final MallProductBrandRepository mallProductBrandRepository;
    private final QueryHelp<MallProductBrand> queryHelp;
    private final MallProductBrandMapper mallProductBrandMapper;

    @Override
    public Long save(MallProductBrandCreate create) {
        log.info("保存品牌：{}", create);
        if (StringUtils.hasText(create.getName()) && mallProductBrandRepository.existsByName(create.getName())) {
            throw new BusinessException("品牌名称已存在");
        }
        if (StringUtils.hasText(create.getCode()) && mallProductBrandRepository.existsByCode(create.getCode())) {
            throw new BusinessException("品牌编码已存在");
        }
        MallProductBrand brand = mallProductBrandMapper.toBrand(create);
        mallProductBrandRepository.save(brand);
        return brand.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MallProductBrandUpdate update) {
        log.info("修改品牌：{}", update);
        MallProductBrand brand = mallProductBrandRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("品牌不存在"));
        if (StringUtils.hasText(update.getName()) && mallProductBrandRepository.existsByNameAndIdNot(update.getName(), update.getId())) {
            throw new BusinessException("品牌名称已存在");
        }
        if (StringUtils.hasText(update.getCode()) && mallProductBrandRepository.existsByCodeAndIdNot(update.getCode(), update.getId())) {
            throw new BusinessException("品牌编码已存在");
        }
        mallProductBrandMapper.updateBrandFromDto(update, brand);
        mallProductBrandRepository.save(brand);
    }

    @Override
    public void delete(Long id) {
        log.info("删除品牌：{}", id);
        mallProductBrandRepository.findById(id).orElseThrow(() -> new BusinessException("品牌不存在"));
        mallProductBrandRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除品牌：{}", ids);
        mallProductBrandRepository.deleteAllById(ids);
    }

    @Override
    public MallProductBrandVo findById(Long id) {
        log.info("根据ID查询品牌：{}", id);
        MallProductBrand brand = mallProductBrandRepository.findById(id).orElse(null);
        return brand != null ? mallProductBrandMapper.toVo(brand) : null;
    }

    @Override
    public PageVo<List<MallProductBrandVo>> findPage(MallProductBrandQuery query) {
        log.info("分页查询品牌：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("sort").ascending());
        Specification<MallProductBrand> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getName())) predicates.add(cb.like(root.get("name"), "%" + query.getName() + "%"));
            if (StringUtils.hasText(query.getCode())) predicates.add(cb.equal(root.get("code"), query.getCode()));
            if (StringUtils.hasText(query.getFirstLetter())) predicates.add(cb.equal(root.get("firstLetter"), query.getFirstLetter()));
            if (query.getStatus() != null) predicates.add(cb.equal(root.get("status"), query.getStatus()));
            return predicates;
        });
        Page<MallProductBrand> page = mallProductBrandRepository.findAll(spec, pageable);
        return PageVo.of(page.getTotalElements(), mallProductBrandMapper.toVo(page.getContent()));
    }

    @Override
    public List<MallProductBrandVo> findAll() {
        return mallProductBrandMapper.toVo(mallProductBrandRepository.findAll(Sort.by("sort").ascending()));
    }

    @Override
    public List<MallProductBrandVo> selectAll() {
        Specification<MallProductBrand> spec = (root, query, cb) -> cb.equal(root.get("status"), StatusEnum.NORMAL.getCode());
        return mallProductBrandMapper.toVo(mallProductBrandRepository.findAll(spec, Sort.by("sort").ascending()));
    }
}
