package com.bci.proyectobci.service.impl;

import com.bci.proyectobci.entity.PhoneEntity;
import com.bci.proyectobci.controller.dto.UserDto;
import com.bci.proyectobci.controller.dto.UserResponseDto;
import com.bci.proyectobci.controller.dto.PhoneDto;
import com.bci.proyectobci.controller.dto.UserLoginResponseDto;
import com.bci.proyectobci.controller.dto.LoginDto;
import com.bci.proyectobci.entity.RoleEntity;
import com.bci.proyectobci.entity.UserEntity;
import com.bci.proyectobci.exception.ErrorUserExist;
import com.bci.proyectobci.exception.ErrorExistUsername;
import com.bci.proyectobci.repository.RoleRepository;
import com.bci.proyectobci.repository.UserRepository;
import com.bci.proyectobci.security.JwtTokenProvider;
import com.bci.proyectobci.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponseDto registerUser(UserDto userDto) {
        if(userRepository.existsByUsername(userDto.getUsername())){
            throw new ErrorUserExist(new Date(), 400,"user exist");
        }
        RoleEntity roles = roleRepository.findByName("ROLE_ADMIN").orElse(null);

        UserEntity user = UserEntity.builder().username(userDto.getUsername()).email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword())).created(new Date()).lastLogin(new Date())
                .isActive(true)
                .phones(userDto.getPhones().stream().map(PhoneEntity::fromDtoToEntity).collect(Collectors.toSet()))
                .token(jwtTokenProvider.generateTokens(userDto.getUsername())).roles(Collections.singleton(roles)).build();
        userRepository.save(user);
        return UserResponseDto.builder().id(user.getId()).token(user.getToken()).username(user.getUsername())
                .email(user.getEmail()).password(user.getPassword())
                .phones(user.getPhones().stream().map(PhoneDto::phoneDtoToEntity).collect(Collectors.toSet()))
                .created(user.getCreated()).lastLogin(user.getLastLogin()).token(user.getToken())
                .isActive(user.getIsActive()).build();
    }

    @Override
    public UserLoginResponseDto loginUser(LoginDto loginDto) {

        if(userRepository.findByUsername(loginDto.getUsername()).isEmpty()){
            throw new ErrorUserExist(new Date(), 400,"UserName is empty");
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new ErrorExistUsername(new Date(), 500,
                        loginDto.getUsername() + " user doesnt exist, please register first"));

        UserLoginResponseDto userResponse = UserLoginResponseDto.builder().id(user.getId()).created(user.getCreated()).lastLogin(user.getLastLogin())
                .token(user.getToken()).isActive(user.getIsActive()).username(user.getUsername()).email(user.getEmail())
                .password(user.getPassword())
                .phones(user.getPhones().stream().map(PhoneDto::phoneDtoToEntity).collect(Collectors.toSet())).build();

                userResponse.setToken(user.getToken());
                return userResponse;

    }
}
