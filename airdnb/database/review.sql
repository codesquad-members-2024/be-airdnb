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
