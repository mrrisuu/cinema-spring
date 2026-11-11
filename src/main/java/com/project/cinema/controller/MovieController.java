package com.project.cinema.controller;

import com.project.cinema.dto.MovieRequest;
import com.project.cinema.dto.MovieResponse;
import com.project.cinema.dto.MovieSearchResponse;
import com.project.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponse> addMovie(
            @RequestBody MovieRequest request
            ){
        MovieResponse movieResponse = movieService.createMovie(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(
            @PathVariable @RequestParam Integer id,
            @RequestBody MovieRequest request
    ){
        MovieResponse movieResponse = movieService.updateMovie(id, request);
        return ResponseEntity.ok(movieResponse);
    }

    @GetMapping
    public  ResponseEntity<Page<MovieResponse>> showMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name
            ){
        Pageable pageable = PageRequest.of(page, size);

        Page<MovieResponse> response = movieService.showAllMovies(pageable, name);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(
            @PathVariable @RequestParam Integer id
    ){
        movieService.deleteMovie(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> showMovie(
            @PathVariable @RequestParam Integer id
    ){
        MovieResponse response = movieService.showMovie(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<MovieSearchResponse> searchOmdb(
            @RequestParam String title,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String type
    ){
        return ResponseEntity.ok(movieService.searchOmdb(title, page, year, type));
    }

    @PostMapping("/import")
    public ResponseEntity<MovieResponse> importMovie(
            @RequestParam String imdbId
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movieService.importFromOmdb(imdbId));
    }
}
