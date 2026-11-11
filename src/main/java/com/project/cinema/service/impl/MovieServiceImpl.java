package com.project.cinema.service.impl;

import com.project.cinema.client.OmdbClient;
import com.project.cinema.common.exception.ExternalServiceException;
import com.project.cinema.common.exception.ResourceConflictException;
import com.project.cinema.common.exception.ResourceNotFoundException;
import com.project.cinema.dto.*;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    // Dependencies dan business logic
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final GenreRepository genreRepository;
    private final OmdbClient omdbClient;

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

    @Override
    @Transactional
    public MovieResponse importFromOmdb(String imdbId) {
        if (imdbId == null || !imdbId.matches("^tt\\d{7,10}$")) {
            throw new IllegalArgumentException("Format imdbId tidak valid, contoh: tt1375666");
        }

        OmdbMovieDetail omdb = omdbClient.findByImdbId(imdbId);

        if (omdb == null || !"True".equalsIgnoreCase(omdb.response())) {
            throw new ResourceNotFoundException("Film tidak ditemukan di OMDb");
        }
        if (movieRepository.existsByNameIgnoreCase(omdb.title())) {
            throw new ResourceConflictException("Film sudah ada: " + omdb.title());
        }

        Movie movie = new Movie();
        movie.setName(omdb.title());
        movie.setDescription("N/A".equals(omdb.plot()) ? null : omdb.plot());
        movie.setGenre(resolveGenre(omdb.genre()));

        return movieMapper.toResponse(movieRepository.save(movie));
    }

    @Override
    public MovieSearchResponse searchOmdb(String title, int page, String year, String type) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Judul pencarian tidak boleh kosong");
        }
        if (page < 1 || page > 100) {
            throw new IllegalArgumentException("Page harus antara 1 dan 100");
        }

        OmdbSearchResponse omdb = omdbClient.search(title.trim(), page, year, type);

        if (omdb == null) {
            throw new ExternalServiceException("Respons OMDb kosong");
        }

        if (!"True".equalsIgnoreCase(omdb.response())) {
            // OMDb membalas HTTP 200 + Response=False saat tidak ada hasil
            if ("Movie not found!".equalsIgnoreCase(omdb.error())) {
                return new MovieSearchResponse(List.of(), page, 0, 0);
            }
            if ("Too many results.".equalsIgnoreCase(omdb.error())) {
                throw new IllegalArgumentException(
                        "Kata kunci terlalu umum, gunakan judul yang lebih spesifik");
            }
            throw new ExternalServiceException("OMDb menolak request: " + omdb.error());
        }

        List<MovieSearchResponse.MovieSearchItem> items = omdb.search().stream()
                .map(i -> new MovieSearchResponse.MovieSearchItem(
                        i.title(), i.year(), i.imdbId(), i.type(), i.poster()))
                .toList();

        int total = parseIntOrZero(omdb.totalResults());
        int totalPages = (int) Math.ceil(total / 10.0);

        return new MovieSearchResponse(items, page, total, totalPages);
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

    // OMDb mengirim "Action, Adventure, Sci-Fi", sedangkan Movie hanya punya 1 genre.
// Dipakai genre pertama; dibuat otomatis jika belum ada.
    private Genre resolveGenre(String omdbGenre) {
        if (omdbGenre == null || omdbGenre.isBlank() || "N/A".equals(omdbGenre)) {
            return null;
        }
        String genreName = omdbGenre.split(",")[0].trim();
        return genreRepository.findByNameIgnoreCase(genreName)
                .orElseGet(() -> {
                    Genre genre = new Genre();
                    genre.setName(genreName);
                    return genreRepository.save(genre);
                });
    }

    private int parseIntOrZero(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
