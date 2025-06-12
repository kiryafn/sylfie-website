package com.sylfie.controller.api;

import com.sylfie.dto.tour.category.TourCategoryResponseDto;
import com.sylfie.mapper.TourCategoryMapper;
import com.sylfie.model.TourCategory;
import com.sylfie.service.TourCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour-categories")
public class TourCategoryApiController {

    private final TourCategoryService tourCategoryService;
    private final TourCategoryMapper tourCategoryMapper;

    public TourCategoryApiController(TourCategoryService tourCategoryService, TourCategoryMapper tourCategoryMapper) {
        this.tourCategoryService = tourCategoryService;
        this.tourCategoryMapper = tourCategoryMapper;
    }

    @GetMapping
public ResponseEntity<List<TourCategoryResponseDto>> getAllCategories() {
        List<TourCategoryResponseDto> categories = tourCategoryService.getAll()
                .stream()
                .map(tourCategoryMapper::toDto)
                .toList();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("{id}")
    public ResponseEntity<TourCategoryResponseDto> getCategoryById(@PathVariable Long id) {
        TourCategory category = tourCategoryService.getById(id);
        return ResponseEntity.ok(tourCategoryMapper.toDto(category));
    }

    @PostMapping
    public ResponseEntity<TourCategoryResponseDto> createCategory(@Valid @RequestBody TourCategoryResponseDto dto) {
        TourCategory created = tourCategoryService.create(tourCategoryMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(tourCategoryMapper.toDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourCategoryResponseDto> updateCategory(@PathVariable Long id,
                                                                  @Valid @RequestBody TourCategoryResponseDto dto) {
        TourCategory category = tourCategoryService.getById(id);
        tourCategoryMapper.updateEntity(category, dto);
        TourCategory updated = tourCategoryService.update(category);
        return ResponseEntity.ok(tourCategoryMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        tourCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}