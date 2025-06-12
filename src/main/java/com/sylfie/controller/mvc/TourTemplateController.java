package com.sylfie.controller.mvc;


import com.sylfie.dto.tour.template.TourTemplateResponseDto;
import com.sylfie.dto.tour.template.TourTemplateCreateDto;
import com.sylfie.mapper.TourTemplateMapper;
import com.sylfie.model.Difficulty;
import com.sylfie.model.Picture;
import com.sylfie.model.TourPicture;
import com.sylfie.security.CustomUserDetails;
import com.sylfie.service.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tours")
public class TourTemplateController {
    private final TourTemplateMapper tourTemplateMapper;
    TourTemplateService tourTemplateService;
    TourCategoryService tourCategoryService;
    UserService userService;
    LocationService locationService;
    PictureService pictureService;
    TourService tourService;

    public TourTemplateController(TourTemplateService tourTemplateService, UserService userService, TourCategoryService tourCategoryService, LocationService locationService, PictureService pictureService, TourService tourService, TourTemplateMapper tourTemplateMapper) {
        this.tourTemplateService = tourTemplateService;
        this.userService = userService;
        this.tourCategoryService = tourCategoryService;
        this.locationService = locationService;
        this.pictureService = pictureService;
        this.tourService = tourService;
        this.tourTemplateMapper = tourTemplateMapper;
    }

    @GetMapping("/{slug}")
    public String showTour(@PathVariable String slug, Model model) {
        TourTemplateResponseDto tt = tourTemplateMapper.toDto(tourTemplateService.getBySlug(slug));
        model.addAttribute("tourTemplate", tt);
        model.addAttribute("tours", tourService.getAvailableByTemplateId(tt.getId()));
        return "tour-template/show-tour-template";
    }

    @GetMapping
    public String showAllTours(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestParam(required = false) List<Long> categoryId,
            @RequestParam(required = false) List<Difficulty> difficulty,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) List<Long> locationId,
            Model model
    ) {
        List<TourTemplateResponseDto> templates = tourTemplateService.getAll().stream()
                .filter(t -> categoryId == null  || categoryId.contains(t.getCategory().getId()))
                .filter(t -> difficulty == null  || difficulty.isEmpty() || difficulty.contains(t.getDifficulty()))
                .filter(t -> minCapacity == null || t.getMaxParticipants() >= minCapacity)
                .filter(t -> maxCapacity == null || t.getMaxParticipants() <= maxCapacity)
                .filter(t -> minPrice == null || t.getPrice().compareTo(BigDecimal.valueOf(minPrice)) >= 0)
                .filter(t -> maxPrice == null || t.getPrice().compareTo(BigDecimal.valueOf(maxPrice)) <= 0)
                .filter(t -> locationId == null  || locationId.contains(t.getLocation().getId()))
                .map(tourTemplateMapper::toDto)
                .toList();

        model.addAttribute("templates", templates);
        model.addAttribute("categories", tourCategoryService.getAll());
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("locations", locationService.getAll());

        model.addAttribute("selectedCategoryIds", categoryId);
        model.addAttribute("selectedDifficulties", difficulty);
        model.addAttribute("selectedLocationIds", locationId);

        model.addAttribute("minCapacity", minCapacity);
        model.addAttribute("maxCapacity", maxCapacity);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        model.addAttribute("userFavouritesIds", userService.getFavouriteTourIds(principal.getName()));

        return "tour-template/show-all-tour-template";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("template", new TourTemplateCreateDto());
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("locations", locationService.getAll());
        model.addAttribute("categories", tourCategoryService.getAll());
        return "tour-template/add-tour-template";
    }


    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String save(
            //TODO: DTO VALIDATION!!!!!!!!!
            @ModelAttribute("template") TourTemplateCreateDto template,
            @RequestParam("previewPic") MultipartFile previewPic,
            @RequestParam("pictures") MultipartFile[] pictures) throws IOException {

        List<TourPicture> pics = new ArrayList<>();

        if(pictures != null && pictures.length > 0){
            for (MultipartFile file : pictures) {
                Picture picture = pictureService.saveTourPicture(file);
                TourPicture tourPicture = new TourPicture();
                tourPicture.setPicture(picture);
                tourPicture.setPreview(false);
                pics.add(tourPicture);
            }

        }

        Picture previewPicture = pictureService.saveTourPicture(previewPic);
        TourPicture preview = new TourPicture();
        preview.setPicture(previewPicture);
        preview.setPreview(true);
        pics.add(preview);

        template.setTourPictures(pics);
        TourTemplateResponseDto ttDto = tourTemplateService.create(template);

        return "redirect:/tours/" + ttDto.getSlug() ;
    }
}
