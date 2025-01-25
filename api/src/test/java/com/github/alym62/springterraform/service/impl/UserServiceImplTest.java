package com.github.alym62.springterraform.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.alym62.springterraform.domain.User;
import com.github.alym62.springterraform.exceptions.BusinessException;
import com.github.alym62.springterraform.payload.UserRequestDTO;
import com.github.alym62.springterraform.payload.UserUpdateRequestDTO;
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
        assertEquals(createdUser.getId(), userId.id());
    }

    @Test
    @DisplayName("Test [Find by email] - User service")
    void itShouldFindByEmailIfExists() {
        var dto = new UserRequestDTO("Aly", "aly@email.com", "123");

        when(this.repository.findByEmail(dto.email())).thenReturn(Optional.of(User.builder().build()));

        assertThrows(BusinessException.class, () -> this.serviceImpl.createUser(dto));
        verify(this.encoder, times(0)).encode(any());
        verify(this.repository, times(1)).findByEmail(dto.email());
        verify(this.repository, times(0)).save(any());
    }

    @Test
    @DisplayName("Test [Find by id] - User service")
    void itShouldFindById() {
        var uuid = UUID.randomUUID();
        var user = User.builder()
                .id(uuid)
                .name("Aly")
                .email("aly@email.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();

        when(this.repository.findById(uuid)).thenReturn(Optional.of(user));

        var userResponseDTO = this.serviceImpl.getUserById(uuid);

        assertEquals(userResponseDTO.id(), user.getId());
        assertEquals(userResponseDTO.name(), user.getName());
        assertEquals(userResponseDTO.email(), user.getEmail());
        assertEquals(userResponseDTO.createdAt(), user.getCreatedAt());
        assertEquals(userResponseDTO.updatedAt(), user.getUpdatedAt());

        verify(this.repository, times(1)).findById(uuid);
    }

    @Test
    @DisplayName("Test [Find by id not found] - User service")
    void itShouldFindByIdNotExists() {
        var uuid = UUID.randomUUID();

        when(this.repository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> this.serviceImpl.getUserById(uuid));

        verify(this.repository, times(1)).findById(uuid);
    }

    @Test
    @DisplayName("Test [Delete by id] - User service")
    void itShouldDeleteById() {
        var uuid = UUID.randomUUID();
        var user = User.builder()
                .id(uuid)
                .name("Aly")
                .email("aly@email.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();

        when(this.repository.findById(uuid)).thenReturn(Optional.of(user));
        doNothing().when(this.repository).deleteById(uuid);

        this.serviceImpl.deleteById(uuid);

        verify(this.repository, times(1)).deleteById(uuid);
    }

    @Test
    @DisplayName("Test [Update by id] - User service")
    void itShouldUpdateById() {
        var password = "newPassword";
        var dto = new UserUpdateRequestDTO("Aly meireles", password);
        var uuid = UUID.randomUUID();

        var user = User.builder()
                .id(uuid)
                .name("Aly")
                .email("aly@email.com")
                .password("123")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();

        when(this.repository.findById(uuid)).thenReturn(Optional.of(user));
        when(this.encoder.encode(password)).thenReturn("encodedUpdatePassword");
        when(this.repository.save(any(User.class))).thenAnswer(invoke -> {
            return invoke.getArgument(0, User.class);
        });

        this.serviceImpl.updateById(uuid, dto);

        verify(this.repository, times(1)).findById(uuid);
        verify(this.encoder, times(1)).encode(password);

        var captor = ArgumentCaptor.forClass(User.class);
        verify(this.repository, times(1)).save(captor.capture());

        var userUpdated = captor.getValue();
        assertEquals(userUpdated.getPassword(), "encodedUpdatePassword");
        assertEquals(userUpdated.getName(), dto.name());
    }

    @Test
    @DisplayName("Test [Pager] - User service")
    void itShouldPager() {
        var pageable = PageRequest.of(0, 10);
        var userResponse = User.builder()
                .id(UUID.randomUUID())
                .name("Aly")
                .email("aly@email.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        var page = new PageImpl<>(List.of(userResponse), pageable, 1);

        when(this.repository.findAll(pageable)).thenReturn(page);

        var response = this.serviceImpl.getPager(pageable);

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals(userResponse.getName(), response.getContent().get(0).name());

        verify(this.repository, times(1)).findAll(pageable);
    }
}
