package com.sylfie.controller;


import com.sylfie.model.entity.*;
import com.sylfie.repository.TourCategoryRepository;
import com.sylfie.service.TourCategoryService;
import com.sylfie.service.TourTemplateService;
import com.sylfie.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tours")
public class TourTemplateController {
    TourTemplateService tourTemplateService;
    TourCategoryService tourCategoryService;
    UserService userService;

    public TourTemplateController(TourTemplateService tourTemplateService, UserService userService, TourCategoryRepository tourCategoryRepository) {
        this.tourTemplateService = tourTemplateService;
        this.userService = userService;
        this.tourCategoryService = new TourCategoryService(tourCategoryRepository);
    }

    @GetMapping("/{id}")
    public String showTour(@PathVariable Long id, Model model) {
        TourTemplate tourTemplate = tourTemplateService.getById(id);
        model.addAttribute("tourTemplate", tourTemplate);
        model.addAttribute("user", userService.getById(1L));
        return "tour-template/show-tour-template";
    }

    @GetMapping
    public String showAllTours(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Difficulty difficulty,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity,
            Model model) {


        List<TourTemplate> templates = tourTemplateService.getAll().stream()
                .filter(t -> categoryId  == null || t.getCategory().getId().equals(categoryId))
                .filter(t -> difficulty  == null || t.getDifficulty() == difficulty)
                .filter(t -> minCapacity == null || t.getCapacity() >= minCapacity)
                .filter(t -> maxCapacity == null || t.getCapacity() <= maxCapacity)
                .collect(Collectors.toList());


        model.addAttribute("tourTemplates",  templates);
        model.addAttribute("categories",     tourCategoryService.getAll());
        model.addAttribute("difficulties",   Difficulty.values());

        model.addAttribute("selectedCategory",   categoryId);
        model.addAttribute("selectedDifficulty", difficulty);
        model.addAttribute("minCapacity",        minCapacity);
        model.addAttribute("maxCapacity",        maxCapacity);

        model.addAttribute("user", userService.getById(1L));
        return "tour-template/show-all-tour-template";
    }
}
