package com.fastproject.system.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.system.domain.SysDictData;
import com.fastproject.system.mapper.SysDictDataMapper;
import com.fastproject.system.repository.db.SysDictDataRepository;
import com.fastproject.system.service.SysDictDataService;
import com.fastproject.system.vo.dictdata.SysDictDataUpdate;
import com.fastproject.system.vo.dictdata.SysDictDataCreate;
import com.fastproject.system.vo.dictdata.SysDictDataQuery;
import com.fastproject.system.vo.dictdata.SysDictDataVo;
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
 * 字典数据 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysDictDataServiceImpl implements SysDictDataService {

    private final SysDictDataRepository sysDictDataRepository;
    private final QueryHelp<SysDictData> queryHelp;
    private final SysDictDataMapper sysDictDataMapper;

    @Override
    public Long save(SysDictDataCreate create) {
        log.info("保存字典数据信息：{}", create);

        // 检查字典值是否已存在
        if (sysDictDataRepository.existsByValueAndDictTypeId(create.getValue(), create.getDictTypeId())) {
            throw new BusinessException("字典值已存在");
        }

        SysDictData dictData = sysDictDataMapper.toDictData(create);
        sysDictDataRepository.save(dictData);
        return dictData.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictDataUpdate sysDictDataUpdate) {
        log.info("修改字典数据信息：{}", sysDictDataUpdate);
        SysDictData dictData = sysDictDataRepository.findById(sysDictDataUpdate.getId())
                .orElseThrow(() -> new BusinessException("字典数据不存在"));

        // 检查字典值是否已存在（排除自身）
        if (sysDictDataRepository.existsByValueAndDictTypeIdAndIdNot(sysDictDataUpdate.getValue(), sysDictDataUpdate.getDictTypeId(), sysDictDataUpdate.getId())) {
            throw new BusinessException("字典值已存在");
        }

        sysDictDataMapper.updateDictDataFromDto(sysDictDataUpdate, dictData);
        sysDictDataRepository.save(dictData);
    }

    @Override
    public void delete(Long id) {
        log.info("删除字典数据信息：{}", id);
        sysDictDataRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除字典数据信息：{}", ids);
        sysDictDataRepository.deleteAllById(ids);
    }

    @Override
    public SysDictDataVo findById(Long id) {
        log.info("根据ID查询字典数据信息：{}", id);
        SysDictData dictData = sysDictDataRepository.findById(id).orElse(null);
        if (dictData != null) {
            return sysDictDataMapper.toVo(dictData);
        }
        return null;
    }

    @Override
    public PageVo<List<SysDictDataVo>> findPage(SysDictDataQuery sysDictDataQuery) {
        log.info("分页查询字典数据信息：{}", sysDictDataQuery);
        Pageable pageable = PageRequest.of(sysDictDataQuery.getPage(), sysDictDataQuery.getPageSize(), Sort.by("sort").ascending());

        Specification<SysDictData> spec = queryHelp.getWhere(sysDictDataQuery, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(sysDictDataQuery.getLabel())) {
                predicates.add(cb.like(root.get("label"), "%" + sysDictDataQuery.getLabel() + "%"));
            }
            if (StringUtils.hasText(sysDictDataQuery.getValue())) {
                predicates.add(cb.equal(root.get("value"), sysDictDataQuery.getValue()));
            }
            if (sysDictDataQuery.getDictTypeId() != null) {
                predicates.add(cb.equal(root.get("dictTypeId"), sysDictDataQuery.getDictTypeId()));
            }
            if (sysDictDataQuery.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), sysDictDataQuery.getStatus()));
            }
            return predicates;
        });

        Page<SysDictData> page = sysDictDataRepository.findAll(spec, pageable);
        List<SysDictDataVo> list = sysDictDataMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public List<SysDictDataVo> findByDictTypeId(Long dictTypeId) {
        List<SysDictData> list = sysDictDataRepository.findByDictTypeId(dictTypeId);
        return sysDictDataMapper.toVo(list);
    }
}
