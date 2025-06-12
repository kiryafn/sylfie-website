package com.sylfie.dto.tour.template;

import com.sylfie.dto.tour.tour.TourListItemDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TourTemplateDetailsDto {
    private Long id;

    private String category;

    private String name;

    private String slug;

    private String description;

    private String shortDescription;

    private String difficulty;

    private Integer maxParticipants;

    private List<String> picturesUrls = new ArrayList<>();

    private String previewPictureUrl;

    private BigDecimal price;

    private Integer durationDays;

    private String location;

    private List<TourListItemDto> availableTours = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getPicturesUrls() {
        return picturesUrls;
    }

    public void setPicturesUrls(List<String> picturesUrls) {
        this.picturesUrls = picturesUrls;
    }

    public String getPreviewPictureUrl() {
        return previewPictureUrl;
    }

    public void setPreviewPictureUrl(String previewPictureUrl) {
        this.previewPictureUrl = previewPictureUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<TourListItemDto> getAvailableTours() {
        return availableTours;
    }

    public void setAvailableTours(List<TourListItemDto> availableTours) {
        this.availableTours = availableTours;
    }
}