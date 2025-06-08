package com.sylfie.controller.mvc;

import com.sylfie.model.User;
import com.sylfie.security.CustomUserDetails;
import com.sylfie.service.TourTemplateService;
import com.sylfie.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String home(@AuthenticationPrincipal CustomUserDetails userDetails,  Model model) {
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("tourTemplates", tourTemplateService.getTop3Popular());
        return "home";
    }
}
