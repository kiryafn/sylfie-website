package com.sylfie.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenServiceTest {

    private JwtTokenService jwtTokenService;
    private final String secret = "mySecretKeyWhichMustBeLongEnoughForHS256Algorithm";
    private final long accessTokenDuration = 3600000; // 1 hour
    private final long refreshTokenDuration = 86400000; // 24 hours

    @BeforeEach
    void setUp() {
        jwtTokenService = new JwtTokenService(secret, accessTokenDuration, refreshTokenDuration);
    }

    @Test
    void generateAccessToken_shouldGenerateValidToken() {
        UserDetails userDetails = new User("testuser", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtTokenService.generateAccessToken(userDetails);

        assertNotNull(token);
        assertEquals("testuser", jwtTokenService.extractUsername(token));
        assertEquals(JwtTokenService.TokenType.ACCESS, jwtTokenService.extractTokenType(token));
        assertTrue(jwtTokenService.isAccessTokenValid(token, userDetails));
    }

    @Test
    void generateRefreshToken_shouldGenerateValidToken() {
        UserDetails userDetails = new User("testuser", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtTokenService.generateRefreshToken(userDetails);

        assertNotNull(token);
        assertEquals("testuser", jwtTokenService.extractUsername(token));
        assertEquals(JwtTokenService.TokenType.REFRESH, jwtTokenService.extractTokenType(token));
        assertTrue(jwtTokenService.isRefreshTokenValid(token, userDetails));
    }

    @Test
    void extractRoles_shouldReturnRoles() {
        UserDetails userDetails = new User("testuser", "password",
                List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN")));
        String token = jwtTokenService.generateAccessToken(userDetails);

        List<String> roles = jwtTokenService.extractRoles(token);
        assertNotNull(roles);
        assertEquals(2, roles.size());
        assertTrue(roles.contains("ROLE_USER"));
        assertTrue(roles.contains("ROLE_ADMIN"));
    }

    @Test
    void isAccessTokenValid_shouldReturnFalse_whenTokenIsExpired() {
        // Create a service with short expiration
        JwtTokenService shortLivedService = new JwtTokenService(secret, -1000, refreshTokenDuration);
        UserDetails userDetails = new User("testuser", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String token = shortLivedService.generateAccessToken(userDetails);

        assertFalse(shortLivedService.isAccessTokenValid(token, userDetails));
    }

    @Test
    void isAccessTokenValid_shouldReturnFalse_whenTokenTypeIsWrong() {
        UserDetails userDetails = new User("testuser", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String refreshToken = jwtTokenService.generateRefreshToken(userDetails);

        assertFalse(jwtTokenService.isAccessTokenValid(refreshToken, userDetails));
    }

    @Test
    void isAccessTokenValid_shouldReturnFalse_whenUsernameDoesNotMatch() {
        UserDetails userDetails = new User("testuser", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtTokenService.generateAccessToken(userDetails);

        UserDetails otherUser = new User("otheruser", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        assertFalse(jwtTokenService.isAccessTokenValid(token, otherUser));
    }
}
