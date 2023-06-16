package com.bci.proyectobci.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
@Getter
@AllArgsConstructor
public class ErrorUserExist extends RuntimeException{
    private static final long serialVersionUID = -3066446381099045270L;
    private final Date timestamp;
    private final int code;
    private final String detail;
}
