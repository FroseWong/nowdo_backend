package com.nowdo.board.service;

import com.nowdo.board.dto.LoginRequestDTO;
import com.nowdo.board.dto.LoginResponseDTO;
import com.nowdo.board.entity.UserEntity;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
    UserEntity getUserFromToken(String authHeader);
}
