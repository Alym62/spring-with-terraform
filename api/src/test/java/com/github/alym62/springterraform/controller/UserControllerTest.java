package com.github.alym62.springterraform.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.alym62.springterraform.payload.UserRequestDTO;
import com.github.alym62.springterraform.service.UserService;

@TestInstance(Lifecycle.PER_CLASS)
class UserControllerTest {
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.userController = new UserController(userService);
    }

    @BeforeEach
    void setUpEach() {
        reset(userService);
    }

    @Test
    @DisplayName("Test [Create] - User controller")
    void itShouldCallServiceAndReturnCreateUserID() {
        var request = new UserRequestDTO("Eu", "eu@email.com", "123");

        when(userService.createUser(request)).thenReturn(UUID.randomUUID());

        var response = userController.create(request);
        var body = response.getBody();

        assertNotNull(body);

        var captor = ArgumentCaptor.forClass(UserRequestDTO.class);

        verify(userService, times(1)).createUser(captor.capture());

        var dto = captor.getValue();
        assertEquals(dto.email(), request.email());
        assertEquals(dto.name(), request.name());
        assertEquals(dto.password(), request.password());
    }

    @Test
    @DisplayName("Test [Health] - User controller")
    void itShouldCallHealth() {
        var response = userController.health();
        var body = response.getBody();

        assertNotNull(body);
        assertEquals(body, "Success");
    }
}
