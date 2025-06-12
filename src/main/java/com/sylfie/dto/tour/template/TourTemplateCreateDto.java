package com.sylfie.dto.tour.template;

import com.sylfie.model.Difficulty;
import com.sylfie.model.Location;
import com.sylfie.model.TourCategory;
import com.sylfie.model.TourPicture;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public class TourTemplateCreateDto {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    private String descriptionHtml;

    @NotNull(message = "Difficulty is required")
    private Difficulty difficulty;

    @NotNull(message = "Max participants is required")
    @Min(value = 1, message = "There must be at least 1 participant")
    private Integer maxParticipants;

    @NotNull(message = "Category is required")
    private TourCategory category;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Location is required")
    private Location location;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 day")
    private Integer durationDays;

    @NotBlank(message = "Short description is required")
    @Size(max = 255, message = "Short description must not exceed 255 characters")
    private String shortDescription;

    private List<TourPicture> tourPictures;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescriptionHtml() { return descriptionHtml; }
    public void setDescriptionHtml(String descriptionHtml) { this.descriptionHtml = descriptionHtml; }

    public Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }

    public Integer getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(Integer maxParticipants) { this.maxParticipants = maxParticipants; }

    public TourCategory getCategory() { return category; }
    public void setCategory(TourCategory category) { this.category = category; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public Integer getDurationDays() { return durationDays; }
    public void setDurationDays(Integer durationDays) { this.durationDays = durationDays; }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public List<TourPicture> getTourPictures() { return tourPictures; }
    public void setTourPictures(List<TourPicture> tourPictures) { this.tourPictures = tourPictures; }
}