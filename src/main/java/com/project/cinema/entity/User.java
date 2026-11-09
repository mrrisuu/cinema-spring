package com.project.cinema.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="users")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "work_email", length = 100)
    private String workEmail;

    @Column(name = "password", nullable = false, length = 100)
    private String password;
}
