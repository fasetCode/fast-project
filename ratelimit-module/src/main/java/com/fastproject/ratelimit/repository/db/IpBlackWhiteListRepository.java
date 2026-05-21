package com.fastproject.ratelimit.repository.db;

import com.fastproject.ratelimit.domain.IpBlackWhiteList;
import com.fastproject.ratelimit.enums.BlackWhiteListType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * IP黑白名单配置 Repository
 */
@Repository
public interface IpBlackWhiteListRepository extends JpaRepository<IpBlackWhiteList, Long>, JpaSpecificationExecutor<IpBlackWhiteList> {

    /**
     * 判断IP地址是否存在
     */
    boolean existsByIpAddress(String ipAddress);

    /**
     * 判断IP地址是否存在（排除指定ID）
     */
    boolean existsByIpAddressAndIdNot(String ipAddress, Long id);

    /**
     * 根据应用代码和IP地址和名单类型查询配置
     */
    IpBlackWhiteList findByAppCodeAndIpAddressAndListType(String appCode, String ipAddress, BlackWhiteListType listType);
}
