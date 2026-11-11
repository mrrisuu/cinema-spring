package com.project.cinema.dto;

public record MovieRequest(
        String name,
        String description,
        Integer genreId
) {
}
