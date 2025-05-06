package com.sylfie.controller;


import com.sylfie.model.entity.TourTemplate;
import com.sylfie.model.entity.User;
import com.sylfie.service.TourTemplateService;
import com.sylfie.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/tours")
public class TourController {
    TourTemplateService tourTemplateService;
    UserService userService;

    public TourController(TourTemplateService tourTemplateService, UserService userService) {
        this.tourTemplateService = tourTemplateService;
        this.userService = userService;
    }

//    @GetMapping("/{id}")
//    public String showTour(@PathVariable Long id, Model model) {
//        TourTemplate tourTemplate = tourTemplateService.getById(id);
//        model.addAttribute("user", userService.getById(1L));
//        model.addAttribute("tourTemplate", tourTemplate);
//        return "/tour/show-tour";
//    }
@GetMapping("/{id}")
public String showTour(@PathVariable Long id, Model model) {
    TourTemplate tourTemplate = tourTemplateService.getById(id);
    model.addAttribute("tourTemplate", tourTemplate);
    model.addAttribute("user", userService.getById(1L));
    return "tour-template/show-tour-template";
}

    @GetMapping
    public String showAllTours(Model model) {
        List<TourTemplate> templates = tourTemplateService.getAll();
        model.addAttribute("tourTemplates", templates);
        User user = userService.getById(1L);
        model.addAttribute("user", user);
        return "tour-template/show-all-tour-template";
    }



}
