package com.sylfie.mapper;

import com.sylfie.model.ContentType;
import com.sylfie.model.Picture;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PictureMapper {
    public Picture map(MultipartFile file) throws IOException {
        Picture pic = new Picture();
        pic.setFilename(file.getOriginalFilename());
        pic.setContentType(ContentType.getByName(file.getContentType()));
        pic.setUploadedAt(LocalDateTime.now());
        return pic;
    }

    public List<Picture> toEntityList(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.stream(files)
                .map(file -> {
                    try {
                        return map(file);
                    } catch (IOException ex) {
                        throw new RuntimeException("MultipartFile Read Error", ex);
                    }
                })
                .collect(Collectors.toList());
    }
}