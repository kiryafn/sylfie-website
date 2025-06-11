package com.sylfie.service;

import com.sylfie.util.S3Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
public class StorageService {

    @Value("avatars/")
    private String avatarFolder = "avatars/";

    @Value("tour_pictures/")
    private String pictureFolder;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final S3Client s3Client;

    public StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public S3Info uploadAvatar(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String key = avatarFolder + fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(fileObj));

        fileObj.delete();

        String url = s3Client.utilities().getUrl(builder -> builder
                .bucket(bucketName)
                .key(key)
        ).toExternalForm();

        return new S3Info(key, url, bucketName);
    }

    public S3Info uploadTourPicture(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String key = pictureFolder + fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(fileObj));

        fileObj.delete();

        String url = s3Client.utilities().getUrl(builder -> builder
                .bucket(bucketName)
                .key(key)
        ).toExternalForm();

        return new S3Info(key, url, bucketName);
    }

    public byte[] downloadFile(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        return s3Client.getObjectAsBytes(getObjectRequest).asByteArray();
    }

    public String deleteFile(String fileName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
        return "File deleted: " + fileName;
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        try {
            String suffix = file.getOriginalFilename() != null && file.getOriginalFilename().contains(".")
                    ? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'))
                    : ".tmp";
            File tempFile = File.createTempFile("upload-", suffix);
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(file.getBytes());
            }
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException("Error converting multipartFile to file", e);
        }
    }
}