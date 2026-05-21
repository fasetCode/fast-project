package com.fastproject.mall.mapper;

import com.fastproject.mall.domain.MallCart;
import com.fastproject.mall.vo.cart.MallCartCreate;
import com.fastproject.mall.vo.cart.MallCartUpdate;
import com.fastproject.mall.vo.cart.MallCartVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MallCartMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCartFromDto(MallCartUpdate dto, @MappingTarget MallCart entity);

    MallCart toCart(MallCartCreate create);

    List<MallCartVo> toVo(List<MallCart> content);

    MallCartVo toVo(MallCart entity);
}
