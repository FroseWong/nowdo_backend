package com.nowdo.board.service;

import com.nowdo.board.dao.PasswordResetTokenDAO;
import com.nowdo.board.entity.PasswordResetTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private PasswordResetTokenDAO tokenDAO;

    public PasswordResetTokenServiceImpl(PasswordResetTokenDAO theTokenDAO) {
        tokenDAO = theTokenDAO;
    }

    @Transactional
    @Override
    public String createToken(String email) {
        String token = UUID.randomUUID().toString();
        PasswordResetTokenEntity resetToken = new PasswordResetTokenEntity(email, token, LocalDateTime.now().plusHours(1));
        tokenDAO.save(resetToken);
        return token;
    }

    @Transactional
    @Override
    public void save(PasswordResetTokenEntity token) {
        tokenDAO.save(token);
    }

    @Override
    public Optional<PasswordResetTokenEntity> getValidToken(String token) {
        return tokenDAO.findByToken(token)
                .filter(t -> t.getExpiryDate().isAfter(LocalDateTime.now()));
    }


    @Transactional
    @Override
    public void deleteToken(PasswordResetTokenEntity token) {
        tokenDAO.delete(token);
    }
}
