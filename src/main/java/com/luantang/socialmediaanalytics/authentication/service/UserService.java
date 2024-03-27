package com.luantang.socialmediaanalytics.authentication.service;

import com.luantang.socialmediaanalytics.authentication.model.UserEntity;

import java.util.List;


public interface UserService {
    UserEntity createUser(UserEntity user);

    List<UserEntity> getUsers();

    UserEntity getUserByEmail(String email);

    UserEntity enableUserByEmail(String email);
}
