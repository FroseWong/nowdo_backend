package com.nowdo.board.service;

import com.nowdo.board.dao.UserDAO;
import com.nowdo.board.dto.LoginRequestDTO;
import com.nowdo.board.dto.LoginResponseDTO;
import com.nowdo.board.dto.UserInfoDTO;
import com.nowdo.board.entity.UserEntity;
import com.nowdo.board.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDAO userDAO;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        UserEntity user = userDAO.findUserByEmailAndProvider(request.getEmail(), request.getProvider());

        // 用 BCrypt 驗證密碼是否吻合
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean passwordMatches = user != null && passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new RuntimeException("帳號或密碼錯誤");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getProvider());
        System.out.println("🔐 Token created for:");
        System.out.println("Email: " + user.getEmail());
        System.out.println("Provider: " + user.getProvider());
        System.out.println("JWT: " + token);

        UserInfoDTO userInfo = new UserInfoDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getProvider()
        );

        return new LoginResponseDTO(token, userInfo);
    }

    @Override
    public UserEntity getUserFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("缺少或無效的授權資訊");
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        String provider = jwtUtil.extractProvider(token);

        UserEntity user = userDAO.findUserByEmailAndProvider(email, provider);
        if (user == null) {
            throw new RuntimeException("找不到使用者");
        }

        return user;
    }
}
