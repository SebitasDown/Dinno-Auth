package com.dinno.Auth.infrastructure.exception;

import com.dinno.Auth.application.exception.AccountDeleteException;
import com.dinno.Auth.application.exception.EmailAlreadyExistException;
import com.dinno.Auth.application.exception.InvalidCredentialsException;
import com.dinno.Auth.application.exception.UserInactiveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.net.URI;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ProblemDetail handleEmailAlreadyExistException(EmailAlreadyExistException e) {
        log.warn("Handling EmailAlreadyExistException: {}", e.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setTitle("Email Already Exists");
        problemDetail.setType(URI.create("https://dinno.com/errors/email-exists"));
        return problemDetail;
    }

    @ExceptionHandler(AccountDeleteException.class)
    public ProblemDetail handleAccountDeleteException(AccountDeleteException e) {
        log.warn("Handling AccountDeleteException: {}", e.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        problemDetail.setTitle("Account Deleted");
        problemDetail.setType(URI.create("https://dinno.com/errors/account-deleted"));
        return problemDetail;
    }

    @ExceptionHandler(UserInactiveException.class)
    public ProblemDetail handleUserInactiveException(UserInactiveException e) {
        log.warn("Handling UserInactiveException: {}", e.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        problemDetail.setTitle("User Inactive");
        problemDetail.setType(URI.create("https://dinno.com/errors/user-inactive"));
        return problemDetail;
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ProblemDetail handleInvalidCredentialsException(InvalidCredentialsException e) {
        log.warn("Handling InvalidCredentialsException: {}", e.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setTitle("Invalid Credentials");
        problemDetail.setType(URI.create("https://dinno.com/errors/invalid-credentials"));
        return problemDetail;
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ProblemDetail handleValidationException(WebExchangeBindException e) {
        log.warn("Validation failed: {} errors", e.getErrorCount());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
        problemDetail.setTitle("Bad Request");
        problemDetail.setProperty("errors", e.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception e) {
        log.error("Unhandled exception caught: ", e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred");
        problemDetail.setTitle("Internal Server Error");
        return problemDetail;
    }
}
