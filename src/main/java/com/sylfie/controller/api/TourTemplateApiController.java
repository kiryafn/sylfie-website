package com.sylfie.controller.api;

import com.sylfie.dto.tour.template.TourTemplateDetailsDto;
import com.sylfie.dto.tour.tour.TourListItemDto;
import com.sylfie.dto.tour.template.TourTemplateResponseDto;
import com.sylfie.mapper.TourMapper;
import com.sylfie.mapper.TourTemplateMapper;
import com.sylfie.model.TourTemplate;
import com.sylfie.service.TourService;
import com.sylfie.service.TourTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour-templates")
public class TourTemplateApiController {

    private final TourTemplateService tourTemplateService;
    private final TourTemplateMapper tourTemplateMapper;
    private final TourService tourService;
    private final TourMapper tourMapper;

    public TourTemplateApiController(TourTemplateService tourTemplateService, TourTemplateMapper tourTemplateMapper, TourService tourService, TourMapper tourMapper) {
        this.tourTemplateService = tourTemplateService;
        this.tourTemplateMapper = tourTemplateMapper;
        this.tourService = tourService;
        this.tourMapper = tourMapper;
    }


    @GetMapping
    public ResponseEntity<List<TourTemplateResponseDto>> getAllTours() {
        return ResponseEntity.ok(tourTemplateService.getAll().stream().map(tourTemplateMapper::toResponseDto).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<TourTemplateDetailsDto> getTourById(@PathVariable Long id) {
        List<TourListItemDto> availableTours = tourService.getAvailableByTemplateId(id).stream().map(tourMapper::toListItemDto).toList();
        TourTemplate tt = tourTemplateService.getById(id);

        return ResponseEntity.ok(tourTemplateMapper.toDetailedDto(tt, availableTours));
    }
}
