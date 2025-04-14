package com.nowdo.board.dto;
public class UserInfoDTO {
    private int id;
    private String username;
    private String email;
    private String provider;

    public UserInfoDTO() {}

    public UserInfoDTO(int id, String username, String email, String provider) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.provider = provider;
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
}
