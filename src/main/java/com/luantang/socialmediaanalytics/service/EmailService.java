package com.luantang.socialmediaanalytics.service;

import com.luantang.socialmediaanalytics.model.EmailConfirmationToken;

public interface EmailService {
    boolean isValidEmail(String email);

    EmailConfirmationToken saveConfirmationToken(EmailConfirmationToken token);

    EmailConfirmationToken getConfirmationToken(String token);

    void sendEmail(String to, String email);
}
