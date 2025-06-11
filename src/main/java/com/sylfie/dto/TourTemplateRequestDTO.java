package com.sylfie.dto;

import com.sylfie.model.Difficulty;
import com.sylfie.model.Location;
import com.sylfie.model.TourCategory;
import com.sylfie.model.TourPicture;

import java.math.BigDecimal;
import java.util.List;

public class TourTemplateRequestDTO {
    private String name;
    private String descriptionHtml;
    private Difficulty difficulty;
    private Integer maxParticipants;
    private TourCategory category;
    private BigDecimal price;
    private Location location;
    private Integer durationDays;
    private String shortDescription;
    private List<TourPicture> tourPictures;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    public void setDescriptionHtml(String descriptionHtml) {
        this.descriptionHtml = descriptionHtml;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public TourCategory getCategory() {
        return category;
    }

    public void setCategory(TourCategory category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public List<TourPicture> getTourPictures() {
        return tourPictures;
    }

    public void setTourPictures(List<TourPicture> tourPictures) {
        this.tourPictures = tourPictures;
    }
}
