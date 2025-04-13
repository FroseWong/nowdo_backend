package com.nowdo.board.dao;

import com.nowdo.board.entity.UserEntity;

import java.util.List;

public interface UserDAO {
    List<UserEntity> findAll();

    UserEntity findById(int theId);

    UserEntity save(UserEntity theUser);

    void deleteById(int theId);

    UserEntity updateUsernameById(int theId, String theUsername);
    UserEntity updatePasswordById(int theId, String thePassword);
    UserEntity findUserByEmailAndProvider(String theEmail, String theProvider);

    boolean existsByEmailAndProvider(String email, String provider);
    void updatePasswordByEmailAndProvider(String email, String provider, String newPassword, String decodePassword);
    UserEntity updateUsernameByEmailAndProvider(String email, String provider, String username);
}
