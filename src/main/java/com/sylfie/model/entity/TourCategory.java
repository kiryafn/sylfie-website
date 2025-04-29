package com.sylfie.model.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Категория тура.
 */
@Entity
@Table(name = "tour_category")
@EntityListeners(AuditingEntityListener.class)
public class TourCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<TourTemplate> templates = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public TourCategory() {}

    public TourCategory(String name, String description, Difficulty difficulty) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.createdAt = LocalDateTime.now();

    }

    //GETTERS AND SETTERS
    public Long getId() {
        return id;
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

    public Set<TourTemplate> getTemplates() {
        return templates;
    }

    public void addTemplate(TourTemplate tpl) {
        templates.add(tpl);
        tpl.setCategory(this);
    }

    public void removeTemplate(TourTemplate tpl) {
        templates.remove(tpl);
        tpl.setCategory(null);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}