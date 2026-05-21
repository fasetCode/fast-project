package com.fastproject.file.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.file.domain.FileInfo;
import com.fastproject.file.mapper.FileInfoMapper;
import com.fastproject.file.repository.db.FileInfoRepository;
import com.fastproject.file.service.FileConfigService;
import com.fastproject.file.service.FileInfoService;
import com.fastproject.file.storage.FileStorageContext;
import com.fastproject.file.vo.config.FileConfigVo;
import com.fastproject.file.vo.info.FileInfoCreate;
import com.fastproject.file.vo.info.FileInfoQuery;
import com.fastproject.file.vo.info.FileInfoUpdate;
import com.fastproject.file.vo.info.FileInfoVo;
import com.fastproject.file.vo.info.FileTypeStatVo;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件信息 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FileInfoServiceImpl implements FileInfoService {

    private final FileInfoRepository fileInfoRepository;
    private final QueryHelp<FileInfo> queryHelp;
    private final FileInfoMapper fileInfoMapper;
    private final FileConfigService fileConfigService;
    private final FileStorageContext fileStorageContext;

    @Override
    public Long save(FileInfoCreate create) {
        log.info("保存文件信息：{}", create);

        // 检查文件MD5是否已存在（可选，根据业务需求）
        if (StringUtils.hasText(create.getFileMd5()) && fileInfoRepository.existsByFileMd5(create.getFileMd5())) {
            throw new BusinessException("文件已存在");
        }

        FileInfo fileInfo = fileInfoMapper.toFileInfo(create);
        fileInfoRepository.save(fileInfo);
        return fileInfo.getId();
    }

    @Override
    public List<FileTypeStatVo> getTypeStats() {
        log.info("获取文件类型统计信息");
        List<FileInfo> allFiles = fileInfoRepository.findAll();
        long totalCount = allFiles.size();
        if (totalCount == 0) {
            return new ArrayList<>();
        }

        Map<String, List<FileInfo>> groupedByType = allFiles.stream()
                .filter(f -> StringUtils.hasText(f.getFileType()))
                .collect(Collectors.groupingBy(f -> f.getFileType().toLowerCase()));

        List<FileTypeStatVo> stats = new ArrayList<>();
        for (Map.Entry<String, List<FileInfo>> entry : groupedByType.entrySet()) {
            String type = entry.getKey();
            List<FileInfo> files = entry.getValue();

            long count = files.size();
            long totalSize = files.stream()
                    .mapToLong(f -> f.getFileSize() != null ? f.getFileSize() : 0L)
                    .sum();

            FileTypeStatVo vo = new FileTypeStatVo();
            vo.setName(type);

            // 数量占比保留两位小数
            double ratio = (double) count / totalCount * 100;
            vo.setPlatformRatio(BigDecimal.valueOf(ratio).setScale(2, RoundingMode.HALF_UP).doubleValue());

            // 空间大小 MB 保留两位小数
            double spaceMb = (double) totalSize / 1024 / 1024;
            vo.setFileSpace(BigDecimal.valueOf(spaceMb).setScale(2, RoundingMode.HALF_UP).doubleValue());

            stats.add(vo);
        }

        // 按数量占比降序排序
        stats.sort((a, b) -> Double.compare(b.getPlatformRatio(), a.getPlatformRatio()));
        return stats;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FileInfoUpdate update) {
        log.info("修改文件信息：{}", update);
        FileInfo fileInfo = fileInfoRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("文件不存在"));

        fileInfoMapper.updateFileInfoFromDto(update, fileInfo);
        fileInfoRepository.save(fileInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除文件信息：{}", id);
        FileInfo fileInfo = fileInfoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("文件不存在"));

        deleteStoredFile(fileInfo);
        fileInfoRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除文件信息：{}", ids);
        if (ids == null || ids.isEmpty()) {
            return;
        }

        List<FileInfo> fileInfos = fileInfoRepository.findAllById(ids);
        if (fileInfos.size() != ids.size()) {
            throw new BusinessException("部分文件不存在，无法批量删除");
        }

        for (FileInfo fileInfo : fileInfos) {
            deleteStoredFile(fileInfo);
        }

        fileInfoRepository.deleteAll(fileInfos);
    }

    @Override
    public FileInfoVo findById(Long id) {
        log.info("根据ID查询文件信息：{}", id);
        FileInfo fileInfo = fileInfoRepository.findById(id).orElse(null);
        if (fileInfo != null) {
            return fileInfoMapper.toVo(fileInfo);
        }
        return null;
    }

    @Override
    public List<FileInfoVo> findByIds(List<Long> ids) {
        log.info("根据ID列表批量查询文件信息：{}", ids);
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        List<FileInfo> fileInfos = fileInfoRepository.findAllById(ids);
        return fileInfoMapper.toVo(fileInfos);
    }

    @Override
    public PageVo<List<FileInfoVo>> findPage(FileInfoQuery query) {
        log.info("分页查询文件信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<FileInfo> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getFileName())) {
                predicates.add(cb.like(root.get("fileName"), "%" + query.getFileName() + "%"));
            }
            if (StringUtils.hasText(query.getFileType())) {
                predicates.add(cb.equal(root.get("fileType"), query.getFileType()));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            if (StringUtils.hasText(query.getFileMd5())) {
                predicates.add(cb.equal(root.get("fileMd5"), query.getFileMd5()));
            }
            return predicates;
        });

        Page<FileInfo> page = fileInfoRepository.findAll(spec, pageable);
        List<FileInfoVo> list = fileInfoMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public FileInfoVo findByFileMd5(String fileMd5) {
        log.info("根据文件MD5查询文件信息：{}", fileMd5);
        FileInfo fileInfo = fileInfoRepository.findByFileMd5(fileMd5).orElse(null);
        if (fileInfo != null) {
            return fileInfoMapper.toVo(fileInfo);
        }
        return null;
    }

    private void deleteStoredFile(FileInfo fileInfo) {
        String storageKey = resolveStorageKey(fileInfo);
        if (!StringUtils.hasText(storageKey)) {
            log.warn("文件未记录存储路径，跳过源文件删除：id={}", fileInfo.getId());
            return;
        }

        if (fileInfo.getConfigId() == null) {
            throw new BusinessException("文件存储配置不存在，无法删除源文件");
        }

        FileConfigVo config = fileConfigService.findById(fileInfo.getConfigId());
        if (config == null) {
            throw new BusinessException("文件存储配置不存在，无法删除源文件");
        }

        boolean deleted = fileStorageContext.deleteFile(config, storageKey);
        if (!deleted) {
            throw new BusinessException("删除源文件失败：" + fileInfo.getFileName());
        }
    }

    private String resolveStorageKey(FileInfo fileInfo) {
        if (StringUtils.hasText(fileInfo.getFilePath())) {
            return fileInfo.getFilePath();
        }
        if (StringUtils.hasText(fileInfo.getAccessPath())) {
            return fileInfo.getAccessPath();
        }
        return null;
    }
}
