package com.sylfie.mapper;

import com.sylfie.dto.tour.history.TourHistoryResponseDto;
import com.sylfie.model.UserTourHistory;
import org.springframework.stereotype.Component;

@Component
public class TourHistoryMapper {
    public TourHistoryResponseDto toResponseDto(UserTourHistory tourHistory) {
        return new TourHistoryResponseDto(
                tourHistory.getTour().getTemplate().getName(),
                tourHistory.getBookingDate(),
                tourHistory.getPriceAtBooking(),
                tourHistory.getStatus().toString()
        );
    }
}
