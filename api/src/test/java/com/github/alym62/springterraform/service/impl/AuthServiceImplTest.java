package com.github.alym62.springterraform.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import com.github.alym62.springterraform.payload.AuthRequestDTO;
import com.github.alym62.springterraform.payload.AuthResponseDTO;
import com.github.alym62.springterraform.service.JwtService;

@TestInstance(Lifecycle.PER_CLASS)
public class AuthServiceImplTest {
    private AuthServiceImpl authServiceImpl;

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @Mock
    private Authentication authentication;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.authServiceImpl = new AuthServiceImpl(authenticationManager, jwtService);
    }

    @BeforeEach
    void resetMocks() {
        reset(this.authenticationManager);
        reset(this.jwtService);
    }

    @Test
    @DisplayName("Test [Login] - Auth service")
    void shouldReturnTokenJwt() {
        var dto = new AuthRequestDTO("email@email.com", "123");

        when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(authentication);

        when(jwtService.generateToken(Mockito.any()))
                .thenReturn("accessToken");

        var result = authServiceImpl.authenticate(dto);

        assertEquals(new AuthResponseDTO("accessToken"), result);

        verify(authenticationManager, times(1)).authenticate(Mockito.any());
        verify(jwtService, times(1)).generateToken(any());
    }
}
