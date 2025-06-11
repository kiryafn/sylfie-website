package com.sylfie.dto.api;

import java.util.List;

public class TourTemplatePicturesDTO {
    List<String> pictursUrls;
    String previewPictureUrl;

    public TourTemplatePicturesDTO(List<String> pictursUrls, String previewPictureUrl) {
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
