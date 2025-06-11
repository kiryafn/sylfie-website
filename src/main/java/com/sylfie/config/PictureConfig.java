package com.sylfie.config;


import com.sylfie.model.Avatar;
import com.sylfie.model.ContentType;
import com.sylfie.model.Picture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PictureConfig {
    @Value("${cloud.aws.default.avatar}")
    private String defaultAvatarUrl;

    @Bean
    public Avatar defaultAvatar() {
        Picture picture = new Picture();
        picture.setUrl(defaultAvatarUrl);
        picture.setFilename("default_pfp.jpg");
        picture.setContentType(ContentType.jpeg);
        picture.setS3key("avatars/default_pfp.jpg");

        Avatar avatar = new Avatar();
        avatar.setPicture(picture);
        return avatar;

    }
}
