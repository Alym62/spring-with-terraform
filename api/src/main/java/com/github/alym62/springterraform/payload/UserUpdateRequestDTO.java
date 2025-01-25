package com.github.alym62.springterraform.payload;

import jakarta.validation.constraints.NotEmpty;

public record UserUpdateRequestDTO(
        @NotEmpty String name,
        @NotEmpty String password) {

}
