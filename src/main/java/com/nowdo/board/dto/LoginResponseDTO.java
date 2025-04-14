package com.nowdo.board.dto;

public class LoginResponseDTO {
    private String token;
    private UserInfoDTO user;

    public LoginResponseDTO(String token, UserInfoDTO user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public UserInfoDTO getUser() {
        return user;
    }
}
