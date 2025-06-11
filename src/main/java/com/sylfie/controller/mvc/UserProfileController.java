package com.sylfie.controller.mvc;

import com.sylfie.dto.UserInfoDTO;
import com.sylfie.mapper.UserMapper;
import com.sylfie.security.CustomUserDetails;
import com.sylfie.service.PictureService;
import com.sylfie.service.TourHistoryService;
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
    private final TourHistoryService tourHistoryService;

    public UserProfileController(UserService userService, UserMapper userMapper, PictureService pictureService, TourHistoryService tourHistoryService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.pictureService = pictureService;
        this.tourHistoryService = tourHistoryService;
    }

    @GetMapping
    public String getProfilePage(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        model.addAttribute("userInfo", userMapper.toInfoDTO(userService.getByUsername(principal.getName())));
        return "profile";
    }

    @PostMapping("update")
    public String updateProfile(@AuthenticationPrincipal CustomUserDetails principal,
                                @ModelAttribute("userInfo") UserInfoDTO dto) throws IOException {
        userService.update(dto, principal.getName());
        return "redirect:/profile";
    }

    @PostMapping("change-password")
    public String changePassword(@AuthenticationPrincipal CustomUserDetails principal,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmNewPassword) {
        userService.changePassword(principal.getName(), currentPassword, newPassword, confirmNewPassword);
        return "redirect:/profile";
    }

    @PostMapping("avatar")
    public String updateAvatar(@AuthenticationPrincipal CustomUserDetails principal, @RequestParam MultipartFile avatar) throws IOException {
        userService.updateAvatar(principal.getName(), avatar);
        return "redirect:/profile";
    }

    @GetMapping("/history")
    public String getHistoryPage(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        model.addAttribute("historyList", tourHistoryService.getByUserName(principal.getName()));
        return "profile/booking-history";
    }
}