package com.sylfie.model.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String shortDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Difficulty difficulty;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(nullable = false)
    private Integer capacity;

    @OneToMany(mappedBy = "tourTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourPicture> pictures = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public TourTemplate() {}

    public TourTemplate(TourCategory category,
                        String name,
                        String description,
                        Difficulty difficulty,
                        Integer maxParticipants,
                        Integer capacity) {
        this.category      = category;
        this.name          = name;
        this.description   = description;
        this.difficulty    = difficulty;
        this.maxParticipants = maxParticipants;
        this.capacity      = capacity;
        this.createdAt = LocalDateTime.now();
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
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
        if (pictures.size() > 0) {return pictures.getFirst();}
        else return null;
    }
}