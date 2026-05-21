package com.fastproject.file.mapper;

import com.fastproject.file.domain.FileInfo;
import com.fastproject.file.vo.info.FileInfoCreate;
import com.fastproject.file.vo.info.FileInfoUpdate;
import com.fastproject.file.vo.info.FileInfoVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FileInfoMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFileInfoFromDto(FileInfoUpdate dto, @MappingTarget FileInfo entity);

    FileInfo toFileInfo(FileInfoCreate create);

    List<FileInfoVo> toVo(List<FileInfo> content);

    FileInfoVo toVo(FileInfo entity);
}
