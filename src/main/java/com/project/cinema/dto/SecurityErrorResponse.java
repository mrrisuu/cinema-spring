package com.project.cinema.dto;

public record SecurityErrorResponse(
        int status,
        String message,
        String path
) {}
