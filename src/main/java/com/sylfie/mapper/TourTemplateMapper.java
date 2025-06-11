package com.sylfie.mapper;

import com.sylfie.dto.TourTemplateDTO;
import com.sylfie.dto.TourTemplateRequestDTO;
import com.sylfie.model.TourTemplate;
import org.springframework.stereotype.Service;

@Service
public class TourTemplateMapper {

    /** Create: DTO → Entity */

    public TourTemplate toEntity(TourTemplateRequestDTO dto){
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

    public TourTemplateDTO toDto(TourTemplate tt){
        TourTemplateDTO dto = new TourTemplateDTO();
        dto.setId(tt.getId());
        dto.setName(tt.getName());
        dto.setShortDescription(tt.getShortDescription());
        dto.setDurationDays(tt.getDurationDays());
        dto.setSlug(tt.getSlug());
        dto.setDescription(tt.getDescription());
        dto.setDifficulty(tt.getDifficulty());
        dto.setMaxParticipants(tt.getMaxParticipants());
        dto.setCategory(tt.getCategory());
        dto.setPrice(tt.getPrice());
        dto.setLocation(tt.getLocation());
        dto.setPictures(tt.getPictures());
        dto.setPreviewPicture(tt.getPreviewPicture());

        return dto;
    }
}
