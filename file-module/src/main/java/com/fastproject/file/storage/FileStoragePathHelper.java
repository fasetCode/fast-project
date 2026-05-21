package com.fastproject.file.storage;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FileStoragePathHelper {

    public String getStorageKey(String fileNameOrPath) {
        return normalizeStorageKey(fileNameOrPath);
    }

    public String getStorageKey(String filePath, Long fileId) {
        if (StringUtils.hasText(filePath)) {
            return normalizeStorageKey(filePath);
        }
        return fileId == null ? "" : String.valueOf(fileId);
    }

    public String normalizeRelativePath(String fileNameOrPath) {
        String key = normalizeStorageKey(fileNameOrPath);
        return key.isEmpty() ? "/" : "/" + key;
    }

    public String joinPrefix(String prefix, String normalizedPath) {
        String normalizedPrefix = prefix.replace("\\", "/");
        while (normalizedPrefix.endsWith("/")) {
            normalizedPrefix = normalizedPrefix.substring(0, normalizedPrefix.length() - 1);
        }
        return normalizedPrefix + normalizedPath;
    }

    public boolean isAbsoluteUrl(String value) {
        return StringUtils.hasText(value)
                && (value.startsWith("http://") || value.startsWith("https://"));
    }

    private String normalizeStorageKey(String fileNameOrPath) {
        if (!StringUtils.hasText(fileNameOrPath)) {
            return "";
        }

        String normalized = fileNameOrPath.trim().replace("\\", "/");
        while (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }
        return normalized;
    }
}
