package com.luantang.socialmediaanalytics.exception.handler;

import com.luantang.socialmediaanalytics.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorObject> handleUserNotFound(UserNotFoundException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.NOT_FOUND);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorObject> handleEmailAlreadyExist(EmailAlreadyExistException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.BAD_REQUEST);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorObject> handleBadCredentials(BadCredentialsException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.BAD_REQUEST);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorObject> handleUserDisabled(DisabledException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.BAD_REQUEST);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailConfirmationTokenNotFoundException.class)
    public ResponseEntity<ErrorObject> handleEmailConfirmationTokenNotFound(EmailConfirmationTokenNotFoundException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.NOT_FOUND);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorObject> handleTokenExpired(TokenExpiredException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.BAD_REQUEST);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FailedToSendEmailException.class)
    public ResponseEntity<ErrorObject> handleFailedToSendEmail(FailedToSendEmailException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.BAD_REQUEST);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }
}
