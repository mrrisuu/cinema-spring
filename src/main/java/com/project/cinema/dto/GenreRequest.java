package com.project.cinema.dto;

import jakarta.validation.constraints.NotBlank;

public record GenreRequest(
    @NotBlank
    String name,
    @NotBlank
    String description
) {
}
