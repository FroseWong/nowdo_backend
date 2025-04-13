package com.nowdo.board.rest;

import com.nowdo.board.dto.*;
import com.nowdo.board.entity.UserEntity;
import com.nowdo.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService theUserService) {
        userService = theUserService;
    }

//    @CrossOrigin(origins = "*")
    @GetMapping("")
    public List<UserEntity> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public UserEntity getUser(@PathVariable int userId) {
        UserEntity theUser = userService.findById(userId);

        if (theUser == null) {
            throw new RuntimeException("User id not found - " + userId);
        }

        return theUser;
    }

    @PostMapping("")
    public UserCreateResponseDTO addUser(@RequestBody UserCreateRequestDTO request) {
//        System.out.println("theUser" + theUser);
        UserCreateResponseDTO responseDTO = userService.createNewUser(request);
        return responseDTO;
    }

    @PatchMapping("")
    public UserEntity updateUser(@RequestBody UserEntity theUser) {
        UserEntity dbUser = userService.save(theUser);
        return dbUser;
    }

//    @PatchMapping("username")
//    public UserEntity updateUsernameById(@RequestBody UsernameUpdateDTO dto) {
//        UserEntity dbUser = userService.updateUsernameById(dto.getId(), dto.getUsername());
//        return dbUser;
//    }

    @PatchMapping("password")
    public UserEntity updatePasswordById(@RequestBody PasswordUpdateDTO dto) {
        UserEntity dbUser = userService.updatePasswordById(dto.getId(), dto.getPassword());
        return dbUser;
    }

    @PatchMapping("username")
    public UserInfoDTO updateUsernameByEmailAndProvider(@RequestHeader("Authorization") String authHeader, @RequestBody UsernameUpdateDTO dto) {
        UserInfoDTO returnDto = userService.updateUsernameByEmailAndProvider(authHeader, dto.getUsername());
        return returnDto;
    }

}
