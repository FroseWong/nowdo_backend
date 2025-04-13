package com.nowdo.board.dao;

import com.nowdo.board.entity.PasswordResetTokenEntity;

import java.util.Optional;

public interface PasswordResetTokenDAO {
    void save(PasswordResetTokenEntity token);
    Optional<PasswordResetTokenEntity> findByToken(String token);
    void delete(PasswordResetTokenEntity token);
}
