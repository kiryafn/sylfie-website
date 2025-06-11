package com.sylfie.controller.api;

import com.sylfie.dto.mvc.TourTemplateDTO;
import com.sylfie.mapper.TourTemplateMapper;
import com.sylfie.service.TourTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourTemplateApiController {

    private final TourTemplateService tourTemplateService;
    private final TourTemplateMapper tourTemplateMapper;

    public TourTemplateApiController(TourTemplateService tourTemplateService, TourTemplateMapper tourTemplateMapper) {
        this.tourTemplateService = tourTemplateService;
        this.tourTemplateMapper = tourTemplateMapper;
    }


    @GetMapping
    public ResponseEntity<List<TourTemplateDTO>> getAllTours() {
        return ResponseEntity.ok(tourTemplateService.getAll().stream().map(tourTemplateMapper::toDto).toList());
    }
}
