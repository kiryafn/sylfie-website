package com.sylfie.controller.api;

import com.sylfie.dto.api.TourApiDTO;
import com.sylfie.dto.api.TourTemplateApiDTO;
import com.sylfie.dto.api.TourTemplatePicturesDTO;
import com.sylfie.dto.mvc.TourTemplateDTO;
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
    public ResponseEntity<List<TourTemplateDTO>> getAllTours() {
        return ResponseEntity.ok(tourTemplateService.getAll().stream().map(tourTemplateMapper::toDto).toList());
    }

    @GetMapping("id/{id}")
    public ResponseEntity<TourTemplateDTO> getTourBySlug(@PathVariable Long id) {
        return ResponseEntity.ok(tourTemplateMapper.toDto(tourTemplateService.getById(id)));
    }

    @GetMapping("id/{id}/available")
    public ResponseEntity<TourTemplateApiDTO> getAvailableTours(@PathVariable Long id) {
        List<TourApiDTO> availableTours = tourService.getAvailableByTemplateId(id).stream().map(tourMapper::toApiDto).toList();
        TourTemplate tt = tourTemplateService.getById(id);

        return ResponseEntity.ok(tourTemplateMapper.toApiDto(tt, availableTours));
    }

    @GetMapping("id/{id}/pictures")
    public ResponseEntity<TourTemplatePicturesDTO> addPictures(@PathVariable Long id) {
        TourTemplate tt = tourTemplateService.getById(id);
        List<String> picUrls = tt.getPictures().stream().map(p -> p.getPicture().getUrl()).toList();
        String prevPicUrl = tt.getPreviewPicture().getPicture().getUrl();
        return ResponseEntity.ok(new TourTemplatePicturesDTO(picUrls, prevPicUrl));
    }



}
