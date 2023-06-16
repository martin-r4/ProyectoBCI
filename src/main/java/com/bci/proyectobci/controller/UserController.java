package com.bci.proyectobci.controller;

import com.bci.proyectobci.controller.dto.LoginDto;
import com.bci.proyectobci.controller.dto.UserDto;
import com.bci.proyectobci.controller.dto.UserResponseDto;
import com.bci.proyectobci.service.IAuthenticationService;
import com.bci.proyectobci.controller.dto.UserLoginResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/proyectobci/v1")
public class UserController {
    private IAuthenticationService authService;
    public UserController(IAuthenticationService authService) {
        this.authService = authService;
    }
    @PostMapping("/sign_up")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserDto request) {
        return new ResponseEntity<>(authService.registerUser(request), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> loginUser(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(authService.loginUser(loginDto), HttpStatus.OK);
    }
}
