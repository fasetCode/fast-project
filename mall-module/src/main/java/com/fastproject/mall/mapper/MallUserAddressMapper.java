package com.fastproject.mall.mapper;

import com.fastproject.mall.domain.MallUserAddress;
import com.fastproject.mall.vo.useraddress.MallUserAddressCreate;
import com.fastproject.mall.vo.useraddress.MallUserAddressUpdate;
import com.fastproject.mall.vo.useraddress.MallUserAddressVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MallUserAddressMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserAddressFromDto(MallUserAddressUpdate dto, @MappingTarget MallUserAddress entity);

    MallUserAddress toUserAddress(MallUserAddressCreate create);

    List<MallUserAddressVo> toVo(List<MallUserAddress> content);

    MallUserAddressVo toVo(MallUserAddress entity);
}
