package com.sylfie.dto.auth;

public class AuthResponseDto {

    private String token;
    private String tokenType;

    public AuthResponseDto() {
        this.tokenType = "Bearer";
    }

    public AuthResponseDto(String token) {
        this.token = token;
        this.tokenType = "Bearer";
    }

    public AuthResponseDto(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}

