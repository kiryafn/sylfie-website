package com.sylfie.dto.tour.template;

import java.util.List;

public class TourTemplatePicturesResponseDto {
    List<String> pictursUrls;
    String previewPictureUrl;

    public TourTemplatePicturesResponseDto(List<String> pictursUrls, String previewPictureUrl) {
        this.pictursUrls = pictursUrls;
        this.previewPictureUrl = previewPictureUrl;
    }

    public List<String> getPictursUrls() {
        return pictursUrls;
    }

    public void setPictursUrls(List<String> pictursUrls) {
        this.pictursUrls = pictursUrls;
    }

    public String getPreviewPictureUrl() {
        return previewPictureUrl;
    }

    public void setPreviewPictureUrl(String previewPictureUrl) {
        this.previewPictureUrl = previewPictureUrl;
    }
}
