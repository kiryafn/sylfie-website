package com.sylfie.service;

import com.sylfie.mapper.PictureMapper;
import com.sylfie.model.Picture;
import com.sylfie.repository.PictureRepository;
import com.sylfie.util.S3Info;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static java.awt.SystemColor.info;

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

    public Picture saveAvatar(MultipartFile file) throws IOException {
        S3Info info = storageService.uploadAvatar(file);
        Picture pic = pictureMapper.map(file);
        pic.setUrl(info.url());
        pic.setS3key(info.key());

        return pictureRepository.save(pic);
    }

    @Transactional
    public Picture saveTourPicture(MultipartFile file) throws IOException {
        S3Info info = storageService.uploadTourPicture(file);
        Picture pic = pictureMapper.map(file);
        pic.setUrl(info.url());
        pic.setS3key(info.key());

        return pictureRepository.save(pic);
    }

    @Transactional
    public void deletePicture(Long id) {
        Picture picture = pictureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Picture not found with id: " + id));
        storageService.deleteFile(picture.getS3key());
        pictureRepository.delete(picture);
    }
}
