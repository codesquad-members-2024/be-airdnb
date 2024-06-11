CREATE TABLE accommodation_amenity (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       accommodation_id BIGINT NOT NULL,
                                       amenity_id BIGINT NOT NULL,
                                       CONSTRAINT fk_accommodation FOREIGN KEY (accommodation_id) REFERENCES accommodation (id) ON DELETE CASCADE,
                                       CONSTRAINT fk_amenity FOREIGN KEY (amenity_id) REFERENCES amenity (id) ON DELETE CASCADE
);
