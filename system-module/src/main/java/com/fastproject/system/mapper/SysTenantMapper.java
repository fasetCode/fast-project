package com.fastproject.system.mapper;

import com.fastproject.system.domain.SysTenant;
import com.fastproject.system.vo.tenant.SysTenantCreate;
import com.fastproject.system.vo.tenant.SysTenantUpdate;
import com.fastproject.system.vo.tenant.SysTenantVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 租户 Mapper
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SysTenantMapper {

    /**
     * 更新操作 - 忽略 null 值
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "expireTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    void updateTenantFromDto(SysTenantUpdate dto, @MappingTarget SysTenant entity);

    /**
     * Create DTO -> Entity
     */
    @Mapping(target = "expireTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    SysTenant toTenant(SysTenantCreate create);

    /**
     * Entity -> VO (单对象)
     */
    @Mapping(target = "expireTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    SysTenantVo toVo(SysTenant entity);

    /**
     * Entity -> VO (列表)
     */
    List<SysTenantVo> toVo(List<SysTenant> content);
}
