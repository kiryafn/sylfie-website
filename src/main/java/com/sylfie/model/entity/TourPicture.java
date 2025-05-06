package com.sylfie.model.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("TOUR")
public class TourPicture extends Picture {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_template_id")
    private TourTemplate tourTemplate;

    private Boolean isPreview = false;

    public TourTemplate getTourTemplate() {
        return tourTemplate;
    }

    public void setTourTemplate(TourTemplate tourTemplate) {
        this.tourTemplate = tourTemplate;
        if (tourTemplate != null && !tourTemplate.getPictures().contains(this)) {
            tourTemplate.getPictures().add(this);
        }
    }

    public Boolean isPreview() {
        return isPreview;
    }

    public void setPreview(Boolean preview) {
        isPreview = preview;
    }
}