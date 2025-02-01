package com.github.alym62.springterraform.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.alym62.springterraform.payload.AuthRequestDTO;
import com.github.alym62.springterraform.payload.AuthResponseDTO;
import com.github.alym62.springterraform.service.AuthService;

@TestInstance(Lifecycle.PER_CLASS)
public class AuthControllerTest {
    private AuthController authController;

    @Mock
    private AuthService authService;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.authController = new AuthController(authService);
    }

    @BeforeEach
    void setUpEach() {
        reset(authService);
    }

    @Test
    @DisplayName("Test [Login] - Auth controller")
    void shouldReturnAccessToken() {
        var request = new AuthRequestDTO("email@email.com", "123456");
        when(this.authService.authenticate(request)).thenReturn(new AuthResponseDTO("accessToken"));

        var response = this.authController.login(request);
        var body = response.getBody();

        assertNotNull(body);
        assertEquals(body.accessToken(), "accessToken");

        verify(this.authService, times(1)).authenticate(request);
    }
}
