package com.sylfie.service;

import com.github.slugify.Slugify;
import com.sylfie.dto.tour.template.TourTemplateCreateDto;
import com.sylfie.dto.tour.template.TourTemplateResponseDto;
import com.sylfie.mapper.TourTemplateMapper;
import com.sylfie.model.TourTemplate;
import com.sylfie.repository.TourHistoryRepository;
import com.sylfie.repository.TourTemplateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TourTemplateService {

    private final TourTemplateRepository tourTemplateRepository;
    private final TourHistoryRepository tourHistoryRepository;
    private final TourTemplateMapper tourTemplateMapper;
    private final SlugService slugService;

    public TourTemplateService(TourTemplateRepository tourTemplateRepository,
                               TourHistoryRepository tourHistoryRepository,
                               TourTemplateMapper tourTemplateMapper,
                               SlugService slugService) {
        this.tourTemplateRepository = tourTemplateRepository;
        this.tourHistoryRepository = tourHistoryRepository;
        this.tourTemplateMapper = tourTemplateMapper;
        this.slugService = slugService;
    }

    public TourTemplate getById(Long id) {
        return tourTemplateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tour template not found with id: " + id));
    }

    public TourTemplate getBySlug(String slug) {
        return tourTemplateRepository.findBySlug(slug)
                .orElseThrow(() -> new EntityNotFoundException("Tour template not found with slug: " + slug));
    }

    public List<TourTemplate> getAll() {
        return tourTemplateRepository.findAll();
    }

    @Cacheable(value = "tourTemplateById", key = "#id")
    public TourTemplateResponseDto getDetailsById(Long id) {
        return tourTemplateMapper.toResponseDto(getById(id));
    }

    @Cacheable(value = "tourTemplateBySlug", key = "#slug")
    public TourTemplateResponseDto getDetailsBySlug(String slug) {
        return tourTemplateMapper.toResponseDto(getBySlug(slug));
    }

    @Cacheable(
            value = "tourTemplatePages",
            key = "'page=' + #pageable.pageNumber + ':size=' + #pageable.pageSize + ':sort=' + #pageable.sort.toString()"
    )
    public Page<TourTemplateResponseDto> getPage(Pageable pageable) {
        return tourTemplateRepository.findAll(pageable)
                .map(tourTemplateMapper::toResponseDto);
    }

    @Cacheable(value = "tourTemplateTop3Popular")
    public List<TourTemplateResponseDto> getTop3Popular() {
        List<Long> topIds = tourHistoryRepository.findTopTourTemplateIds(PageRequest.of(0, 3));

        List<TourTemplate> templates = tourTemplateRepository.findAllById(topIds);

        Map<Long, TourTemplate> byId = templates.stream()
                .collect(Collectors.toMap(TourTemplate::getId, t -> t));

        return topIds.stream()
                .map(byId::get)
                .filter(Objects::nonNull)
                .map(tourTemplateMapper::toResponseDto)
                .toList();
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "tourTemplatePages", allEntries = true),
            @CacheEvict(value = "tourTemplateTop3Popular", allEntries = true)
    })
    public TourTemplateResponseDto create(TourTemplateCreateDto templateDTO) {
        TourTemplate template = tourTemplateMapper.toEntity(templateDTO);
        template.setSlug(slugService.generateSlug(template));
        TourTemplate saved = tourTemplateRepository.save(template);
        return tourTemplateMapper.toResponseDto(saved);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "tourTemplateById", key = "#template.id"),
            @CacheEvict(value = "tourTemplateBySlug", key = "#oldSlug"),
            @CacheEvict(value = "tourTemplateBySlug", key = "#result"),
            @CacheEvict(value = "tourTemplatePages", allEntries = true),
            @CacheEvict(value = "tourTemplateTop3Popular", allEntries = true)
    })
    public String update(TourTemplate template) {

        TourTemplate existing = getById(template.getId());
        String oldSlug = existing.getSlug();

        if (!existing.getName().equals(template.getName())) {
            template.setSlug(slugService.generateSlug(template));
        }

        tourTemplateRepository.save(template);

        return template.getSlug();
    }
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "tourTemplateById", key = "#id"),
            @CacheEvict(value = "tourTemplateBySlug", key = "#result"),
            @CacheEvict(value = "tourTemplatePages", allEntries = true),
            @CacheEvict(value = "tourTemplateTop3Popular", allEntries = true)
    })
    public String delete(Long id) {
        TourTemplate template = getById(id);
        String slug = template.getSlug();

        tourTemplateRepository.delete(template);

        return slug;
    }
}