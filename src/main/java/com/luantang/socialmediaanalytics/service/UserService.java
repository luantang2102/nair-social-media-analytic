package com.luantang.socialmediaanalytics.service;

import com.luantang.socialmediaanalytics.model.UserEntity;

import java.util.List;


public interface UserService {
    UserEntity createUser(UserEntity user);

    List<UserEntity> getUsers();

    UserEntity getUserByEmail(String email);

    UserEntity enableUserByEmail(String email);
}
