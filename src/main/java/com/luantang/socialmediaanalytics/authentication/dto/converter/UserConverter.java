package com.luantang.socialmediaanalytics.authentication.dto.converter;

import com.luantang.socialmediaanalytics.authentication.model.Role;
import com.luantang.socialmediaanalytics.authentication.dto.RegisterDto;
import com.luantang.socialmediaanalytics.authentication.dto.UserDto;
import com.luantang.socialmediaanalytics.authentication.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserConverter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity registerDtoToNewUser(RegisterDto registerDto) {
        UserEntity user = new UserEntity();
        user.setUserId(UUID.randomUUID());
        user.setFirstname(registerDto.getFirstname());
        user.setLastname(registerDto.getLastname());
        user.setEmail(registerDto.getEmail());
        user.setEnabled(false);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Role.USER);
        return user;
    }

    public UserDto userEntityToDto(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
