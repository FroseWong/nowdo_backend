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
//        return tokenDAO.findByToken(token)
//                .filter(t -> t.getExpiryDate().isAfter(LocalDateTime.now()));
        Optional<PasswordResetTokenEntity> tokenOpt = tokenDAO.findByToken(token);

        if (tokenOpt.isPresent()) {
            PasswordResetTokenEntity t = tokenOpt.get();
            System.out.println("🔍 查到的 token: " + t.getToken());
            System.out.println("🔐 過期時間: " + t.getExpiryDate());
            System.out.println("🕒 現在時間: " + LocalDateTime.now());

            if (t.getExpiryDate().isAfter(LocalDateTime.now())) {
                return Optional.of(t);
            } else {
                System.out.println("⚠️ Token 已過期");
            }
        } else {
            System.out.println("❌ 找不到 token: " + token);
        }

        return Optional.empty();
    }


    @Transactional
    @Override
    public void deleteToken(PasswordResetTokenEntity token) {
        tokenDAO.delete(token);
    }
}
