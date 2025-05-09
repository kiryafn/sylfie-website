package com.sylfie.mapper;

import com.sylfie.model.entity.TourPicture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TourPictureMapper {

    @Mapping(target = "filename", expression = "java(file.getOriginalFilename())")
    @Mapping(target = "contentType", expression = "java(file.getContentType())")
    @Mapping(target = "data",      expression = "java(file.getBytes())")
    @Mapping(target = "tourTemplate", ignore = true)
    TourPicture toEntity(MultipartFile file) throws IOException;

    default List<TourPicture> toEntityList(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.stream(files)
                .map(file -> {
                    try {
                        return toEntity(file);
                    } catch (IOException ex) {
                        throw new RuntimeException("Ошибка чтения MultipartFile", ex);
                    }
                })
                .collect(Collectors.toList());
    }
}