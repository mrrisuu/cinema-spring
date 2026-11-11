package com.project.cinema.service.impl;

import com.project.cinema.common.exception.ResourceNotFoundException;
import com.project.cinema.dto.GenreRequest;
import com.project.cinema.dto.GenreResponse;
import com.project.cinema.dto.MovieResponse;
import com.project.cinema.entity.Genre;
import com.project.cinema.entity.Movie;
import com.project.cinema.mapper.GenreMapper;
import com.project.cinema.mapper.MovieMapper;
import com.project.cinema.repository.GenreRepository;
import com.project.cinema.repository.MovieRepository;
import com.project.cinema.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;


    @Override
    public Page<GenreResponse> showAllGenre(Pageable pageable, String name) {
        Page<Genre> genres;

        if(name == null || name.isBlank()){
            genres = genreRepository.findAll(pageable);
        }else {
            genres = genreRepository.findByName(pageable, name);
        }

        return genres.map(genreMapper::toResponse);
    }

    @Override
    public GenreResponse showDetailGenre(Integer id) {
        Genre genre = findGenre(id);

        return genreMapper.toResponse(genre);
    }

    @Override
    public GenreResponse createGenre(GenreRequest request) {
        Genre genre = genreMapper.toEntity(request);

        Genre genreSaved = genreRepository.save(genre);

        return genreMapper.toResponse(genreSaved);
    }

    @Override
    public GenreResponse updateGenre(Integer id, GenreRequest request) {
        Genre genre = findGenre(id);

        genreMapper.updateEntity(request, genre);

        Genre genreSaved = genreRepository.save(genre);

        return genreMapper.toResponse(genreSaved);
    }

    @Override
    public void deleteGenre(Integer id) {
        Genre genre = findGenre(id);

        genreRepository.delete(genre);
    }

    @Override
    public Page<MovieResponse> showAllMovieByGenre(Pageable pageable, Integer id) {
        Genre genre = findGenre(id);

        Page<Movie> movies = movieRepository.findByGenreId(pageable, genre.getId());

        return movies.map(movieMapper::toResponse);
    }

    /*
        HELPER
     */

    private Genre findGenre(Integer id){
        Genre genre = genreRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Genre tidak ditemukan"));
        return genre;
    }
}
