package com.sylfie.service;

import com.sylfie.model.Tour;
import com.sylfie.repository.TourRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TourService {

    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public List<Tour> getAll() {
        return tourRepository.findAll();
    }

    public Tour getById(Long id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tour not found with id: " + id));
    }

    @Transactional
    public Tour create(Tour tour) {
        return tourRepository.save(tour);
    }

    @Transactional
    public Tour update(Tour tour) {
        return tourRepository.save(tour);
    }

    @Transactional
    public void delete(Long id) {
        Tour tour = getById(id);
        tourRepository.delete(tour);
    }
}