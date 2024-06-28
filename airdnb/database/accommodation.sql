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
