package com.project.cinema.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OmdbSearchItem(
        @JsonProperty("Title") String title,
        @JsonProperty("Year") String year,
        @JsonProperty("imdbID") String imdbId,
        @JsonProperty("Type") String type,
        @JsonProperty("Poster") String poster
) {}