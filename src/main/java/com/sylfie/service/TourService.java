package com.sylfie.service;

import com.sylfie.dto.tour.tour.TourCreateDto;
import com.sylfie.dto.tour.tour.TourUpdateDto;
import com.sylfie.model.Tour;
import com.sylfie.model.TourTemplate;
import com.sylfie.repository.TourRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TourService {

    private final TourRepository tourRepository;
    private final TourTemplateService tourTemplateService;

    public TourService(TourRepository tourRepository, TourTemplateService tourTemplateService) {
        this.tourRepository = tourRepository;
        this.tourTemplateService = tourTemplateService;
    }

    public List<Tour> getAll() {
        return tourRepository.findAll();
    }

    public Tour getById(Long id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tour not found with id: " + id));
    }

    @Transactional
    public void delete(Long id) {
        Tour tour = getById(id);
        tourRepository.delete(tour);
    }

    public List<Tour> getAvailableByTemplateId(Long templateId) {
        return tourRepository.findAllByTemplateIdOrderByStartDate(templateId).stream().filter(t -> t.getStartDate().isAfter(LocalDateTime.now())).toList();
    }

    @Transactional
    public Tour create(TourCreateDto dto) {
        TourTemplate tt = tourTemplateService.getById(dto.getTemplateId());
        Tour tour = new Tour(tt, dto.getStartDate(), dto.getEndDate());
        return tourRepository.save(tour);
    }

    @Transactional
    public Tour update(Long id, @Valid TourUpdateDto dto) {
        Tour tour = getById(id);
        tour.setStartDate(dto.getStartDate());
        tour.setEndDate(dto.getEndDate());
        return tourRepository.save(tour);
    }
}