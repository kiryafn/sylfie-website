package com.sylfie.mapper;

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
        tt.setPictures(dto.getPictures());
        return tt;

    }
}
