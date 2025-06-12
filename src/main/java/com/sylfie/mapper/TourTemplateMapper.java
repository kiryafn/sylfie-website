package com.sylfie.mapper;

import com.sylfie.dto.tour.tour.TourResponseDto;
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
        tt.setPictures(dto.getTourPictures());
        return tt;
    }

    public TourTemplateResponseDto toDto(TourTemplate tt){
        TourTemplateResponseDto dto = new TourTemplateResponseDto();
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
        dto.setPicturesUrls(tt.getPictures().stream().map(p -> p.getPicture().getUrl()).toList());
        dto.setPreviewPictureUrl(tt.getPreviewPicture().getPicture().getUrl());

        return dto;
    }

    public TourTemplateApiDTO toApiDto(TourTemplate tt, List<TourResponseDto> tours){
        TourTemplateApiDTO dto = new TourTemplateApiDTO();
        dto.setId(tt.getId());
        dto.setName(tt.getName());
        dto.setCaregory(tt.getCategory().getName());
        dto.setLocation(tt.getLocation().getName());
        dto.setMaxParticipants(tt.getMaxParticipants());
        dto.setAvailable(tours);

        return dto;
    }
}
