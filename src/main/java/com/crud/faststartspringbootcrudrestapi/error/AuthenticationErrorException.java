package com.crud.faststartspringbootcrudrestapi.error;

public class AuthenticationErrorException extends Exception{
    public AuthenticationErrorException(String message) {
        super(message);
    }
}
