package com.nowdo.board.service;

public interface EmailService {
    void sendResetPasswordEmail(String toEmail, String token);
}
