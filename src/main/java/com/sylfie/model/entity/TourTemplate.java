package com.sylfie.model.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tour_templates")
public class TourTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_category", nullable = false)
    private TourCategory category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String shortDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Difficulty difficulty;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @OneToMany(mappedBy = "tourTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourPicture> pictures = new ArrayList<>();

    @Column(precision = 19, scale = 2)
    private BigDecimal price;

    private Integer durationDays;

    @ManyToOne()
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    public TourTemplate() {}

    public TourTemplate(TourCategory category,
                        String name,
                        String description,
                        Difficulty difficulty,
                        Integer maxParticipants) {
        this.category        = category;
        this.name            = name;
        this.description     = description;
        this.difficulty      = difficulty;
        this.maxParticipants = maxParticipants;
        this.createdAt       = LocalDateTime.now();
    }

    public void addPicture(TourPicture picture) {
        this.pictures.add(picture);
        picture.setTourTemplate(this);
    }

    public void setPreviewPicture(TourPicture picture) {
        for (TourPicture pic : pictures) {
            if (pic.isPreview() && pic.getTourTemplate() == this && pic.isPreview() != null) {
                pic.setPreview(false);
            }
        }
        picture.setPreview(true);
    }

    public void removePicture(TourPicture picture) {
        this.pictures.remove(picture);
        picture.setTourTemplate(null);
    }

    //GETTERS AND SETTERS
    public Long getId() {
        return id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<TourPicture> getPictures() {
        return pictures;
    }

    public void setPictures(List<TourPicture> pictures) {
        this.pictures = pictures;
        for (TourPicture pic : pictures) {
            if (pic.getTourTemplate() != this) {
                pic.setTourTemplate(this);
            }
        }
    }

    public TourPicture getPreviewPicture() {
        for (var picture : pictures){
            if (picture.isPreview() && picture.getTourTemplate() == this) {
                return picture;
            }
        }
        if (!pictures.isEmpty()) {return pictures.getFirst();}
        else return null;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}