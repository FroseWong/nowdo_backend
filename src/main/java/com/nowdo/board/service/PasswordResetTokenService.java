package com.nowdo.board.service;

import com.nowdo.board.entity.PasswordResetTokenEntity;

import java.util.Optional;

public interface PasswordResetTokenService {
    String createToken(String email);
    void save(PasswordResetTokenEntity token);
    Optional<PasswordResetTokenEntity> getValidToken(String token);
    void deleteToken(PasswordResetTokenEntity token);
}
