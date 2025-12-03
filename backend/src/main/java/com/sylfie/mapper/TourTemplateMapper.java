package com.sylfie.mapper;

import com.sylfie.dto.tour.template.TourTemplateDetailsDto;
import com.sylfie.dto.tour.tour.TourListItemDto;
import com.sylfie.dto.tour.template.TourTemplateResponseDto;
import com.sylfie.dto.tour.template.TourTemplateCreateDto;
import com.sylfie.model.TourTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourTemplateMapper {

    /** Create: DTO → Entity */

    public TourTemplate toEntity(TourTemplateCreateDto dto){
        TourTemplate tt = new TourTemplate();
        tt.setName(dto.getName());
        tt.setDescription(dto.getDescriptionHtml());
        tt.setDifficulty(dto.getDifficulty());
        tt.setMaxParticipants(dto.getMaxParticipants());
        tt.setCategory(dto.getCategory());
        tt.setPrice(dto.getPrice());
        tt.setLocation(dto.getLocation());
        tt.setDurationDays(dto.getDurationDays());
        tt.setShortDescription(dto.getShortDescription());
        if (dto.getTourPictures() != null && !dto.getTourPictures().isEmpty()) {
            tt.setPictures(dto.getTourPictures());
        }
        return tt;
    }

    public TourTemplateDetailsDto toDetailedDto(TourTemplate tt, List<TourListItemDto> tourListItems){
        TourTemplateDetailsDto dto = new TourTemplateDetailsDto();
        dto.setId(tt.getId());
        dto.setName(tt.getName());
        dto.setShortDescription(tt.getShortDescription());
        dto.setDurationDays(tt.getDurationDays());
        dto.setSlug(tt.getSlug());
        dto.setDescription(tt.getDescription());
        dto.setDifficulty(tt.getDifficulty().getName());
        dto.setMaxParticipants(tt.getMaxParticipants());
        dto.setCategory(tt.getCategory().getName());
        dto.setPrice(tt.getPrice());
        dto.setLocation(tt.getLocation().getName());
        if (tt.getPictures() != null && !tt.getPictures().isEmpty()) {
            dto.setPicturesUrls(
                    tt.getPictures().stream()
                            .filter(p -> p.getPicture() != null)
                            .map(p -> p.getPicture().getUrl())
                            .toList()
            );
        } else {
            dto.setPicturesUrls(null);
        }

        var previewPicture = tt.getPreviewPicture();
        if (previewPicture != null && previewPicture.getPicture() != null) {
            dto.setPreviewPictureUrl(previewPicture.getPicture().getUrl());
        } else {
            dto.setPreviewPictureUrl(null);
        }
        dto.setAvailableTours(tourListItems);

        return dto;
    }

    public TourTemplateResponseDto toResponseDto(TourTemplate tt){
        TourTemplateResponseDto dto = new TourTemplateResponseDto();
        dto.setId(tt.getId());
        dto.setName(tt.getName());
        dto.setShortDescription(tt.getShortDescription());
        dto.setDurationDays(tt.getDurationDays());
        dto.setSlug(tt.getSlug());
        dto.setDifficulty(tt.getDifficulty().getName());
        dto.setMaxParticipants(tt.getMaxParticipants());
        dto.setCategory(tt.getCategory().getName());
        dto.setPrice(tt.getPrice());
        dto.setLocation(tt.getLocation().getName());
        var previewPicture = tt.getPreviewPicture();
        if (previewPicture != null && previewPicture.getPicture() != null) {
            dto.setPreviewPictureUrl(previewPicture.getPicture().getUrl());
        } else {
            dto.setPreviewPictureUrl(null);
        }

        return dto;
    }
}
