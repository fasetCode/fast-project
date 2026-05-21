package com.fastproject.file.storage.vo;

import lombok.Data;

@Data
public class AlibabaFIleKeyVo {
    /**
     * 阿里云访问密钥ID
     */
    private String accessKeyId;
    /**
     * 阿里云访问密钥密钥
     */
    private String accessKeySecret;

    /**
     * 阿里云区域
     */
    private String region;

    /**
     * 阿里云存储空间
     */
    private String bucket;

    /**
     * 阿里云Endpoint
     */
    private String endpoint;

    /**
     * 是否私有桶
     */
    private Boolean privateBucket;

    /**
     * 临时访问地址有效期，单位秒
     */
    private Long urlExpireSeconds;
}
