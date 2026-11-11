package com.project.cinema.mapper;

import com.project.cinema.dto.MovieRequest;
import com.project.cinema.dto.MovieResponse;
import com.project.cinema.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(source = "genre.name", target = "genreName")
    MovieResponse toResponse(Movie movie);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genre", ignore = true)
    Movie toEntity(MovieRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genre", ignore = true)
    void updateEntity(MovieRequest request, @MappingTarget Movie movie);
}