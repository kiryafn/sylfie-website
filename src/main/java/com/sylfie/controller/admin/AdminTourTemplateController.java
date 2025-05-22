package com.sylfie.controller.admin;

import com.sylfie.mapper.PictureMapper;
import com.sylfie.mapper.TourTemplateMapper;
import com.sylfie.model.dto.TourTemplateRequestDTO;
import com.sylfie.model.entity.Difficulty;
import com.sylfie.model.entity.Picture;
import com.sylfie.model.entity.TourPicture;
import com.sylfie.service.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/tour-templates")
public class AdminTourTemplateController {

    private final UserService userService;
    private final TourCategoryService tourCategoryService;
    private final TourTemplateService tourTemplateService;
    private final LocationService locationService;
    private final PictureService pictureService;


    public AdminTourTemplateController(UserService userService,
                                       TourCategoryService tourCategoryService,
                                       TourTemplateService tourTemplateService,
                                       LocationService locationService,
                                       PictureService pictureService) {
        this.userService = userService;
        this.tourCategoryService = tourCategoryService;
        this.tourTemplateService = tourTemplateService;
        this.locationService = locationService;
        this.pictureService = pictureService;
    }

    @InitBinder("template")
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("pictures");
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

    // ... в конструкторе — добавить pictureService

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String save(
            @ModelAttribute("template") TourTemplateRequestDTO template,
            @RequestParam("previewPic") MultipartFile previewPic,
            @RequestParam("pictures") MultipartFile[] pictures) throws IOException {

        List<TourPicture> pics = new ArrayList<>();
        // Сохраняем обычные картинки
        for (MultipartFile file : pictures) {
            Picture picture = pictureService.save(file);
            TourPicture tourPicture = new TourPicture();
            tourPicture.setPicture(picture);
            tourPicture.setPreview(false);
            pics.add(tourPicture);
        }

        // Сохраняем preview картинку
        Picture previewPicture = pictureService.save(previewPic);
        TourPicture preview = new TourPicture();
        preview.setPicture(previewPicture);
        preview.setPreview(true);
        pics.add(preview);

        template.setPictures(pics);
        tourTemplateService.create(template);

        return "redirect:/admin/tour-templates/add";
    }

}
