package com.project.cinema.service;

import com.project.cinema.dto.MovieRequest;
import com.project.cinema.dto.MovieResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {

    MovieResponse createMovie(MovieRequest request);

    MovieResponse updateMovie(Integer id, MovieRequest request);

    Page<MovieResponse> showAllMovies(Pageable pageable, String name);

    void deleteMovie(Integer id);

    MovieResponse showMovie(Integer id);

}
