package com.bci.proyectobci.service.impl;

import com.bci.proyectobci.controller.dto.LoginDto;
import com.bci.proyectobci.controller.dto.PhoneDto;
import com.bci.proyectobci.controller.dto.UserDto;
import com.bci.proyectobci.controller.dto.UserLoginResponseDto;
import com.bci.proyectobci.entity.PhoneEntity;
import com.bci.proyectobci.entity.RoleEntity;
import com.bci.proyectobci.entity.UserEntity;
import com.bci.proyectobci.exception.ErrorUserExist;
import com.bci.proyectobci.repository.RoleRepository;
import com.bci.proyectobci.repository.UserRepository;
import com.bci.proyectobci.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private AuthenticationServiceImpl authenticationServiceImpl;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;


    UserEntity userEntity;
    UserDto userDto;
    LoginDto loginDto;
    @BeforeEach
    void setup(){
        RoleEntity roles = new RoleEntity();
        roles.setName("ROLE_ADMIN");
        roles.setId(1L);
        PhoneEntity phones = new PhoneEntity();
        phones.setCityCode(5800);
        phones.setCountryCode("BCI");
        phones.setId(1L);
        phones.setNumber(4625823);
        userEntity = UserEntity.builder()
                .id("1L")
                .username("martin")
                .email("rio@gmail.com")
                .password("bci")
                .phones(Collections.singleton(phones))
                .roles(Collections.singleton(roles))
                .lastLogin(new Date())
                .created(new Date())
                .isActive(true)
                .build();

        userDto = UserDto.builder().email("rio@gmail.com").password("bci").username("martin")
                .phones(userEntity.getPhones().stream().map(PhoneDto::phoneDtoToEntity).collect(Collectors.toSet())).build();

        loginDto = LoginDto.builder().username("global").password("globaLogic").build();
    }

    @Test
    void registerUserTest(){
        //given
        given(userRepository.existsByUsername(userDto.getUsername())).willReturn(false);
        given(roleRepository.findByName(anyString())).willReturn(Optional.of(new RoleEntity()));
        given(passwordEncoder.encode(anyString())).willReturn("");
        given(tokenProvider.generateTokens(anyString())).willReturn("");
        given(userRepository.save(any(UserEntity.class))).willReturn(userEntity);
        //when
        UserDto userGuardado = authenticationServiceImpl.registerUser(userDto);
        //then
        assertThat(userGuardado).isNotNull();
    }

    @Test
    void registerUserExistTest(){
        //given
        given(userRepository.existsByUsername(userDto.getUsername())).willReturn(true);
        //when
        //then
        assertThrows(ErrorUserExist.class, () -> authenticationServiceImpl.registerUser(userDto));
    }

    @Test
    void loginUserExceptionTest(){
        //given
        given(userRepository.findByUsername(loginDto.getUsername())).willReturn(Optional.empty());
        //then
        assertThrows(ErrorUserExist.class, () -> authenticationServiceImpl.loginUser(loginDto));
    }

    @Test
    void loginUserTest(){
        //given
        given(userRepository.findByUsername(loginDto.getUsername())).willReturn(Optional.of(userEntity));
        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).willReturn(authentication);
        //then
        UserLoginResponseDto response = authenticationServiceImpl.loginUser(loginDto);

        assertNotNull(response);
        assertEquals(userEntity.getId(), response.getId());
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

}
