package com.bci.proyectobci.controller.dto;

import com.bci.proyectobci.entity.PhoneEntity;
import com.bci.proyectobci.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;

    @Email(message = "Formato de email incorrecto")
    @NotEmpty(message = "El email no debe estar vacio")
    @Column(unique = true)
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Z]{1})(?=.*\\d.*\\d)(?=.*[a-z])(?!.*[A-Z]{2,})(?!.*\\d{3,})[a-zA-Z0-9]{8,12}$", message = "La clave no cumple con los requisitos.")
    @Size(min = 8, max = 12, message = "La contraseña debe tener entre 8 y 12 caracteres")
    private String password;

    private Set<PhoneDto> phones;

}
