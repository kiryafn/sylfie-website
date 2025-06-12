package com.sylfie.controller;

import com.sylfie.dto.user.UserResponseDto;
import com.sylfie.mapper.UserMapper;
import com.sylfie.security.CustomUserDetails;
import com.sylfie.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserService userService;
    private final UserMapper userMapper;

    public GlobalControllerAdvice(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

        @ModelAttribute("userInfo")
    public UserResponseDto getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return null;
        }

        return userMapper.toInfoDTO(userService.getByUsername(userDetails.getName()));
    }
}