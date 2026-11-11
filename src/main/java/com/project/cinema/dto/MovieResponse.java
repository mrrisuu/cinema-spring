package com.project.cinema.dto;

public record MovieResponse(
        Integer id,
        String name,
        String description,
        String genreName
) {
}
