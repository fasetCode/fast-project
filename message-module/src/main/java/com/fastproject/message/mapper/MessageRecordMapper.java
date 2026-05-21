package com.fastproject.message.mapper;

import com.fastproject.message.domain.MessageRecord;
import com.fastproject.message.vo.record.MessageRecordCreate;
import com.fastproject.message.vo.record.MessageRecordUpdate;
import com.fastproject.message.vo.record.MessageRecordVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface MessageRecordMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRecordFromDto(MessageRecordUpdate dto, @MappingTarget MessageRecord entity);

    MessageRecord toRecord(MessageRecordCreate create);

    List<MessageRecordVo> toVo(List<MessageRecord> content);

    MessageRecordVo toVo(MessageRecord entity);
}
