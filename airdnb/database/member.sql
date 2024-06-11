CREATE TABLE member
(
    id                    VARCHAR(255) NOT NULL PRIMARY KEY,
    password              VARCHAR(255),
    member_type           VARCHAR(255),
    profile               VARCHAR(255),
    email                 VARCHAR(255),
    member_name           VARCHAR(255),
    refresh_token         VARCHAR(255),
    token_expiration_time TIMESTAMP
);

