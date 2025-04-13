package com.nowdo.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDTO {
    private int id;
    private String username;
    private String email;
    private String privider;
}
