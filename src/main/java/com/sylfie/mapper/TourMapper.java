package com.sylfie.mapper;

import com.sylfie.dto.api.TourApiDTO;
import com.sylfie.model.Tour;
import org.springframework.stereotype.Service;

@Service
public class TourMapper {
public TourApiDTO toApiDto(Tour t){
    return new TourApiDTO(t.getBookedCount(), t.getStartDate(), t.getEndDate());
}
}
