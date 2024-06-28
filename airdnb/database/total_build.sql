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

CREATE TABLE admin
(
    admin_id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(30) NOT NULL
);

CREATE TABLE amenity (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL
);

CREATE TABLE accommodation_room_type
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE accommodation_type
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE accommodation
(
    id                        BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id                 VARCHAR(255) NOT NULL,
    name                      VARCHAR(255) NOT NULL,
    max_capacity              BIGINT       NOT NULL,
    accommodation_type        BIGINT       NULL,
    accommodation_room_type   BIGINT       NULL,
    accommodation_description TEXT         NULL,
    accommodation_images      VARCHAR(2048) NULL,
    day_rate                  DECIMAL(10, 2)      NOT NULL,
    cleaning_fee              DECIMAL(10, 2)      NOT NULL,
    bedroom_count             INTEGER      NOT NULL,
    bathroom_count            INTEGER      NOT NULL,
    bed_count                 INTEGER      NOT NULL,

    -- Address fields
    city                      VARCHAR(255) NOT NULL,
    district                  VARCHAR(255) NOT NULL,
    neighborhood              VARCHAR(255) NOT NULL,
    street_name               VARCHAR(255) NOT NULL,
    detailed_address          VARCHAR(255) NOT NULL,

    -- Coordinate fields
    latitude                  DOUBLE       NOT NULL,
    longitude                 DOUBLE       NOT NULL,

    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE,
    CONSTRAINT fk_accommodation_type FOREIGN KEY (accommodation_type) REFERENCES accommodation_type (id) ON DELETE SET NULL,
    CONSTRAINT fk_accommodation_room_type FOREIGN KEY (accommodation_room_type) REFERENCES accommodation_room_type (id) ON DELETE SET NULL
);


CREATE TABLE accommodation_amenity (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       accommodation_id BIGINT NOT NULL,
                                       amenity_id BIGINT NOT NULL,
                                       CONSTRAINT fk_accommodation FOREIGN KEY (accommodation_id) REFERENCES accommodation (id) ON DELETE CASCADE,
                                       CONSTRAINT fk_amenity FOREIGN KEY (amenity_id) REFERENCES amenity (id) ON DELETE CASCADE
);

CREATE TABLE bookmark
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id        VARCHAR(255) NOT NULL,
    accommodation_id BIGINT       NOT NULL,
    CONSTRAINT fk_bookmark_member
        FOREIGN KEY (member_id)
            REFERENCES member (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_bookmark_accommodation
        FOREIGN KEY (accommodation_id)
            REFERENCES accommodation (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE reservation
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id        VARCHAR(255) NOT NULL,
    accommodation_id BIGINT       NOT NULL,
    check_in_date    DATE,
    check_out_date   DATE,
    capacity         BIGINT,
    is_confirmed     BOOLEAN,
    total_price      BIGINT,
    CONSTRAINT fk_reservation_member FOREIGN KEY (member_id) REFERENCES member (id),
    CONSTRAINT fk_reservation_accommodation FOREIGN KEY (accommodation_id) REFERENCES accommodation (id)
);

CREATE TABLE review
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id        VARCHAR(255) NOT NULL,
    accommodation_id BIGINT       NOT NULL,
    comment          VARCHAR(255),
    rate             DOUBLE       NOT NULL,
    CONSTRAINT fk_review_member
        FOREIGN KEY (member_id)
            REFERENCES member (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_review_accommodation
        FOREIGN KEY (accommodation_id)
            REFERENCES accommodation (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT chk_rate
        CHECK (rate >= 0 AND rate <= 5)
);
