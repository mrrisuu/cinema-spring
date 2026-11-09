package com.project.cinema.repository;

import com.project.cinema.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
