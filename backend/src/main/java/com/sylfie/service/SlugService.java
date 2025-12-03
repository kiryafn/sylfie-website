package com.sylfie.service;

import com.github.slugify.Slugify;
import com.sylfie.model.TourTemplate;
import com.sylfie.repository.TourTemplateRepository;
import org.springframework.stereotype.Service;

@Service
public class SlugService {
    private final Slugify slugify;
    private final TourTemplateRepository tourTemplateRepository;

    public SlugService(Slugify slugify, TourTemplateRepository tourTemplateRepository) {
        this.slugify = slugify;
        this.tourTemplateRepository = tourTemplateRepository;
    }

    public String generateSlug(TourTemplate template) {
        String base = slugify.slugify(template.getName());
        String slug = base;
        int i = 1;
        while (tourTemplateRepository.existsBySlug(slug)) {
            slug = base + "-" + i++;
        }
        return slug;
    }
}
