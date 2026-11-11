package com.project.cinema.repository;

import com.project.cinema.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Page<Genre> findAll(Pageable pageable);

    Page<Genre> findByName(Pageable pageable, String name);

    Optional<Genre> findByNameIgnoreCase(String name);
}
