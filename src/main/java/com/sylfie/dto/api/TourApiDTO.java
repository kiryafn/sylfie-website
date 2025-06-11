package com.sylfie.dto.api;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TourApiDTO {
    private Integer bookedCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public TourApiDTO(Integer bookedCount, LocalDateTime startDate, LocalDateTime endDate) {
        this.bookedCount = bookedCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TourApiDTO() {
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
