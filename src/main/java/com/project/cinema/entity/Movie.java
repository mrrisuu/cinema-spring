package com.project.cinema.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "movies")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "movie_name")
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name ="genre_id", nullable = false)
    private Genre genre;
}
