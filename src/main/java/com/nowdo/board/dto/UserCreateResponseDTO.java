package com.nowdo.board.dto;

public class UserCreateResponseDTO {
    private int id;
    private String username;
    private String email;
    private String provider;
    private String providerId;

    public UserCreateResponseDTO(int id, String username, String email, String provider, String providerId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getProvider() {
        return provider;
    }

    public String getProviderId() {
        return providerId;
    }
}
