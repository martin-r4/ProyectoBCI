package com.bci.proyectobci.service;
import com.bci.proyectobci.controller.dto.LoginDto;
import com.bci.proyectobci.controller.dto.UserDto;
import com.bci.proyectobci.controller.dto.UserLoginResponseDto;
import com.bci.proyectobci.controller.dto.UserResponseDto;
public interface IAuthenticationService {
    public UserResponseDto registerUser(UserDto request);
    public UserLoginResponseDto loginUser(LoginDto loginDto);
}
