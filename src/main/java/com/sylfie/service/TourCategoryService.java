package com.sylfie.service;

import com.sylfie.model.entity.TourCategory;
import com.sylfie.repository.TourCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TourCategoryService {

    private final TourCategoryRepository tourCategoryRepository;

    public TourCategoryService(TourCategoryRepository tourCategoryRepository) {
        this.tourCategoryRepository = tourCategoryRepository;
    }

    public List<TourCategory> getAll() {
        return tourCategoryRepository.findAll();
    }

    public TourCategory getById(Long id) {
        return tourCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TourCategory not found with id: " + id));
    }

    @Transactional
    public TourCategory create(TourCategory category) {
        return tourCategoryRepository.save(category);
    }

    @Transactional
    public TourCategory update(TourCategory category) {
        return tourCategoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        TourCategory category = getById(id);
        tourCategoryRepository.delete(category);
    }
}