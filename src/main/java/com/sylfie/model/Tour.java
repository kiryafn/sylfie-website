package com.sylfie.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tours")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_template_id", nullable = false)
    private TourTemplate template;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "booked_count", nullable = false)
    private Integer bookedCount = 0;

    public Tour() {}

    public Tour(TourTemplate template,
                LocalDateTime startDate,
                LocalDateTime endDate) {
        this.template    = template;
        this.startDate   = startDate;
        this.endDate     = endDate;
    }

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
}