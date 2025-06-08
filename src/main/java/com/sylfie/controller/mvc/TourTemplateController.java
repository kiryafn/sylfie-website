package com.sylfie.controller.mvc;


import com.sylfie.dto.TourTemplateRequestDTO;
import com.sylfie.model.Difficulty;
import com.sylfie.model.Picture;
import com.sylfie.model.TourPicture;
import com.sylfie.model.TourTemplate;
import com.sylfie.repository.TourCategoryRepository;
import com.sylfie.service.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tours")
public class TourTemplateController {
    TourTemplateService tourTemplateService;
    TourCategoryService tourCategoryService;
    UserService userService;
    LocationService locationService;
    PictureService pictureService;

    public TourTemplateController(TourTemplateService tourTemplateService, UserService userService, TourCategoryService tourCategoryService, LocationService locationService, PictureService pictureService) {
        this.tourTemplateService = tourTemplateService;
        this.userService = userService;
        this.tourCategoryService = tourCategoryService;
        this.locationService = locationService;
        this.pictureService = pictureService;
    }

    @GetMapping("/{slug}")
    public String showTour(@PathVariable String slug, Model model) {
        TourTemplate tourTemplate = tourTemplateService.getBySlug(slug);
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
                .filter(t -> minCapacity == null || t.getMaxParticipants() >= minCapacity)
                .filter(t -> maxCapacity == null || t.getMaxParticipants() <= maxCapacity)
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

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("template", new TourTemplateRequestDTO());
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("locations", locationService.getAll());
        model.addAttribute("user", userService.getById(1L));
        model.addAttribute("categories", tourCategoryService.getAll());
        return "tour-template/add-tour-template";
    }


    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String save(
            @ModelAttribute("template") TourTemplateRequestDTO template,
            @RequestParam("previewPic") MultipartFile previewPic,
            @RequestParam("pictures") MultipartFile[] pictures) throws IOException {

        List<TourPicture> pics = new ArrayList<>();

        for (MultipartFile file : pictures) {
            Picture picture = pictureService.saveTourPicture(file);
            TourPicture tourPicture = new TourPicture();
            tourPicture.setPicture(picture);
            tourPicture.setPreview(false);
            pics.add(tourPicture);
        }

        Picture previewPicture = pictureService.saveTourPicture(previewPic);
        TourPicture preview = new TourPicture();
        preview.setPicture(previewPicture);
        preview.setPreview(true);
        pics.add(preview);

        template.setPictures(pics);
        tourTemplateService.create(template);

        return "redirect:/admin/tour-templates/add";
    }
}
