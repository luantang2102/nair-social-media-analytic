package com.luantang.socialmediaanalytics.controller;

import com.luantang.socialmediaanalytics.dto.UserDto;
import com.luantang.socialmediaanalytics.dto.converter.UserConverter;
import com.luantang.socialmediaanalytics.model.UserEntity;
import com.luantang.socialmediaanalytics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;

    @Autowired
    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserEntity> userEntityList = userService.getUsers();
        return new ResponseEntity<>(userEntityList.stream().map(userConverter::userEntityToDto).toList(), HttpStatus.OK);
    }

}
