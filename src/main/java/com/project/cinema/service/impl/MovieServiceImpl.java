package com.project.cinema.service.impl;

import com.project.cinema.common.exception.ResourceNotFoundException;
import com.project.cinema.dto.MovieRequest;
import com.project.cinema.dto.MovieResponse;
import com.project.cinema.entity.Genre;
import com.project.cinema.entity.Movie;
import com.project.cinema.mapper.MovieMapper;
import com.project.cinema.repository.GenreRepository;
import com.project.cinema.repository.MovieRepository;
import com.project.cinema.service.MovieService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    // Dependencies dan business logic
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public MovieResponse createMovie(MovieRequest request) {
        //pengecekan genre
        Genre genre = findGenre(request.genreId());

        Movie movie = movieMapper.toEntity(request);

        movie.setGenre(genre);

        Movie savedMovie = movieRepository.save(movie);

        return movieMapper.toResponse(savedMovie);
    }

    @Override
    public MovieResponse updateMovie(Integer id, MovieRequest request) {

        Movie movie = findMovie(id);

        Genre genre = findGenre(request.genreId());

        movieMapper.updateEntity(request, movie);

        movie.setGenre(genre);

        Movie updatedMovie = movieRepository.save(movie);

        return movieMapper.toResponse(updatedMovie);
    }

    @Override
    public Page<MovieResponse> showAllMovies(Pageable pageable, String name) {

        Page<Movie> movies;

        if (name == null || name.isBlank()){
            movies = movieRepository.findAll(pageable);
        }else {
            movies = movieRepository.findByNameContainingIgnoreCase(
                pageable, name);
        }

        return movies.map(movieMapper::toResponse);
    }

    @Override
    public void deleteMovie(Integer id) {
        Movie movie = findMovie(id);

        movieRepository.delete(movie);
    }

    @Override
    public MovieResponse showMovie(Integer id) {
        Movie movie = findMovie(id);
        return movieMapper.toResponse(movie);
    }

    /*
    HELPER
     */

    private Movie findMovie(Integer id){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Movie tidak ditemukan"));
        return movie;
    }

    private Genre findGenre(Integer id){
        Genre genre = genreRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Genre tidak ditemukan"));
        return genre;
    }
}
