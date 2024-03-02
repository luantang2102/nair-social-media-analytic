package com.luantang.socialmediaanalytics.controller;

import com.luantang.socialmediaanalytics.dto.AuthResponseDto;
import com.luantang.socialmediaanalytics.dto.LoginDto;
import com.luantang.socialmediaanalytics.dto.MessageDto;
import com.luantang.socialmediaanalytics.dto.RegisterDto;
import com.luantang.socialmediaanalytics.dto.converter.UserConverter;
import com.luantang.socialmediaanalytics.model.UserEntity;
import com.luantang.socialmediaanalytics.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserConverter userConverter;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserConverter userConverter) {
        this.authenticationService = authenticationService;
        this.userConverter = userConverter;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageDto> register(@RequestBody RegisterDto registerDto) {
        UserEntity userRequest = userConverter.registerDtoToNewUser(registerDto);
        return new ResponseEntity<>(authenticationService.register(userRequest), HttpStatus.CREATED);
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<MessageDto> confirmEmail(@RequestParam("token") String token) {
        return new ResponseEntity<>(authenticationService.confirmToken(token), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(authenticationService.login(loginDto), HttpStatus.OK);
    }


}
