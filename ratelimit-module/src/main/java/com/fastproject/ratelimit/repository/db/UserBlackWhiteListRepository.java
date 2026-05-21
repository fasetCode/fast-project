package com.fastproject.ratelimit.repository.db;

import com.fastproject.ratelimit.domain.UserBlackWhiteList;
import com.fastproject.ratelimit.enums.BlackWhiteListType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 用户黑白名单配置 Repository
 */
@Repository
public interface UserBlackWhiteListRepository extends JpaRepository<UserBlackWhiteList, Long>, JpaSpecificationExecutor<UserBlackWhiteList> {

    /**
     * 判断用户ID是否存在
     */
    boolean existsByUserId(Long userId);

    /**
     * 判断用户ID是否存在（排除指定ID）
     */
    boolean existsByUserIdAndIdNot(Long userId, Long id);

    /**
     * 根据应用代码和用户ID和名单类型查询配置
     */
    UserBlackWhiteList findByAppCodeAndUserIdAndListType(String appCode, Long userId, BlackWhiteListType listType);
}
