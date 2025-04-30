package com.sylfie.service;

import com.sylfie.model.entity.TourTemplate;
import com.sylfie.repository.TourTemplateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TourTemplateService {

    private final TourTemplateRepository tourTemplateRepository;

    public TourTemplateService(TourTemplateRepository tourTemplateRepository) {
        this.tourTemplateRepository = tourTemplateRepository;
    }

    public List<TourTemplate> getAll() {
        return tourTemplateRepository.findAll();
    }

    public TourTemplate getById(Long id) {
        return tourTemplateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tour template not found with id: " + id));
    }

    @Transactional
    public TourTemplate create(TourTemplate template) {
        return tourTemplateRepository.save(template);
    }

    @Transactional
    public TourTemplate update(TourTemplate template) {
        return tourTemplateRepository.save(template);
    }

    @Transactional
    public void delete(Long id) {
        TourTemplate template = getById(id);
        tourTemplateRepository.delete(template);
    }
}