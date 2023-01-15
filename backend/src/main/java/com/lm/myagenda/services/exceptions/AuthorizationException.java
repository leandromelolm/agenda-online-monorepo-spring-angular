package com.lm.myagenda.services.exceptions;

public class AuthorizationException extends RuntimeException{

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}