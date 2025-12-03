package com.sylfie.controller.rest;

import com.sylfie.dto.auth.AuthResponseDto;
import com.sylfie.dto.auth.LoginDto;
import com.sylfie.dto.auth.RefreshTokenRequestDto;
import com.sylfie.dto.auth.RegisterDto;
import com.sylfie.exception.InvalidJwtException;
import com.sylfie.model.User;
import com.sylfie.security.CustomUserDetails;
import com.sylfie.security.CustomUserDetailsService;
import com.sylfie.jwt.JwtTokenService;
import com.sylfie.service.UserService;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager,
                          CustomUserDetailsService userDetailsService,
                          UserService userService,
                          JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String accessToken = jwtTokenService.generateAccessToken(userDetails);
            String refreshToken = jwtTokenService.generateRefreshToken(userDetails);

            return ResponseEntity.ok(new AuthResponseDto(accessToken, refreshToken));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        try {
            User user = userService.createFromDTO(dto);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String accessToken = jwtTokenService.generateAccessToken(userDetails);
            String refreshToken = jwtTokenService.generateRefreshToken(userDetails);

            return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDto(accessToken, refreshToken));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userDetails.getUser();
        return ResponseEntity.ok(user.getUsername());
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequestDto request) {
        String refreshToken = request.getRefreshToken();

        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.badRequest().body("Refresh token is required");
        }

        try {
            String username = jwtTokenService.extractUsername(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (!jwtTokenService.isRefreshTokenValid(refreshToken, userDetails)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
            }

            String newAccessToken = jwtTokenService.generateAccessToken(userDetails);
            String newRefreshToken = jwtTokenService.generateRefreshToken(userDetails);

            return ResponseEntity.ok(new AuthResponseDto(newAccessToken, newRefreshToken));
        } catch (JwtException | InvalidJwtException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }
}
