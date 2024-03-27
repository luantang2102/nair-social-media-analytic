package com.luantang.socialmediaanalytics.authentication.controller;

import com.luantang.socialmediaanalytics.authentication.dto.LoginDto;
import com.luantang.socialmediaanalytics.authentication.dto.AuthMessageDto;
import com.luantang.socialmediaanalytics.authentication.dto.RegisterDto;
import com.luantang.socialmediaanalytics.authentication.dto.converter.UserConverter;
import com.luantang.socialmediaanalytics.authentication.model.UserEntity;
import com.luantang.socialmediaanalytics.authentication.service.AuthenticationService;
import com.luantang.socialmediaanalytics.authentication.dto.AuthResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
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
    public ResponseEntity<AuthMessageDto> register(@RequestBody RegisterDto registerDto) {
        UserEntity userRequest = userConverter.registerDtoToNewUser(registerDto);
        return new ResponseEntity<>(authenticationService.register(userRequest), HttpStatus.CREATED);
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<AuthMessageDto> confirmEmail(@RequestParam("token") String token) {
        return new ResponseEntity<>(authenticationService.confirmToken(token), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(authenticationService.login(loginDto), HttpStatus.OK);
    }


}
