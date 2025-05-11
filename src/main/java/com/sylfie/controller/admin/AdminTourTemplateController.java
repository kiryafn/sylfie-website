package com.sylfie.controller.admin;

import com.sylfie.mapper.TourPictureMapper;
import com.sylfie.mapper.TourTemplateMapper;
import com.sylfie.model.dto.TourTemplateRequestDTO;
import com.sylfie.model.entity.Difficulty;
import com.sylfie.model.entity.Location;
import com.sylfie.model.entity.TourPicture;
import com.sylfie.model.entity.TourTemplate;
import com.sylfie.service.TourCategoryService;
import com.sylfie.service.TourTemplateService;
import com.sylfie.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/tour-templates")
public class AdminTourTemplateController {

    private final UserService userService;
    private final TourCategoryService tourCategoryService;
    private final TourTemplateService tourTemplateService;
    private final TourPictureMapper tourPictureMapper;
    private final TourTemplateMapper tourTemplateMapper;

    public AdminTourTemplateController(UserService userService, TourCategoryService tourCategoryService, TourTemplateService tourTemplateService, TourPictureMapper tourPictureMapper, TourTemplateMapper tourTemplateMapper) {
        this.userService = userService;
        this.tourCategoryService = tourCategoryService;
        this.tourTemplateService = tourTemplateService;
        this.tourPictureMapper = tourPictureMapper;
        this.tourTemplateMapper = tourTemplateMapper;
    }

    @InitBinder("template")
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("pictures");
    }


    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("template", new TourTemplateRequestDTO());
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("locations", Location.values());
        model.addAttribute("user", userService.getById(1L));
        model.addAttribute("categories", tourCategoryService.getAll());
        return "tour-template/add-tour-template";
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String save(
            @ModelAttribute("template") TourTemplateRequestDTO template,
            @RequestParam("previewPic") MultipartFile previewPic,
            @RequestParam("pictures") MultipartFile[] pictures) throws IOException {
        List<TourPicture> pics = tourPictureMapper.toEntityList(pictures);
        TourPicture preview = tourPictureMapper.toEntity(previewPic);
        preview.setPreview(true);
        pics.add(preview);
        template.setPictures(pics);
        tourTemplateService.create(template);
        return "redirect:/admin/tour-templates/add";

    }
}
