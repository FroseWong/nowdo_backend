package com.nowdo.board.dto;

public class PasswordUpdateDTO {
    private int id;
    private String password;

    public PasswordUpdateDTO(){
    }

    public PasswordUpdateDTO(int id, String password) {
        this.id = id;
        this.password = password;
    }

    // getter & setter
    public int getId() { return id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
