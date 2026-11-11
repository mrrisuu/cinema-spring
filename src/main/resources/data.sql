INSERT INTO genres (genre_name, description)
VALUES
    ('Action', 'Movies with action scenes'),
    ('Drama', 'Movies with dramatic stories'),
    ('Romance', 'Movies about love'),
    ('Horror', 'Movies designed to frighten');

-- Roles
INSERT INTO roles (role_name)
VALUES
    ('ADMIN'),
    ('CUSTOMER');

-- Users
INSERT INTO users (work_email, password)
VALUES
    ('admin1@cinema.com', '$2a$10$5wSsXa3oATa6/3ZfW7H1lecsbYHJ/lmDTQCJyAHx.OMtx8cbD/R/G'),
    ('admin2@cinema.com', '$2a$10$5wSsXa3oATa6/3ZfW7H1lecsbYHJ/lmDTQCJyAHx.OMtx8cbD/R/G'),
    ('customer1@cinema.com', '$2a$10$5wSsXa3oATa6/3ZfW7H1lecsbYHJ/lmDTQCJyAHx.OMtx8cbD/R/G'),
    ('customer2@cinema.com', '$2a$10$5wSsXa3oATa6/3ZfW7H1lecsbYHJ/lmDTQCJyAHx.OMtx8cbD/R/G');

-- User Roles
INSERT INTO user_roles (work_email, role_id)
VALUES
    ('admin1@cinema.com', 1),
    ('admin2@cinema.com', 1),
    ('customer1@cinema.com', 2),
    ('customer2@cinema.com', 2);