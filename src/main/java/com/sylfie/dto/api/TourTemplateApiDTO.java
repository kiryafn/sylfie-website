package com.sylfie.dto.api;

import java.util.List;

public class TourTemplateApiDTO {
    private Long id;
    private String name;
    private String caregory;
    private String location;
    private Integer maxParticipants;
    private List<TourApiDTO> available;

    public TourTemplateApiDTO(Long id, String name, String caregory, String location, Integer maxParticipants, List<TourApiDTO> available) {
        this.id = id;
        this.name = name;
        this.caregory = caregory;
        this.location = location;
        this.available = available;
        this.maxParticipants = maxParticipants;
    }

    public TourTemplateApiDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaregory() {
        return caregory;
    }

    public void setCaregory(String caregory) {
        this.caregory = caregory;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<TourApiDTO> getAvailable() {
        return available;
    }

    public void setAvailable(List<TourApiDTO> available) {
        this.available = available;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
}