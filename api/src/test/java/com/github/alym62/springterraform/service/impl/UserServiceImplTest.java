package com.github.alym62.springterraform.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.alym62.springterraform.domain.User;
import com.github.alym62.springterraform.payload.UserRequestDTO;
import com.github.alym62.springterraform.repository.UserRepository;

@TestInstance(Lifecycle.PER_CLASS)
public class UserServiceImplTest {
    private UserServiceImpl serviceImpl;

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.serviceImpl = new UserServiceImpl(repository, encoder);
    }

    @BeforeEach
    void setUpEach() {
        reset(this.encoder, this.repository);
    }

    @Test
    @DisplayName("Test [Create] - User service")
    void itShouldCreateTheUserIfEmailIsAvailable() {
        var passwordEncoded = "encodedPassword";
        var dto = new UserRequestDTO("Aly", "aly@email.com", "123");

        when(this.encoder.encode(dto.password())).thenReturn(passwordEncoded);
        when(this.repository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(this.repository.save(any(User.class))).thenAnswer(invoke -> {
            var user = invoke.getArgument(0, User.class);
            user.setId(UUID.randomUUID());
            return user;
        });

        var userId = this.serviceImpl.createUser(dto);

        assertNotNull(userId);
        verify(this.encoder, times(1)).encode(dto.password());
        verify(this.repository, times(1)).findByEmail(dto.email());

        var captor = ArgumentCaptor.forClass(User.class);

        verify(this.repository, times(1)).save(captor.capture());

        var createdUser = captor.getValue();

        assertEquals(createdUser.getPassword(), passwordEncoded);
        assertEquals(createdUser.getId(), userId);
    }

    @Test
    @DisplayName("Test [Find by email] - User service")
    void itShouldFindByEmailIfExists() {

    }
}
