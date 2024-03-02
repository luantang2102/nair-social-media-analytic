package com.luantang.socialmediaanalytics.service.impl;

import com.luantang.socialmediaanalytics.dto.AuthResponseDto;
import com.luantang.socialmediaanalytics.dto.LoginDto;
import com.luantang.socialmediaanalytics.dto.MessageDto;
import com.luantang.socialmediaanalytics.exception.EmailAlreadyExistException;
import com.luantang.socialmediaanalytics.exception.TokenExpiredException;
import com.luantang.socialmediaanalytics.model.EmailConfirmationToken;
import com.luantang.socialmediaanalytics.model.UserEntity;
import com.luantang.socialmediaanalytics.service.AuthenticationService;
import com.luantang.socialmediaanalytics.service.EmailService;
import com.luantang.socialmediaanalytics.service.JwtService;
import com.luantang.socialmediaanalytics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UserService userService, EmailService emailService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public MessageDto register(UserEntity userRequest) {
        if(emailService.isValidEmail(userRequest.getEmail())) {
            UserEntity savedUser = userService.createUser(userRequest);

            //Send confirmation token
            EmailConfirmationToken confirmationToken = new EmailConfirmationToken(
                    UUID.randomUUID().toString(),
                    new Date(System.currentTimeMillis()),
                    new Date(System.currentTimeMillis() + 1000 * 60 * 15),
                    null,
                    savedUser.getEmail());
            emailService.saveConfirmationToken(confirmationToken);

            //Send email
            String link = "http://localhost:8080/api/v1/auth/register/confirm?token=" + confirmationToken.getToken();

            emailService.sendEmail(userRequest.getEmail(), link);

            return MessageDto.builder()
                    .message("User created. Confirmation email sent")
                    .timestamp(new Date(System.currentTimeMillis()))
                    .build();
        }
        else {
            throw new EmailAlreadyExistException("Invalid Email");
        }
    }

    @Transactional
    @Override
    public MessageDto confirmToken(String token) {
        EmailConfirmationToken confirmationToken = emailService.getConfirmationToken(token);
        if(confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyExistException("Email already confirmed");
        }
        if(confirmationToken.getExpiredAt().before(new Date(System.currentTimeMillis()))) {
            throw new TokenExpiredException("Token expired");
        }
        //Update confirmed date
        confirmationToken.setConfirmedAt(new Date(System.currentTimeMillis()));
        emailService.saveConfirmationToken(confirmationToken);
        userService.enableUserByEmail(confirmationToken.getEmail());
        return MessageDto.builder()
                .message("Email confirmed")
                .timestamp(new Date(System.currentTimeMillis()))
                .build();
    }

    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        try {
            authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));//Check if email and password corrected
            var user = userService.getUserByEmail(loginDto.getEmail());
            var jwtToken = jwtService.generateToken(user);
            return AuthResponseDto.builder()
                    .accessToken(jwtToken)
                    .tokenType("Bearer ")
                    .build();
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Bad Credential");
        }
        catch (DisabledException e) {
            throw new DisabledException("User is disabled");
        }
    }

}
