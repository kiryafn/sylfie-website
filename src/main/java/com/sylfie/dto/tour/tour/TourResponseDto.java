package com.sylfie.dto.tour.tour;

import java.time.LocalDateTime;

public class TourResponseDto {
    private Integer bookedCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public TourResponseDto(Integer bookedCount, LocalDateTime startDate, LocalDateTime endDate) {
        this.bookedCount = bookedCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TourResponseDto() {
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
