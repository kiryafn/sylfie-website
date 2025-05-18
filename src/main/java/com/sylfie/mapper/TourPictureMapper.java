package com.sylfie.mapper;

import com.sylfie.model.entity.TourPicture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TourPictureMapper {
    public TourPicture toEntity(MultipartFile file) throws IOException {
        TourPicture tourPicture = new TourPicture();
        tourPicture.setData(file.getBytes());
        tourPicture.setContentType(file.getContentType());
        tourPicture.setFilename(file.getOriginalFilename());
        return tourPicture;
    }

    public List<TourPicture> toEntityList(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.stream(files)
                .map(file -> {
                    try {
                        return toEntity(file);
                    } catch (IOException ex) {
                        throw new RuntimeException("MultipartFile Read Error", ex);
                    }
                })
                .collect(Collectors.toList());
    }
}