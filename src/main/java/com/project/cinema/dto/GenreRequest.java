package com.project.cinema.dto;

import com.project.cinema.validation.NoNumber;
import jakarta.validation.constraints.NotBlank;

public record GenreRequest(
    @NotBlank(message = "Name cannot blank")
    @NoNumber(message = "Input Name only using alphabet")
    String name,
    @NotBlank(message = "Description cannot blank")
    String description
) {
}
