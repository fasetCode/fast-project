package com.fastproject.db;

import com.fastproject.utils.SpringContextUtil;
import com.fastproject.utils.TokenUtils;
import com.fastproject.utils.utils.JsonUtils;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import com.fastproject.utils.id.IdGenerator;

import java.time.LocalDateTime;

public class SnowflakeIdListener {

    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof BaseEntity base) {

            // ID
            if (base.getId() == null) {
                base.setId(IdGenerator.nextId());
            }

            // 创建时间
            if (base.getCreateTime() == null) {
                base.setCreateTime(LocalDateTime.now());
            }

            // 更新时间
            base.setUpdateTime(LocalDateTime.now());

            // 创建人
            if (base.getCreateBy() == null) {
                base.setCreateBy(getCurrentUserId());
            }

            // 更新人
            base.setUpdateBy(getCurrentUserId());

            // 逻辑删除
            if (base.getDeleted() == null) {
                base.setDeleted(0);
            }
        }
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof BaseEntity base) {
            base.setUpdateTime(LocalDateTime.now());
            base.setUpdateBy(getCurrentUserId());
//            base.setUpdateBy(UserContext.getUserId());
        }
    }


    private Long getCurrentUserId() {
        // 这里你自己实现，比如：
        TokenUtils tokenUtils = SpringContextUtil.getBean(TokenUtils.class);
        if (tokenUtils.getUser()!=null) {
            return tokenUtils.getUser().getUserId();
        }
        return null;
    }
}