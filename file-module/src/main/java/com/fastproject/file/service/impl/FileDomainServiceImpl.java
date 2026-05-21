package com.fastproject.file.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.file.domain.FileDomain;
import com.fastproject.file.mapper.FileDomainMapper;
import com.fastproject.file.repository.db.FileDomainRepository;
import com.fastproject.file.service.FileDomainService;
import com.fastproject.file.vo.domain.FileDomainCreate;
import com.fastproject.file.vo.domain.FileDomainQuery;
import com.fastproject.file.vo.domain.FileDomainUpdate;
import com.fastproject.file.vo.domain.FileDomainVo;
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
 * 文件域名 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FileDomainServiceImpl implements FileDomainService {

    private final FileDomainRepository fileDomainRepository;
    private final QueryHelp<FileDomain> queryHelp;
    private final FileDomainMapper fileDomainMapper;

    @Override
    public Long save(FileDomainCreate create) {
        log.info("保存文件域名信息：{}", create);

        // 检查域名是否已存在
        if (fileDomainRepository.existsByDomain(create.getDomain())) {
            throw new BusinessException("域名已存在");
        }

        FileDomain domain = fileDomainMapper.toDomain(create);
        fileDomainRepository.save(domain);
        return domain.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FileDomainUpdate update) {
        log.info("修改文件域名信息：{}", update);
        FileDomain domain = fileDomainRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("域名不存在"));

        // 检查域名是否已存在（排除自身）
        if (fileDomainRepository.existsByDomainAndIdNot(update.getDomain(), update.getId())) {
            throw new BusinessException("域名已存在");
        }

        fileDomainMapper.updateDomainFromDto(update, domain);
        fileDomainRepository.save(domain);
    }

    @Override
    public void delete(Long id) {
        log.info("删除文件域名信息：{}", id);
        fileDomainRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除文件域名信息：{}", ids);
        fileDomainRepository.deleteAllById(ids);
    }

    @Override
    public FileDomainVo findById(Long id) {
        log.info("根据ID查询文件域名信息：{}", id);
        FileDomain domain = fileDomainRepository.findById(id).orElse(null);
        if (domain != null) {
            return fileDomainMapper.toVo(domain);
        }
        return null;
    }

    @Override
    public PageVo<List<FileDomainVo>> findPage(FileDomainQuery query) {
        log.info("分页查询文件域名信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<FileDomain> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query.getConfigId() != null) {
                predicates.add(cb.equal(root.get("configId"), query.getConfigId()));
            }
            if (StringUtils.hasText(query.getDomain())) {
                predicates.add(cb.like(root.get("domain"), "%" + query.getDomain() + "%"));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            return predicates;
        });

        Page<FileDomain> page = fileDomainRepository.findAll(spec, pageable);
        List<FileDomainVo> list = fileDomainMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public FileDomainVo findByDomain(String domain) {
        log.info("根据域名查询文件域名信息：{}", domain);
        FileDomain fileDomain = fileDomainRepository.findByDomain(domain);
        if (fileDomain != null) {
            return fileDomainMapper.toVo(fileDomain);
        }
        return null;
    }

    @Override
    public List<FileDomainVo> findByConfigId(Long configId) {
        log.info("根据配置ID查询文件域名列表：{}", configId);
        List<FileDomain> domains = fileDomainRepository.findByConfigId(configId);
        return fileDomainMapper.toVo(domains);
    }

    @Override
    public List<FileDomainVo> findAllEnabled() {
        log.info("查询所有启用的文件域名");
        List<FileDomain> domains = fileDomainRepository.findByStatusOrderByIdDesc(1);
        return fileDomainMapper.toVo(domains);
    }

    @Override
    public List<FileDomainVo> findAllByConfigId(Long configId) {
        List<FileDomain> byConfigIdAndStatus = fileDomainRepository.findByConfigIdAndStatus(configId, StatusEnum.NORMAL.getCode());
        return fileDomainMapper.toVo(byConfigIdAndStatus);
    }
}
