-- password: Qwerty1!
INSERT INTO users (id, username, password, firstname, lastname)
VALUES (1, 'user', '$2y$10$2gTuNhXypMqQdomPnBXKx.lQpOx9NLESWcYXOc5UGNamyBYN7xNsy', 'Oliver', 'Smith'),
       (2, 'admin', '$2y$10$2gTuNhXypMqQdomPnBXKx.lQpOx9NLESWcYXOc5UGNamyBYN7xNsy', 'Olivia', 'Jones');

INSERT INTO authority (id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id)
VALUES (1, 1),
       (2, 1),
       (2, 2);
