package com.fastproject.mall.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.mall.domain.MallFreightTemplate;
import com.fastproject.mall.mapper.MallFreightTemplateMapper;
import com.fastproject.mall.repository.db.MallFreightTemplateRepository;
import com.fastproject.mall.service.MallFreightTemplateService;
import com.fastproject.mall.vo.freighttemplate.MallFreightTemplateCreate;
import com.fastproject.mall.vo.freighttemplate.MallFreightTemplateQuery;
import com.fastproject.mall.vo.freighttemplate.MallFreightTemplateUpdate;
import com.fastproject.mall.vo.freighttemplate.MallFreightTemplateVo;
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
 * 运费模板 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MallFreightTemplateServiceImpl implements MallFreightTemplateService {

    private final MallFreightTemplateRepository mallFreightTemplateRepository;
    private final QueryHelp<MallFreightTemplate> queryHelp;
    private final MallFreightTemplateMapper mallFreightTemplateMapper;

    @Override
    public Long save(MallFreightTemplateCreate create) {
        log.info("保存运费模板：{}", create);
        if (StringUtils.hasText(create.getName()) && mallFreightTemplateRepository.existsByName(create.getName())) {
            throw new BusinessException("运费模板名称已存在");
        }
        MallFreightTemplate entity = mallFreightTemplateMapper.toFreightTemplate(create);
        mallFreightTemplateRepository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MallFreightTemplateUpdate update) {
        log.info("修改运费模板：{}", update);
        MallFreightTemplate entity = mallFreightTemplateRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("运费模板不存在"));
        if (StringUtils.hasText(update.getName())
                && mallFreightTemplateRepository.existsByNameAndIdNot(update.getName(), update.getId())) {
            throw new BusinessException("运费模板名称已存在");
        }
        mallFreightTemplateMapper.updateFreightTemplateFromDto(update, entity);
        mallFreightTemplateRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        log.info("删除运费模板：{}", id);
        mallFreightTemplateRepository.findById(id).orElseThrow(() -> new BusinessException("运费模板不存在"));
        mallFreightTemplateRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除运费模板：{}", ids);
        mallFreightTemplateRepository.deleteAllById(ids);
    }

    @Override
    public MallFreightTemplateVo findById(Long id) {
        log.info("根据ID查询运费模板：{}", id);
        MallFreightTemplate entity = mallFreightTemplateRepository.findById(id).orElse(null);
        return entity != null ? mallFreightTemplateMapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<MallFreightTemplateVo>> findPage(MallFreightTemplateQuery query) {
        log.info("分页查询运费模板：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("sort").ascending());
        Specification<MallFreightTemplate> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getName())) predicates.add(cb.like(root.get("name"), "%" + query.getName() + "%"));
            if (query.getShopId() != null) predicates.add(cb.equal(root.get("shopId"), query.getShopId()));
            if (query.getChargeType() != null) predicates.add(cb.equal(root.get("chargeType"), query.getChargeType()));
            if (query.getStatus() != null) predicates.add(cb.equal(root.get("status"), query.getStatus()));
            if (query.getIsDefault() != null) predicates.add(cb.equal(root.get("isDefault"), query.getIsDefault()));
            return predicates;
        });
        Page<MallFreightTemplate> page = mallFreightTemplateRepository.findAll(spec, pageable);
        return PageVo.of(page.getTotalElements(), mallFreightTemplateMapper.toVo(page.getContent()));
    }

    @Override
    public List<MallFreightTemplateVo> findAll() {
        return mallFreightTemplateMapper.toVo(mallFreightTemplateRepository.findAll(Sort.by("sort").ascending()));
    }

    @Override
    public List<MallFreightTemplateVo> selectAll() {
        Specification<MallFreightTemplate> spec = (root, query, cb) -> cb.equal(root.get("status"), StatusEnum.NORMAL.getCode());
        return mallFreightTemplateMapper.toVo(mallFreightTemplateRepository.findAll(spec, Sort.by("sort").ascending()));
    }
}
