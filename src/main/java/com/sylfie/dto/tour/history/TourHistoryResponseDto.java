package com.sylfie.dto.tour.history;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TourHistoryResponseDto {

    private String tourName;
    private LocalDateTime bookingDate;
    private BigDecimal priceAtBooking;
    private String status;

    public TourHistoryResponseDto(String tourName, LocalDateTime bookingDate, BigDecimal priceAtBooking, String status) {
        this.tourName = tourName;
        this.bookingDate = bookingDate;
        this.priceAtBooking = priceAtBooking;
        this.status = status;
    }

    public String getTourName() {
        return tourName;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public BigDecimal getPriceAtBooking() {
        return priceAtBooking;
    }

    public String getStatus() {
        return status;
    }
}