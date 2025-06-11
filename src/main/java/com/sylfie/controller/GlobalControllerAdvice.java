package com.sylfie.controller;

import com.sylfie.dto.mvc.UserInfoDTO;
import com.sylfie.mapper.UserMapper;
import com.sylfie.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserMapper userMapper;

    public GlobalControllerAdvice(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

        @ModelAttribute("userInfo")
    public UserInfoDTO getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return null;
        }
        return userMapper.toInfoDTO(userDetails.getUser());
    }
}