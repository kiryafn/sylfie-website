package com.sylfie.jwt;

import com.sylfie.exception.InvalidJwtException;
import groovy.util.logging.Slf4j;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtTokenService {
    public enum TokenType {
        ACCESS, REFRESH
    }

    private static final String CLAIM_TOKEN_TYPE = "typ";
    private static final String CLAIM_ROLES = "roles";

    private final Key signingKey;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;
    private final JwtParser jwtParser;

    public JwtTokenService(@Value("${security.jwt.secret}") String secret,
                           @Value("${security.jwt.access-duration-ms}") long accessTokenExpirationMs,
                           @Value("${security.jwt.refresh-duration-ms}") long refreshTokenExpirationMs) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .setAllowedClockSkewSeconds(30)
                .build();
    }

    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(userDetails, TokenType.ACCESS, accessTokenExpirationMs);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails, TokenType.REFRESH, refreshTokenExpirationMs);
    }

    public String generateToken(UserDetails userDetails) {
        return generateAccessToken(userDetails);
    }

    private String buildToken(UserDetails userDetails,
                              TokenType tokenType,
                              long expirationMs) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_TOKEN_TYPE, tokenType.name());
        claims.put(CLAIM_ROLES, userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public TokenType extractTokenType(String token) {
        String type = extractClaim(token, claims -> claims.get(CLAIM_TOKEN_TYPE, String.class));
        return type != null ? TokenType.valueOf(type) : null;
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        List<?> raw = extractClaim(token, claims -> claims.get(CLAIM_ROLES, List.class));
        if (raw == null) {
            return Collections.emptyList();
        }
        return raw.stream().map(Object::toString).collect(Collectors.toList());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = parseClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        return isTokenValidOfType(token, userDetails, TokenType.ACCESS);
    }

    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        return isTokenValidOfType(token, userDetails, TokenType.REFRESH);
    }

    private boolean isTokenValidOfType(String token,
                                       UserDetails userDetails,
                                       TokenType expectedType) {
        Claims claims;
        try {
            claims = parseClaims(token);
        } catch (InvalidJwtException e) {
            return false;
        }

        String username = claims.getSubject();
        String tokenType = claims.get(CLAIM_TOKEN_TYPE, String.class);
        Date expiration = claims.getExpiration();

        boolean usernameMatches = username != null && username.equals(userDetails.getUsername());
        boolean typeMatches = expectedType.name().equals(tokenType);
        boolean notExpired = expiration != null && expiration.after(new Date());

        boolean accountOk = userDetails.isAccountNonExpired()
                && userDetails.isAccountNonLocked()
                && userDetails.isCredentialsNonExpired()
                && userDetails.isEnabled();

        return usernameMatches
                && typeMatches
                && notExpired
                && accountOk;
    }

    public boolean isTokenExpired(String token) {
        return !extractClaim(token, claims -> {
            Date exp = claims.getExpiration();
            return exp != null && exp.after(new Date());
        });
    }

    public String refreshAccessToken(String refreshToken, UserDetails userDetails) {
        if (!isRefreshTokenValid(refreshToken, userDetails)) {
            throw new InvalidJwtException("Refresh token is not valid");
        }
        return generateAccessToken(userDetails);
    }

    private Claims parseClaims(String token) {
        try {
            return jwtParser.parseClaimsJws(token).getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtException("Invalid JWT token", e);
        }
    }
}