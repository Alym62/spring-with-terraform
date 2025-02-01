package com.github.alym62.springterraform.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record AuthRequestDTO(
        @Schema(description = "Email", example = "admin") @NotEmpty String email,
        @Schema(description = "Password", example = "123") @NotEmpty String password) {

}
