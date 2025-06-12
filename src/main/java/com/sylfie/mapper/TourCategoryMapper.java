package com.sylfie.mapper;

import com.sylfie.dto.tour.category.TourCategoryResponseDto;
import com.sylfie.model.TourCategory;
import org.springframework.stereotype.Service;

@Service
public class TourCategoryMapper {
    public TourCategoryResponseDto toDto(TourCategory category) {
        TourCategoryResponseDto dto = new TourCategoryResponseDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    public TourCategory toEntity(TourCategoryResponseDto dto) {
        TourCategory category = new TourCategory();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    public void updateEntity(TourCategory category, TourCategoryResponseDto dto) {
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
    }
}