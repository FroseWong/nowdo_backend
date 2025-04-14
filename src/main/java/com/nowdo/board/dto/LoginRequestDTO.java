package com.nowdo.board.dto;

public class LoginRequestDTO {
    private String email;
    private String password;
    private String provider;

    public LoginRequestDTO(String email, String password, String provider) {
        this.email = email;
        this.password = password;
        this.provider = provider;
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
}
