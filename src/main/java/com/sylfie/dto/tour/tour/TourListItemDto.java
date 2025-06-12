package com.sylfie.dto.tour.tour;

import java.time.LocalDateTime;

public class TourListItemDto {
    private Long id;
    private Integer bookedCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public TourListItemDto(Long id, Integer bookedCount, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.bookedCount = bookedCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TourListItemDto() {
    }

    public Integer getBookedCount() {
        return bookedCount;
    }

    public void setBookedCount(Integer bookedCount) {
        this.bookedCount = bookedCount;
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
}
