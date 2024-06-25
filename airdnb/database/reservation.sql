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
    deleted          BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_reservation_member FOREIGN KEY (member_id) REFERENCES member (id),
    CONSTRAINT fk_reservation_accommodation FOREIGN KEY (accommodation_id) REFERENCES accommodation (id)
);
