package com.github.alym62.springterraform.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.alym62.springterraform.payload.UserRequestDTO;
import com.github.alym62.springterraform.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UUID createUser(UserRequestDTO dto) {
        return UUID.randomUUID();
    }

}
