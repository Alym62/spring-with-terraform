package com.github.alym62.springterraform.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record AuthResponseDTO(
        @Schema(description = "Access token") String accessToken) {

}
