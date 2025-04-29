package com.sylfie.controller;

import com.sylfie.model.entity.User;
import com.sylfie.service.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class HomeController {
    UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        User user = userService.getById(1L);
        model.addAttribute("user", user);
        return "index";
    }


}
