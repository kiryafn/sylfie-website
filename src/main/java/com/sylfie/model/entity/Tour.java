package com.sylfie.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tour")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_id", nullable = false)
    private TourTemplate template;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "booked_count", nullable = false)
    private Integer bookedCount;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tour_location",
            joinColumns =  @JoinColumn(name = "tour_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    @OrderColumn(name = "sequence")
    private List<Location> locations = new ArrayList<>();

    public Tour() {
    }

    public Tour(TourTemplate template,
                LocalDateTime startDate,
                LocalDateTime endDate) {
        this.template    = template;
        this.startDate   = startDate;
        this.endDate     = endDate;
        this.bookedCount = 0;
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public void deleteLocation(Location location) {
        locations.remove(location);
    }

    //GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public TourTemplate getTemplate() {
        return template;
    }

    public void setTemplate(TourTemplate template) {
        this.template = template;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getBookedCount() {
        return bookedCount;
    }

    public void setBookedCount(Integer bookedCount) {
        this.bookedCount = bookedCount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Location> getLocations() {
        return locations;
    }
}