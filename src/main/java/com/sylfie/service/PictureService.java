package com.sylfie.service;

import com.sylfie.mapper.PictureMapper;
import com.sylfie.model.Picture;
import com.sylfie.repository.PictureRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PictureService {
    private final StorageService storageService;
    private final PictureRepository pictureRepository;
    private final PictureMapper pictureMapper;

    public PictureService(StorageService storageService, PictureRepository pictureRepository, PictureMapper pictureMapper) {
        this.storageService = storageService;
        this.pictureRepository = pictureRepository;
        this.pictureMapper = pictureMapper;
    }

    public Picture save(MultipartFile file) throws IOException {
        String url = storageService.uploadFile(file);
        Picture pic = pictureMapper.map(file);
        pic.setUrl(url);

        return pictureRepository.save(pic);
    }
}
