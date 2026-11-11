package com.project.cinema.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OmdbMovieDetail(
        @JsonProperty("Title") String title,
        @JsonProperty("Plot") String plot,
        @JsonProperty("Genre") String genre,
        @JsonProperty("Response") String response,
        @JsonProperty("Error") String error
) {}