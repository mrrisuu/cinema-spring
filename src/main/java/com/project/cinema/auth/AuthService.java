package com.project.cinema.auth;

import com.project.cinema.dto.LoginRequest;
import com.project.cinema.dto.LoginResponse;
import com.project.cinema.repository.UserRepository;
import com.project.cinema.security.CustomUserDetail;
import com.project.cinema.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.workEmail(),
                        request.password()
                )
        );

        CustomUserDetail userDetail =
                (CustomUserDetail) authentication.getPrincipal();

        boolean hasRole = userDetail.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals(
                                "ROLE_" + request.role().name()
                        )
                );

        if (!hasRole) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(
                userDetail,
                "ROLE_" + request.role().name()
        );

        return new LoginResponse(token);
    }

}