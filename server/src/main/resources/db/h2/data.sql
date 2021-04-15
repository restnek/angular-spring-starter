-- password: qwerty
INSERT INTO users (id, username, password, firstname, lastname)
VALUES (1, 'user', '$2a$10$aeQntRxYoXqi6BNcuB4cgurGZ0kHs4hycXnadisEMoPTuw5sam6Zy', 'Oliver', 'Smith'),
       (2, 'admin', '$2a$10$aeQntRxYoXqi6BNcuB4cgurGZ0kHs4hycXnadisEMoPTuw5sam6Zy', 'Olivia', 'Jones');

INSERT INTO authority (id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id)
VALUES (1, 1),
       (2, 1),
       (2, 2);
