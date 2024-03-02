package com.luantang.socialmediaanalytics.exception;

import java.io.Serial;

public class EmailConfirmationTokenNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 2;

    public EmailConfirmationTokenNotFoundException(String message) {
        super(message);
    }
}
