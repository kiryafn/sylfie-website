package com.sylfie.controller;

import com.sylfie.model.entity.User;
import com.sylfie.service.TourTemplateService;
import com.sylfie.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    UserService userService;
    TourTemplateService tourTemplateService;

    public HomeController(UserService userService, TourTemplateService tourTemplateService) {
        this.userService = userService;
        this.tourTemplateService = tourTemplateService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        User user = userService.getById(1L);
        model.addAttribute("user", user);
        model.addAttribute("tourTemplates", tourTemplateService.getTop3Popular());
        return "home";
    }
}
