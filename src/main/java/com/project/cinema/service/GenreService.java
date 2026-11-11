package com.project.cinema.service;

import com.project.cinema.dto.GenreRequest;
import com.project.cinema.dto.GenreResponse;
import com.project.cinema.dto.MovieResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenreService {

    GenreResponse createGenre(GenreRequest request);

    GenreResponse updateGenre(Integer id, GenreRequest request);

    void deleteGenre(Integer id);

    GenreResponse showDetailGenre(Integer id);

    Page<GenreResponse> showAllGenre(Pageable pageable, String name);

    Page<MovieResponse> showAllMovieByGenre(Pageable pageable, Integer id);


}
