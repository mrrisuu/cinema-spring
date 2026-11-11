package com.project.cinema.controller;

import com.project.cinema.dto.GenreRequest;
import com.project.cinema.dto.GenreResponse;
import com.project.cinema.dto.MovieResponse;
import com.project.cinema.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<Page<GenreResponse>> showAllGenre(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<GenreResponse> responses = genreService.showAllGenre(pageable,name);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponse> showDetailGenre(
            @PathVariable @RequestParam Integer id){
        GenreResponse response = genreService.showDetailGenre(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<GenreResponse> createGenre(
            @Valid @RequestBody GenreRequest request){
        GenreResponse response = genreService.createGenre(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GenreResponse> updateGenre(
            @PathVariable @RequestParam Integer id,
            @Valid @RequestBody GenreRequest request
    ){
        GenreResponse response = genreService.updateGenre(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(
            @PathVariable @RequestParam Integer id
    ){
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/movies")
    public ResponseEntity<Page<MovieResponse>> showMoviesByGenre(
            @PathVariable @RequestParam Integer id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);

        Page<MovieResponse> responses = genreService.showAllMovieByGenre(pageable, id);

        return ResponseEntity.ok(responses);
    }
}
