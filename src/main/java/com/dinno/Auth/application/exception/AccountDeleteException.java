package com.dinno.Auth.application.exception;

public class AccountDeleteException extends RuntimeException {

    private final String errorCode;

    public AccountDeleteException(String message) {
        super(message);
        this.errorCode = "ACCOUNT_DELETED_OR_INACTIVE";
    }

    public AccountDeleteException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}