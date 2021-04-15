DROP TABLE IF EXISTS user_authority;
DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
    id        BIGINT IDENTITY PRIMARY KEY,
    username  VARCHAR,
    password  VARCHAR,
    firstname VARCHAR,
    lastname  VARCHAR
);

CREATE TABLE authority (
    id   BIGINT IDENTITY PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE user_authority (
    user_id      BIGINT NOT NULL,
    authority_id BIGINT NOT NULL
);
ALTER TABLE user_authority
    ADD CONSTRAINT fk_user_authority_users FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE user_authority
    ADD CONSTRAINT fk_user_authority_authority FOREIGN KEY (authority_id) REFERENCES authority (id);
