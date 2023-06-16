package com.bci.proyectobci.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class Errors {
    private Set<ErrorDetails> errors;
}
