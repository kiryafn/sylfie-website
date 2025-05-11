package com.sylfie.mapper;

import com.sylfie.model.dto.TourTemplateRequestDTO;
import com.sylfie.model.entity.TourTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TourTemplateMapper {

    /** Create: DTO → Entity */
    @Mapping(target = "description", source = "descriptionHtml")
    TourTemplate toEntity(TourTemplateRequestDTO dto);
}
