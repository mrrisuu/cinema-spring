package com.project.cinema.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "genres")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "genre_name")
    private String name;

    private String description;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "genre")
    private List<Movie> movies = new ArrayList<>();
}
