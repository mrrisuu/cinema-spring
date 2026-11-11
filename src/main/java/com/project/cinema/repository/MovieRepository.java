package com.project.cinema.repository;

import com.project.cinema.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Page<Movie> findAll(Pageable pageable);

    Page<Movie> findByNameContainingIgnoreCase(
            Pageable pageable, String name);

    @Query("""
    SELECT m FROM Movie m
    WHERE m.genre.id = :id
    """)
    Page<Movie> findByGenreId(Pageable pageable, Integer id);
}
