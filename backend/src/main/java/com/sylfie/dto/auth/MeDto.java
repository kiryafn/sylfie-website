package com.sylfie.dto.auth;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record MeDto(
        String username,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate dateOfBirth,
        BigDecimal balance,
        BigDecimal bonusBalance,
        String avatarUrl,
        List<String> roles
) {}