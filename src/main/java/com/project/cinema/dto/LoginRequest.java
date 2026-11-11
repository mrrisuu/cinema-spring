package com.project.cinema.dto;

import com.project.cinema.entity.enums.RoleEnum;

public record LoginRequest(
        String workEmail,
        String password,
        RoleEnum role
) {}
