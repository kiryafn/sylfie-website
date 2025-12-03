package com.sylfie.security;

public class OAuth2UserInfo {
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String provider;

    public OAuth2UserInfo(String email, String name, String sub, String provider) {
        this.email = email;

        String[] names = name != null ? name.trim().split("\\s+") : new String[]{""};
        this.firstName = names.length > 0 ? names[0] : "";
        this.lastName = names.length > 1 ? names[1] : "";
        this.provider = provider;

        this.username = provider + "_" + sub;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getProvider() {
        return provider;
    }
}