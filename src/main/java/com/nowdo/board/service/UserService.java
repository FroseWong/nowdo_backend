package com.nowdo.board.service;

import com.nowdo.board.dto.UserCreateRequestDTO;
import com.nowdo.board.dto.UserCreateResponseDTO;
import com.nowdo.board.dto.UserInfoDTO;
import com.nowdo.board.entity.UserEntity;

import java.util.List;


public interface UserService {

    List<UserEntity> findAll();

    UserEntity findById(int theId);

    UserEntity save(UserEntity theUser);

    UserCreateResponseDTO createNewUser(UserCreateRequestDTO request);

    void deleteById(int theId);

    UserEntity updateUsernameById(int theId, String theUsername);

    UserEntity updatePasswordById(int theId, String thePassword);

    UserEntity findByEmailAndProvider(String email, String provider);

    boolean existsByEmailAndProvider(String email, String privider);

    void updatePasswordByEmailAndProvider(String email, String provider, String newPassword);

    UserInfoDTO updateUsernameByEmailAndProvider(String authHeader, String username);

}
