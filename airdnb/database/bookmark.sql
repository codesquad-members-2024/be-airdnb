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
