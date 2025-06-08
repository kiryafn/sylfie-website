package com.sylfie.controller.mvc;

import com.sylfie.dto.UserProfileDTO;
import com.sylfie.mapper.UserMapper;
import com.sylfie.model.Avatar;
import com.sylfie.model.Picture;
import com.sylfie.security.CustomUserDetails;
import com.sylfie.service.PictureService;
import com.sylfie.service.StorageService;
import com.sylfie.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("profile")
public class UserProfileController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PictureService pictureService;

    public UserProfileController(UserService userService, UserMapper userMapper, PictureService pictureService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.pictureService = pictureService;
    }

    @GetMapping
    public String getProfilePage(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        var user = principal.getUser();
        model.addAttribute("user", userMapper.toProfileDTO(user));
        return "profile";
    }

    @PostMapping("save")
    public String saveProfile(@AuthenticationPrincipal CustomUserDetails principal,
                              @ModelAttribute("user") UserProfileDTO dto,
                              @RequestParam("avatarFile") MultipartFile avatarFile) throws IOException {

        var user = principal.getUser();

        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setDateOfBirth(dto.getDateOfBirth());

        if (!dto.getPassword().isBlank()) {
            user.setPassword(dto.getPassword());
        }

        if (avatarFile != null && !avatarFile.isEmpty()) {
            Picture picture = pictureService.saveAvatar(avatarFile);
            user.setAvatar(new Avatar(picture));
        }

        userService.update(user);

        return "redirect:/profile";
    }
}