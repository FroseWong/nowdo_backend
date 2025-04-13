package com.nowdo.board.service;

import com.nowdo.board.dao.UserDAO;
import com.nowdo.board.dto.UserCreateRequestDTO;
import com.nowdo.board.dto.UserCreateResponseDTO;
import com.nowdo.board.dto.UserInfoDTO;
import com.nowdo.board.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private AuthService authService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserDAO theUserDAO, AuthService theAuthService) {
        userDAO = theUserDAO;
        authService = theAuthService;
    }

    @Override
    public List<UserEntity> findAll() {
        return userDAO.findAll();
    }

    @Override
    public UserEntity findById(int theId) {
        return userDAO.findById(theId);
    }

    @Transactional
    @Override
    public UserCreateResponseDTO createNewUser(UserCreateRequestDTO request) {

        String email = request.getEmail();
        String originPassword = request.getPassword();
        String provider = request.getProvider();
        String providerId = request.getProviderId();

        if (existsByEmailAndProvider(email, provider)) {
            throw new IllegalArgumentException("此信箱已被註冊");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setProvider(provider);
        user.setProviderId(providerId); // optional
        user.setRemark(originPassword);

        UserEntity savedUser = userDAO.save(user);


        return new UserCreateResponseDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getProvider(),
                savedUser.getProviderId()
        );

    }

    @Transactional
    @Override
    public UserEntity save(UserEntity theUser) {

        if ("local".equals(theUser.getProvider())) {
            String rawPassword = theUser.getPassword();
            String encodedPassword = passwordEncoder.encode(rawPassword);
            theUser.setPassword(encodedPassword);
        }
        return userDAO.save(theUser);
    }

    @Transactional
    @Override
    public void deleteById(int theId) {
        userDAO.deleteById(theId);
    }

    @Transactional
    @Override
    public UserEntity updateUsernameById(int theid, String theUsername) {

        return userDAO.updateUsernameById(theid, theUsername);
    }

    @Transactional
    @Override
    public UserEntity updatePasswordById(int theid, String thePassword) {
        return userDAO.updatePasswordById(theid, thePassword);
    }

    @Override
    public UserEntity findByEmailAndProvider(String email, String provider) {
        return userDAO.findUserByEmailAndProvider(email, provider);
    }

    @Override
    public boolean existsByEmailAndProvider(String email, String provider) {
        return userDAO.existsByEmailAndProvider(email, provider);
    }

    @Transactional
    @Override
    public void updatePasswordByEmailAndProvider(String email, String provider, String newPassword) {
        String encoded = passwordEncoder.encode(newPassword);
        userDAO.updatePasswordByEmailAndProvider(email, provider, encoded, newPassword);
    }

    @Transactional
    @Override
    public UserInfoDTO updateUsernameByEmailAndProvider(String authHeader, String username) {

        UserEntity user = authService.getUserFromToken(authHeader);

        UserEntity updatedUser = userDAO.updateUsernameByEmailAndProvider(user.getEmail(), user.getProvider(), username);

        return new UserInfoDTO(
                updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail(), updatedUser.getProvider()
        );
    }
}
