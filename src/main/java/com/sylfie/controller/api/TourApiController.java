package com.sylfie.controller.api;

import com.sylfie.dto.tour.tour.TourCreateDto;
import com.sylfie.dto.tour.tour.TourResponseDto;
import com.sylfie.dto.tour.tour.TourUpdateDto;
import com.sylfie.mapper.TourMapper;
import com.sylfie.model.Tour;
import com.sylfie.service.TourService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourApiController {

    private final TourService tourService;
    private final TourMapper tourMapper;

    public TourApiController(TourService tourService, TourMapper tourMapper) {
        this.tourService = tourService;
        this.tourMapper = tourMapper;
    }

    @GetMapping
    public ResponseEntity<List<TourResponseDto>> getAllTours() {
        List<TourResponseDto> dtos = tourService.getAll().stream()
                .map(tourMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourResponseDto> getTourById(@PathVariable Long id) {
        Tour tour = tourService.getById(id);
        return ResponseEntity.ok(tourMapper.toResponseDto(tour));
    }

    @PostMapping
    public ResponseEntity<TourResponseDto> createTour(@Valid @RequestBody TourCreateDto dto) {
        Tour created = tourService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/tours/" + created.getId()))
                .body(tourMapper.toResponseDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourResponseDto> updateTour(@PathVariable Long id, @Valid @RequestBody TourUpdateDto dto) {
        Tour updated = tourService.update(id, dto);
        return ResponseEntity.ok(tourMapper.toResponseDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }
}