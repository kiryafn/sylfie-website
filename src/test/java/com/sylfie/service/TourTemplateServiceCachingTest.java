package com.sylfie.service;

import com.sylfie.config.TestCacheConfig;
import com.sylfie.dto.tour.template.TourTemplateCreateDto;
import com.sylfie.dto.tour.template.TourTemplateResponseDto;
import com.sylfie.mapper.TourTemplateMapper;
import com.sylfie.model.TourTemplate;
import com.sylfie.repository.TourHistoryRepository;
import com.sylfie.repository.TourTemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = { TourTemplateService.class, TestCacheConfig.class })
@Import(TestCacheConfig.class)
class TourTemplateServiceCachingTest {

    @Autowired
    private TourTemplateService tourTemplateService;

    @Autowired
    private CacheManager cacheManager;

    @MockBean
    private TourTemplateRepository tourTemplateRepository;

    @MockBean
    private TourHistoryRepository tourHistoryRepository;

    @MockBean
    private TourTemplateMapper tourTemplateMapper;

    @MockBean
    private SlugService slugService;

    @MockBean
    private org.springframework.data.redis.connection.RedisConnectionFactory redisConnectionFactory;

    @BeforeEach
    void setUp() {
        // Clear all caches before each test
        cacheManager.getCacheNames().forEach(name -> cacheManager.getCache(name).clear());
    }

    @Test
    void getDetailsById_shouldCacheResult() {
        Long id = 1L;
        TourTemplate template = new TourTemplate();
        template.setId(id);
        TourTemplateResponseDto dto = new TourTemplateResponseDto();
        dto.setId(id);

        when(tourTemplateRepository.findById(id)).thenReturn(Optional.of(template));
        when(tourTemplateMapper.toResponseDto(template)).thenReturn(dto);

        // First call - should hit repository
        tourTemplateService.getDetailsById(id);
        verify(tourTemplateRepository, times(1)).findById(id);

        // Second call - should hit cache
        tourTemplateService.getDetailsById(id);
        verify(tourTemplateRepository, times(1)).findById(id);
    }

    @Test
    void getDetailsBySlug_shouldCacheResult() {
        String slug = "test-tour";
        TourTemplate template = new TourTemplate();
        template.setSlug(slug);
        TourTemplateResponseDto dto = new TourTemplateResponseDto();
        dto.setSlug(slug);

        when(tourTemplateRepository.findBySlug(slug)).thenReturn(Optional.of(template));
        when(tourTemplateMapper.toResponseDto(template)).thenReturn(dto);

        // First call - should hit repository
        tourTemplateService.getDetailsBySlug(slug);
        verify(tourTemplateRepository, times(1)).findBySlug(slug);

        // Second call - should hit cache
        tourTemplateService.getDetailsBySlug(slug);
        verify(tourTemplateRepository, times(1)).findBySlug(slug);
    }

    @Test
    void update_shouldUpdateCachesAndEvictOldSlug() {
        Long id = 1L;
        String oldSlug = "old-slug";
        String newSlug = "new-slug";

        TourTemplate existing = new TourTemplate();
        existing.setId(id);
        existing.setSlug(oldSlug);
        existing.setName("Old Name");

        TourTemplate update = new TourTemplate();
        update.setId(id);
        update.setName("New Name");
        update.setSlug(newSlug);

        TourTemplateResponseDto responseDto = new TourTemplateResponseDto();
        responseDto.setId(id);
        responseDto.setSlug(newSlug);

        when(tourTemplateRepository.findById(id)).thenReturn(Optional.of(existing));
        when(tourTemplateRepository.save(any())).thenReturn(update);
        when(slugService.generateSlug(any())).thenReturn(newSlug);
        when(tourTemplateMapper.toResponseDto(update)).thenReturn(responseDto);

        // Pre-populate caches
        cacheManager.getCache("tourTemplateById").put(id, new TourTemplateResponseDto());
        cacheManager.getCache("tourTemplateBySlug").put(oldSlug, new TourTemplateResponseDto());

        // Perform update
        tourTemplateService.update(update);

        // Verify cache updates
        assertNotNull(cacheManager.getCache("tourTemplateById").get(id));
        assertNotNull(cacheManager.getCache("tourTemplateBySlug").get(newSlug));

        // Verify eviction of old slug
        assertNull(cacheManager.getCache("tourTemplateBySlug").get(oldSlug));
    }

    @Test
    void delete_shouldEvictCaches() {
        Long id = 1L;
        String slug = "test-slug";

        TourTemplate existing = new TourTemplate();
        existing.setId(id);
        existing.setSlug(slug);

        when(tourTemplateRepository.findById(id)).thenReturn(Optional.of(existing));

        // Pre-populate caches
        cacheManager.getCache("tourTemplateById").put(id, new TourTemplateResponseDto());
        cacheManager.getCache("tourTemplateBySlug").put(slug, new TourTemplateResponseDto());

        // Perform delete
        tourTemplateService.delete(id);

        // Verify eviction
        assertNull(cacheManager.getCache("tourTemplateById").get(id));
        assertNull(cacheManager.getCache("tourTemplateBySlug").get(slug));
    }
}
