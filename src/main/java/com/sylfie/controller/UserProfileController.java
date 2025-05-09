package com.sylfie.controller;

import com.sylfie.model.entity.User;
import com.sylfie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class UserProfileController {

    private final UserService userService;

    @Autowired
    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/{username}")
    public String getProfilePage(Model model, Principal principal, @PathVariable String username) {
        User user = userService.getByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

//    @PostMapping("/profile/update")
//    public String updateProfile(User user, @RequestParam("avatarFile") MultipartFile avatarFile, Principal principal) {
//        userService.updateUserProfile(user, avatarFile, principal.getName());
//        return "redirect:/profile?success";
//    }
}