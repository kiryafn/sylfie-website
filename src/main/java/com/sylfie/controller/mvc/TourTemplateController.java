package com.sylfie.controller.mvc;


import com.sylfie.dto.TourTemplateDTO;
import com.sylfie.dto.TourTemplateRequestDTO;
import com.sylfie.model.Difficulty;
import com.sylfie.model.Picture;
import com.sylfie.model.TourPicture;
import com.sylfie.model.TourTemplate;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tours")
public class TourTemplateController {
    TourTemplateService tourTemplateService;
    TourCategoryService tourCategoryService;
    UserService userService;
    LocationService locationService;
    PictureService pictureService;
    TourService tourService;

    public TourTemplateController(TourTemplateService tourTemplateService, UserService userService, TourCategoryService tourCategoryService, LocationService locationService, PictureService pictureService, TourService tourService) {
        this.tourTemplateService = tourTemplateService;
        this.userService = userService;
        this.tourCategoryService = tourCategoryService;
        this.locationService = locationService;
        this.pictureService = pictureService;
        this.tourService = tourService;
    }

    @GetMapping("/{slug}")
    public String showTour(@PathVariable String slug, Model model) {
        TourTemplateDTO tt = tourTemplateService.getBySlug(slug);
        model.addAttribute("tourTemplate", tt);
        model.addAttribute("tours", tourService.getByTemplateId(tt.getId()));
        return "tour-template/show-tour-template";
    }

    @GetMapping
    public String showAllTours(
            @RequestParam(required = false) List<Long> categoryId,
            @RequestParam(required = false) List<Difficulty> difficulty,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) List<Long> locationId,
            Model model
    ) {
        List<TourTemplate> templates = tourTemplateService.getAll().stream()
                .filter(t -> categoryId == null  || categoryId.contains(t.getCategory().getId()))
                .filter(t -> difficulty == null  || difficulty.isEmpty() || difficulty.contains(t.getDifficulty()))
                .filter(t -> minCapacity == null || t.getMaxParticipants() >= minCapacity)
                .filter(t -> maxCapacity == null || t.getMaxParticipants() <= maxCapacity)
                .filter(t -> minPrice == null || t.getPrice().compareTo(BigDecimal.valueOf(minPrice)) >= 0)
                .filter(t -> maxPrice == null || t.getPrice().compareTo(BigDecimal.valueOf(maxPrice)) <= 0)
                .filter(t -> locationId == null  || locationId.contains(t.getLocation().getId()))
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

        return "tour-template/show-all-tour-template";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("template", new TourTemplateRequestDTO());
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("locations", locationService.getAll());
        model.addAttribute("categories", tourCategoryService.getAll());
        return "tour-template/add-tour-template";
    }


    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String save(
            //TODO: DTO VALIDATION!!!!!!!!!
            @ModelAttribute("template") TourTemplateRequestDTO template,
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
        TourTemplateDTO ttDto = tourTemplateService.create(template);

        return "redirect:/tours/" + ttDto.getSlug() ;
    }
}
