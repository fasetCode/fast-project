package com.fastproject.utils.utils;

import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;


/**
 * GraalVM Native Image 兼容的 JSON <-> Bean 工具类
 */

@Component
public class JsonUtils {
    private static ObjectMapper mapper;

    public JsonUtils(ObjectMapper objectMapper) {
        JsonUtils.mapper = objectMapper;
    }
    /**
     * Bean -> JSON 字符串
     */
    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("json serialize error", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("json parse error", e);
        }
    }
}