package com.project.cinema.mapper;

import com.project.cinema.dto.GenreRequest;
import com.project.cinema.dto.GenreResponse;
import com.project.cinema.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreResponse toResponse (Genre genre);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movies", ignore = true)
    Genre toEntity(GenreRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movies", ignore = true)
    void updateEntity(GenreRequest request, @MappingTarget Genre genre);
}
