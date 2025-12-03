package com.sylfie.controller.rest;

import com.sylfie.dto.tour.template.TourTemplateResponseDto;
import com.sylfie.service.TourTemplateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tour-templates/paged")
public class TourTemplateController {

    private final TourTemplateService tourTemplateService;

    public TourTemplateController(TourTemplateService tourTemplateService) {
        this.tourTemplateService = tourTemplateService;
    }

    @GetMapping
    public ResponseEntity<Page<TourTemplateResponseDto>> getPaged(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<TourTemplateResponseDto> page = tourTemplateService.getPage(pageable);
        return ResponseEntity.ok(page);
    }
}

