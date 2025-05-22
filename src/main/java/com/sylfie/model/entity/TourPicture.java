package com.sylfie.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tour_pictures")
public class TourPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_template_id")
    private TourTemplate tourTemplate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    private Boolean isPreview;

    public TourPicture() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TourTemplate getTourTemplate() {
        return tourTemplate;
    }

    public void setTourTemplate(TourTemplate tourTemplate) {
        this.tourTemplate = tourTemplate;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Boolean isPreview() {
        return isPreview;
    }

    public void setPreview(Boolean preview) {
        isPreview = preview;
    }
}