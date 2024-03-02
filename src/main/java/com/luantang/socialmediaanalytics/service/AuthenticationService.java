package com.luantang.socialmediaanalytics.service;

import com.luantang.socialmediaanalytics.dto.AuthResponseDto;
import com.luantang.socialmediaanalytics.dto.LoginDto;
import com.luantang.socialmediaanalytics.dto.MessageDto;
import com.luantang.socialmediaanalytics.model.UserEntity;
import org.springframework.transaction.annotation.Transactional;

public interface AuthenticationService {
    MessageDto register(UserEntity user);

    @Transactional
    MessageDto confirmToken(String token);

    AuthResponseDto login(LoginDto loginDto);
}
