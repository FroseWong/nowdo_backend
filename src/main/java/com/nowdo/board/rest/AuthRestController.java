package com.nowdo.board.rest;

import com.nowdo.board.dto.LoginRequestDTO;
import com.nowdo.board.dto.LoginResponseDTO;
import com.nowdo.board.entity.PasswordResetTokenEntity;
import com.nowdo.board.security.JwtUtil;
import com.nowdo.board.service.AuthService;
import com.nowdo.board.service.EmailService;
import com.nowdo.board.service.PasswordResetTokenService;
import com.nowdo.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private JwtUtil jwtUtil;
    private AuthService authService;
    private UserService userService;
    private EmailService emailService;
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public AuthRestController(AuthService theAuthService, JwtUtil theJwtUtil, UserService theUserService, EmailService theEmailService, PasswordResetTokenService thePasswordResetTokenService) {

        authService = theAuthService;
        jwtUtil = theJwtUtil;
        userService = theUserService;
        emailService = theEmailService;
        passwordResetTokenService = thePasswordResetTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            LoginResponseDTO response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String provider = "local";

        if (!userService.existsByEmailAndProvider(email, provider)) {
            return ResponseEntity.status(404).body( Map.of(
                    "success", false,
                    "message", "找不到此Email"
            ));
        }

        String token = UUID.randomUUID().toString();
        PasswordResetTokenEntity resetToken = new PasswordResetTokenEntity();
        resetToken.setEmail(email);
        resetToken.setToken(token);
//        resetToken.setExpiryDate(LocalDateTime.now().plusSeconds(1));
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        passwordResetTokenService.save(resetToken);

        emailService.sendResetPasswordEmail(email, token);
        return ResponseEntity.ok("已寄送重設連結");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> req) {
        String token = req.get("token");
        String newPassword = req.get("password");
        String provider = "local";


        Optional<PasswordResetTokenEntity> tokenOpt = passwordResetTokenService.getValidToken(token);
        if (tokenOpt.isEmpty()) {
            return ResponseEntity.status(400).body( Map.of(
                    "success", false,
                    "message", "Token 無效或已過期"
            ));
        }

        String email = tokenOpt.get().getEmail();
        userService.updatePasswordByEmailAndProvider(email, provider, newPassword);
        passwordResetTokenService.deleteToken(tokenOpt.get());

        return ResponseEntity.ok("密碼已更新");
    }
}