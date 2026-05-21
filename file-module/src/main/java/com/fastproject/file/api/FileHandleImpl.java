package com.fastproject.file.api;

import com.fastproject.file.service.FileConfigService;
import com.fastproject.file.service.FileDomainService;
import com.fastproject.file.service.FileInfoService;
import com.fastproject.file.storage.FileAccessUrlResolver;
import com.fastproject.file.vo.FileUrlVo;
import com.fastproject.file.vo.config.FileConfigVo;
import com.fastproject.file.vo.domain.FileDomainVo;
import com.fastproject.file.vo.info.FileInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileHandleImpl implements FileHandle {

    private final FileConfigService fileConfigService;
    private final FileInfoService fileInfoService;
    private final FileDomainService fileDomainService;
    private final FileAccessUrlResolver fileAccessUrlResolver;

    @Override
    public String getUrl(String id) {
        FileInfoVo fileInfo = fileInfoService.findById(Long.parseLong(id));
        return buildUrl(fileInfo);
    }

    @Override
    public Set<FileUrlVo> getUrls(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Set.of();
        }

        // 批量查询文件信息
        List<Long> idList = ids.stream()
                .map(Long::parseLong)
                .toList();
        List<FileInfoVo> fileInfos = fileInfoService.findByIds(idList);

        if (fileInfos.isEmpty()) {
            return Set.of();
        }

        // 按 configId 分组，批量查询配置和域名
        Map<Long, List<FileInfoVo>> configGroup = fileInfos.stream()
                .collect(Collectors.groupingBy(FileInfoVo::getConfigId));

        // 批量查询配置
        Map<Long, FileConfigVo> configMap = configGroup.keySet().stream()
                .map(fileConfigService::findById)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(FileConfigVo::getId, Function.identity()));

        // 批量查询域名并按 configId 分组
        Map<Long, List<FileDomainVo>> domainMap = configGroup.keySet().stream()
                .flatMap(configId -> fileDomainService.findAllByConfigId(configId).stream())
                .collect(Collectors.groupingBy(FileDomainVo::getConfigId));

        // 组装结果
        Set<FileUrlVo> result = new HashSet<>();
        for (FileInfoVo fileInfo : fileInfos) {
            String url = buildUrl(fileInfo, configMap.get(fileInfo.getConfigId()), domainMap.get(fileInfo.getConfigId()));
            if (url != null) {
                FileUrlVo vo = new FileUrlVo();
                vo.setId(fileInfo.getId().toString());
                vo.setUrl(url);
                result.add(vo);
            }
        }

        return result;
    }

    /**
     * 构建文件访问URL
     */
    private String buildUrl(FileInfoVo fileInfo) {
        if (fileInfo == null) {
            return null;
        }
        FileConfigVo config = fileConfigService.findById(fileInfo.getConfigId());
        List<FileDomainVo> domains = fileDomainService.findAllByConfigId(fileInfo.getConfigId());
        return buildUrl(fileInfo, config, domains);
    }

    private String buildUrl(FileInfoVo fileInfo, FileConfigVo config, List<FileDomainVo> domains) {
        if (fileInfo == null || config == null) {
            return null;
        }
        return fileAccessUrlResolver.resolve(fileInfo, config, domains);
    }
}
