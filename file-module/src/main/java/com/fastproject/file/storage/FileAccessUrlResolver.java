package com.fastproject.file.storage;

import com.fastproject.file.vo.config.FileConfigVo;
import com.fastproject.file.vo.domain.FileDomainVo;
import com.fastproject.file.vo.info.FileInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 统一处理文件访问地址拼装，避免业务层关心不同存储的 URL 细节。
 */
@Component
@RequiredArgsConstructor
public class FileAccessUrlResolver {

    private final FileStorageContext fileStorageContext;
    private final FileStoragePathHelper fileStoragePathHelper;

    public String resolve(FileInfoVo fileInfo, FileConfigVo config, List<FileDomainVo> domains) {
        if (fileInfo == null) {
            return null;
        }
        return resolve(config, fileStoragePathHelper.getStorageKey(fileInfo.getFilePath(), fileInfo.getId()),
                domains, fileInfo.getAccessPath());
    }

    public String resolve(FileConfigVo config, String fileName, List<FileDomainVo> domains, String fallbackAccessPath) {
        String normalizedPath = fileStoragePathHelper.normalizeRelativePath(fileName);
        String strategyUrl = resolveByStrategy(config, fileName);
        if (fileStoragePathHelper.isAbsoluteUrl(strategyUrl)) {
            return strategyUrl;
        }

        String prefix = selectDomain(domains);

        if (StringUtils.hasText(prefix)) {
            return fileStoragePathHelper.joinPrefix(prefix, normalizedPath);
        }

        if (StringUtils.hasText(strategyUrl)) {
            return strategyUrl;
        }

        if (StringUtils.hasText(fallbackAccessPath)) {
            return fallbackAccessPath;
        }

        return normalizedPath;
    }

    public String resolve(FileConfigVo config, String fileName) {
        return resolve(config, fileName, List.of(), null);
    }

    private String selectDomain(List<FileDomainVo> domains) {
        if (CollectionUtils.isEmpty(domains)) {
            return null;
        }

        List<String> availableDomains = domains.stream()
                .map(FileDomainVo::getDomain)
                .filter(StringUtils::hasText)
                .toList();

        if (availableDomains.isEmpty()) {
            return null;
        }

        int index = ThreadLocalRandom.current().nextInt(availableDomains.size());
        return availableDomains.get(index);
    }

    private String resolveByStrategy(FileConfigVo config, String fileName) {
        if (config == null) {
            return null;
        }
        try {
            return fileStorageContext.getAccessUrl(config, fileName);
        } catch (Exception ignored) {
            if (StringUtils.hasText(config.getAccessDomain())) {
                return fileStoragePathHelper.joinPrefix(config.getAccessDomain(),
                        fileStoragePathHelper.normalizeRelativePath(fileName));
            }
            if (StringUtils.hasText(config.getRemoteUrl())) {
                return fileStoragePathHelper.joinPrefix(config.getRemoteUrl(),
                        fileStoragePathHelper.normalizeRelativePath(fileName));
            }
            return null;
        }
    }
}
