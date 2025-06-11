package com.sylfie.dto;

import com.sylfie.model.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TourTemplateDTO {
    private Long id;

    private TourCategory category;

    private String name;

    private String slug;

    private String description;

    private String shortDescription;

    private Difficulty difficulty;

    private Integer maxParticipants;

    private List<TourPicture> pictures = new ArrayList<>();

    private TourPicture previewPicture;

    private BigDecimal price;

    private Integer durationDays;

    private Location location;

    public TourTemplateDTO(Long id, TourCategory category, String name, String slug, String description, String shortDescription, Difficulty difficulty, Integer maxParticipants, List<TourPicture> pictures, TourPicture preview, BigDecimal price, Integer durationDays, Location location) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.shortDescription = shortDescription;
        this.difficulty = difficulty;
        this.maxParticipants = maxParticipants;
        this.pictures = pictures;
        this.price = price;
        this.durationDays = durationDays;
        this.location = location;
        this.previewPicture = preview;
    }

    public TourTemplateDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TourCategory getCategory() {
        return category;
    }

    public void setCategory(TourCategory category) {
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

    public List<TourPicture> getPictures() {
        return pictures;
    }

    public void setPictures(List<TourPicture> pictures) {
        this.pictures = pictures;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public TourPicture getPreviewPicture() {
        return previewPicture;
    }

    public void setPreviewPicture(TourPicture previewPicture) {
        this.previewPicture = previewPicture;
    }
}
