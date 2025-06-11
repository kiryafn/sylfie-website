package com.sylfie.service;

import com.github.slugify.Slugify;
import com.sylfie.dto.mvc.TourTemplateDTO;
import com.sylfie.mapper.TourTemplateMapper;
import com.sylfie.dto.mvc.TourTemplateRequestDTO;
import com.sylfie.model.TourTemplate;
import com.sylfie.repository.TourHistoryRepository;
import com.sylfie.repository.TourTemplateRepository;
import com.sylfie.util.HtmlSanitizer;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TourTemplateService{

    private final TourTemplateRepository tourTemplateRepository;
    private final TourHistoryRepository tourHistoryRepository;
    private final Slugify slugify;
    private final HtmlSanitizer htmlSanitizer;
    private final TourTemplateMapper tourTemplateMapper;


    public TourTemplateService(TourTemplateRepository tourTemplateRepository, TourHistoryRepository tourHistoryRepository, Slugify slugify, HtmlSanitizer htmlSanitizer, TourTemplateMapper tourTemplateMapper) {
        this.tourTemplateRepository = tourTemplateRepository;
        this.tourHistoryRepository = tourHistoryRepository;
        this.tourTemplateMapper = tourTemplateMapper;
        this.htmlSanitizer = htmlSanitizer;
        this.slugify = slugify;
    }

    public List<TourTemplate> getAll() {
        return tourTemplateRepository.findAll();
    }

    public TourTemplate getById(Long id) {
        return tourTemplateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tour template not found with id: " + id));
    }

    public TourTemplateDTO getBySlug(String slug) {
        return tourTemplateRepository.findBySlug(slug).map(tourTemplateMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Tour template not found with slug: " + slug));
    }

    @Transactional
    public TourTemplateDTO create(TourTemplateRequestDTO templateDTO) {
        TourTemplate template = tourTemplateMapper.toEntity(templateDTO);
        template.setSlug(generateSlug(template));
        template.setDescription(htmlSanitizer.sanitize(template.getDescription()));
        TourTemplate tt = tourTemplateRepository.save(template);

        return tourTemplateMapper.toDto(tt);
    }

    @Transactional
    public TourTemplate update(TourTemplate template) {
        TourTemplate existing = getById(template.getId());

        if (!existing.getName().equals(template.getName())) {
            template.setSlug(generateSlug(template));
        }
        return tourTemplateRepository.save(template);
    }

    @Transactional
    public void delete(Long id) {
        TourTemplate template = getById(id);
        tourTemplateRepository.delete(template);
    }

    public List<TourTemplateDTO> getTop3Popular() {
        List<Long> topIds = tourHistoryRepository.findTopTourIds(PageRequest.of(0, 3));
        return tourTemplateRepository.findAllById(topIds).stream().map(tourTemplateMapper::toDto).toList();
    }

    private String generateSlug(TourTemplate template) {
        String base = slugify.slugify(template.getName());
        String slug = base;
        int i = 1;
        while (tourTemplateRepository.existsBySlug(slug)) {
            slug = base + "-" + i++;
        }
        return slug;
    }

}