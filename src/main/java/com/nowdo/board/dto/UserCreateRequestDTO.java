package com.nowdo.board.dto;

public class UserCreateRequestDTO {
    private String username;
    private String email;
    private String password;
    private String provider;
    private String providerId;

    public UserCreateRequestDTO(String username, String email, String password, String provider, String providerId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProvider() {
        return provider;
    }

    public String getProviderId() {
        return providerId;
    }
}
