package com.fastproject.system.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.system.domain.SysDictData;
import com.fastproject.system.domain.SysDictType;
import com.fastproject.system.mapper.SysDictTypeMapper;
import com.fastproject.system.repository.db.SysDictDataRepository;
import com.fastproject.system.repository.db.SysDictTypeRepository;
import com.fastproject.system.service.SysDictDataService;
import com.fastproject.system.service.SysDictTypeService;
import com.fastproject.system.vo.dictdata.Dict;
import com.fastproject.system.vo.dictdata.Dict.DictData;
import com.fastproject.system.vo.dicttype.SysDictTypeUpdate;
import com.fastproject.system.vo.dicttype.SysDictTypeCreate;
import com.fastproject.system.vo.dicttype.SysDictTypeQuery;
import com.fastproject.system.vo.dicttype.SysDictTypeVo;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典类型 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysDictTypeServiceImpl implements SysDictTypeService {

    private final SysDictTypeRepository sysDictTypeRepository;
    private final QueryHelp<SysDictType> queryHelp;
    private final SysDictTypeMapper sysDictTypeMapper;
    private final SysDictDataService sysDictDataService;
    private final SysDictDataRepository sysDictDataRepository;

    @Override
    public Long save(SysDictTypeCreate create) {
        log.info("保存字典类型信息：{}", create);

        // 检查字典类型是否已存在
        if (sysDictTypeRepository.existsByType(create.getType())) {
            throw new BusinessException("字典类型已存在");
        }

        SysDictType dictType = sysDictTypeMapper.toDictType(create);
        sysDictTypeRepository.save(dictType);
        return dictType.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictTypeUpdate sysDictTypeUpdate) {
        log.info("修改字典类型信息：{}", sysDictTypeUpdate);
        SysDictType dictType = sysDictTypeRepository.findById(sysDictTypeUpdate.getId())
                .orElseThrow(() -> new BusinessException("字典类型不存在"));

        // 检查字典类型是否已存在（排除自身）
        if (sysDictTypeRepository.existsByTypeAndIdNot(sysDictTypeUpdate.getType(), sysDictTypeUpdate.getId())) {
            throw new BusinessException("字典类型已存在");
        }

        sysDictTypeMapper.updateDictTypeFromDto(sysDictTypeUpdate, dictType);
        sysDictTypeRepository.save(dictType);
    }

    @Override
    public void delete(Long id) {
        log.info("删除字典类型信息：{}", id);
        sysDictTypeRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除字典类型信息：{}", ids);
        sysDictTypeRepository.deleteAllById(ids);
    }

    @Override
    public SysDictTypeVo findById(Long id) {
        log.info("根据ID查询字典类型信息：{}", id);
        SysDictType dictType = sysDictTypeRepository.findById(id).orElse(null);
        if (dictType != null) {
            return sysDictTypeMapper.toVo(dictType);
        }
        return null;
    }

    @Override
    public PageVo<List<SysDictTypeVo>> findPage(SysDictTypeQuery sysDictTypeQuery) {
        log.info("分页查询字典类型信息：{}", sysDictTypeQuery);
        Pageable pageable = PageRequest.of(sysDictTypeQuery.getPage(), sysDictTypeQuery.getPageSize(), Sort.by("id").descending());

        Specification<SysDictType> spec = queryHelp.getWhere(sysDictTypeQuery, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(sysDictTypeQuery.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + sysDictTypeQuery.getName() + "%"));
            }
            if (StringUtils.hasText(sysDictTypeQuery.getType())) {
                predicates.add(cb.like(root.get("type"), "%" + sysDictTypeQuery.getType() + "%"));
            }
            if (sysDictTypeQuery.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), sysDictTypeQuery.getStatus()));
            }
            return predicates;
        });

        Page<SysDictType> page = sysDictTypeRepository.findAll(spec, pageable);
        List<SysDictTypeVo> list = sysDictTypeMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public List<SysDictTypeVo> findAll() {
        List<SysDictType> all = sysDictTypeRepository.findAll(Sort.by("id").descending());
        return sysDictTypeMapper.toVo(all);
    }

    @Override
    public List<SysDictTypeVo> selectAll() {
        // 只查询正常状态的字典类型
        Specification<SysDictType> spec = (root, query, cb) -> cb.equal(root.get("status"), 1);
        List<SysDictType> all = sysDictTypeRepository.findAll(spec, Sort.by("id").descending());
        return sysDictTypeMapper.toVo(all);
    }

    @Override
    public List<Dict> findAllDict() {
        // 一次性查询所有字典类型（只查正常状态）
        List<SysDictTypeVo> dictTypes = selectAll();

        // 一次性查询所有字典数据（包含所有状态），按 sort 字段升序排序
        List<SysDictData> allDictData = sysDictDataRepository.findAll(Sort.by("sort").ascending());

        // 使用流式 API 按 dictTypeId 分组，并筛选出正常状态(status=1)的数据
        Map<Long, List<SysDictData>> dictDataMap = allDictData.stream()
                .filter(data -> data.getStatus() != null && data.getStatus().equals(StatusEnum.NORMAL.getCode()))
                .collect(Collectors.groupingBy(SysDictData::getDictTypeId));

        // 构建结果
        List<Dict> result = new ArrayList<>();
        for (SysDictTypeVo dictType : dictTypes) {
            Dict dict = new Dict();
            dict.setType(dictType.getType());

            List<SysDictData> dictDataList = dictDataMap.get(dictType.getId());
            if (dictDataList != null && !dictDataList.isEmpty()) {
                List<DictData> dataList = dictDataList.stream()
                        .map(dataVo -> {
                            DictData dictData = new DictData();
                            dictData.setLabel(dataVo.getLabel());
                            dictData.setValue(dataVo.getValue());
                            return dictData;
                        })
                        .collect(Collectors.toList());
                dict.setData(dataList);
            } else {
                dict.setData(new ArrayList<>());
            }
            result.add(dict);
        }

        return result;
    }
}
