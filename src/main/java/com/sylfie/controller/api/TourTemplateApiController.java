package com.sylfie.controller.api;

import com.sylfie.dto.tour.tour.TourResponseDto;
import com.sylfie.dto.tour.template.TourTemplatePicturesResponseDto;
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
@RequestMapping("/api/tours")
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
        return ResponseEntity.ok(tourTemplateService.getAll().stream().map(tourTemplateMapper::toDto).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<TourTemplateResponseDto> getTourBySlug(@PathVariable Long id) {
        return ResponseEntity.ok(tourTemplateMapper.toDto(tourTemplateService.getById(id)));
    }

    @GetMapping("{id}/available")
    public ResponseEntity<TourTemplateApiDTO> getAvailableTours(@PathVariable Long id) {
        List<TourResponseDto> availableTours = tourService.getAvailableByTemplateId(id).stream().map(tourMapper::toApiDto).toList();
        TourTemplate tt = tourTemplateService.getById(id);

        return ResponseEntity.ok(tourTemplateMapper.toApiDto(tt, availableTours));
    }

    @GetMapping("{id}/pictures")
    public ResponseEntity<TourTemplatePicturesResponseDto> addPictures(@PathVariable Long id) {
        TourTemplate tt = tourTemplateService.getById(id);
        List<String> picUrls = tt.getPictures().stream().map(p -> p.getPicture().getUrl()).toList();
        String prevPicUrl = tt.getPreviewPicture().getPicture().getUrl();
        return ResponseEntity.ok(new TourTemplatePicturesResponseDto(picUrls, prevPicUrl));
    }



}
