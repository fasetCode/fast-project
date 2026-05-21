package com.fastproject.mall.mapper;

import com.fastproject.mall.domain.MallOrderRefund;
import com.fastproject.mall.vo.orderrefund.MallOrderRefundCreate;
import com.fastproject.mall.vo.orderrefund.MallOrderRefundUpdate;
import com.fastproject.mall.vo.orderrefund.MallOrderRefundVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MallOrderRefundMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderRefundFromDto(MallOrderRefundUpdate dto, @MappingTarget MallOrderRefund entity);

    MallOrderRefund toOrderRefund(MallOrderRefundCreate create);

    List<MallOrderRefundVo> toVo(List<MallOrderRefund> content);

    MallOrderRefundVo toVo(MallOrderRefund entity);
}
