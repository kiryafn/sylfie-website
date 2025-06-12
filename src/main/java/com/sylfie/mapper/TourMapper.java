package com.sylfie.mapper;

import com.sylfie.dto.tour.tour.TourResponseDto;
import com.sylfie.model.Tour;
import org.springframework.stereotype.Service;

@Service
public class TourMapper {
public TourResponseDto toApiDto(Tour t){
    return new TourResponseDto(t.getBookedCount(), t.getStartDate(), t.getEndDate());
}
}
