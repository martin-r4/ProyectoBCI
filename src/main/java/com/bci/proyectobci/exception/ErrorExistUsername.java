package com.bci.proyectobci.exception;

import lombok.Getter;

import java.util.Date;
@Getter
public class ErrorExistUsername extends RuntimeException{
    private static final long serialVersionUID = -3534520956070708260L;
    private final Date timestamp;
    private final int code;
    private final String detail;
    public ErrorExistUsername(Date timestamp, int code, String detail) {
        this.timestamp = timestamp;
        this.code = code;
        this.detail = detail;
    }
}
