package com.project.cinema.dto;

import java.util.List;

public record MovieSearchResponse(
        List<MovieSearchItem> results,
        int page,
        int totalResults,
        int totalPages
) {
    public record MovieSearchItem(
            String title,
            String year,
            String imdbId,
            String type,
            String poster
    ) {}
}