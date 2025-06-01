package com.sylfie.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Категория тура.
 */
@Entity
@Table(name = "tour_categories")
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

    public TourCategory() {}

    public TourCategory(String name, String description) {
        this.name = name;
        this.description = description;
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
}