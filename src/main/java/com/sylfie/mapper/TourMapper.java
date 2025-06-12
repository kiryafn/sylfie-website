package com.sylfie.mapper;

import com.sylfie.dto.tour.tour.TourCreateDto;
import com.sylfie.dto.tour.tour.TourListItemDto;
import com.sylfie.dto.tour.tour.TourResponseDto;
import com.sylfie.model.Tour;
import org.springframework.stereotype.Service;

@Service
public class TourMapper {
    public TourListItemDto toListItemDto(Tour t) {
        return new TourListItemDto(t.getId(), t.getBookedCount(), t.getStartDate(), t.getEndDate());
    }

    public TourResponseDto toResponseDto(Tour t) {
        TourResponseDto dto = new TourResponseDto();
        dto.setId(t.getId());
        dto.setName(t.getTemplate().getName());
        dto.setBookedCount(t.getBookedCount());
        dto.setStartDate(t.getStartDate());
        dto.setEndDate(t.getEndDate());
        return dto;
    }
}
