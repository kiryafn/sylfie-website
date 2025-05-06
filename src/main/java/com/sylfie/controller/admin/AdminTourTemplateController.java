package com.sylfie.controller.admin;

import com.sylfie.mapper.TourPictureMapper;
import com.sylfie.model.entity.Difficulty;
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

import java.util.List;

@Controller
@RequestMapping("/admin/tour-templates")
public class AdminTourTemplateController {

    private final UserService userService;
    private final TourCategoryService tourCategoryService;
    private final TourTemplateService tourTemplateService;
    private final TourPictureMapper tourPictureMapper;

    public AdminTourTemplateController(UserService userService, TourCategoryService tourCategoryService, TourTemplateService tourTemplateService, TourPictureMapper tourPictureMapper) {
        this.userService = userService;
        this.tourCategoryService = tourCategoryService;
        this.tourTemplateService = tourTemplateService;
        this.tourPictureMapper = tourPictureMapper;
    }

    @InitBinder("template")
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("pictures");
    }


    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("template", new TourTemplate());
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("user", userService.getById(1L));
        model.addAttribute("categories", tourCategoryService.getAll());
        return "tour-template/add-tour-template";
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String save(
            @ModelAttribute("template") TourTemplate template,
            @RequestParam("pictures") MultipartFile[] pictures){
        List<TourPicture> pics = tourPictureMapper.toEntityList(pictures);
        pics.getFirst().setPreview(true);
        template.setPictures(pics);
        tourTemplateService.create(template);
        return "redirect:/home";

    }
}
