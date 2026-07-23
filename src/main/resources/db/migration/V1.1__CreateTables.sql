CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    description VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
    id_user INTEGER NOT NULL,
    id_role INTEGER NOT NULL,
    PRIMARY KEY (id_user, id_role),
    FOREIGN KEY (id_user) REFERENCES users(id),
    FOREIGN KEY (id_role) REFERENCES roles(id)
);

INSERT INTO roles (description)
VALUES ('ROLE_ADMIN');

INSERT INTO roles (description)
VALUES ('ROLE_USER');

INSERT INTO users (username, password, enabled)
VALUES ('admin', '123', TRUE); -- Senha sem criptografia

INSERT INTO user_roles (id_user, id_role)
VALUES (1, 1);

INSERT INTO users (username, password, enabled)
VALUES ('user', '456', TRUE); -- Senha sem criptografia

INSERT INTO user_roles (id_user, id_role)
VALUES (2, 2);