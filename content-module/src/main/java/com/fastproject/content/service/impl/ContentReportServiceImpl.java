package com.fastproject.content.service.impl;

import com.fastproject.content.domain.ContentReport;
import com.fastproject.content.mapper.ContentReportMapper;
import com.fastproject.content.repository.db.ContentReportRepository;
import com.fastproject.content.service.ContentReportService;
import com.fastproject.content.vo.report.ContentReportCreate;
import com.fastproject.content.vo.report.ContentReportQuery;
import com.fastproject.content.vo.report.ContentReportUpdate;
import com.fastproject.content.vo.report.ContentReportVo;
import com.fastproject.exception.BusinessException;
import com.fastproject.utils.vo.PageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentReportServiceImpl implements ContentReportService {

    private final ContentReportRepository repository;
    private final ContentReportMapper mapper;

    @Override
    public Long save(ContentReportCreate create) {
        ContentReport entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContentReportUpdate update) {
        ContentReport entity = repository.findById(update.getId()).orElseThrow(() -> new BusinessException("数据不存在"));
        mapper.updateFromDto(update, entity);
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new BusinessException("数据不存在"));
        repository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public ContentReportVo findById(Long id) {
        ContentReport entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<ContentReportVo>> findPage(ContentReportQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<ContentReport> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), mapper.toVo(page.getContent()));
    }

    @Override
    public List<ContentReportVo> findAll() {
        return mapper.toVo(repository.findAll(Sort.by("id").descending()));
    }

    @Override
    public List<ContentReportVo> selectAll() {
        return findAll();
    }
}
