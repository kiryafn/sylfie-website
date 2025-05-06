package com.sylfie.controller;

import com.sylfie.model.entity.TourPicture;
import com.sylfie.model.entity.TourTemplate;
import com.sylfie.service.TourTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/tours/{tourId}/pictures")
public class TourPictureController {

    private final TourTemplateService tourTemplateService;

    public TourPictureController(TourTemplateService tourTemplateService) {
        this.tourTemplateService = tourTemplateService;
    }

    @GetMapping(value = "/{picId}")
    public ResponseEntity<byte[]> getPicture(
            @PathVariable Long tourId,
            @PathVariable Long picId) {

        TourTemplate tpl = tourTemplateService.getById(tourId);
        TourPicture pic = tpl.getPictures().stream()
            .filter(p -> p.getId().equals(picId))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Picture not found"));

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(pic.getContentType()))
            .body(pic.getData());
    }
}