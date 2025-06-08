package com.sylfie.controller;

import com.sylfie.dto.UserBalanceDTO;
import com.sylfie.mapper.UserMapper;
import com.sylfie.model.User;
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

    @ModelAttribute("userBalance")
    public UserBalanceDTO getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return null;
        }
        return userMapper.toBalanceDTO(userDetails.getUser());
    }
}