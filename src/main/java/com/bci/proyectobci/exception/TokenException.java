package com.bci.proyectobci.exception;

public class TokenException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final String message;

    public TokenException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
