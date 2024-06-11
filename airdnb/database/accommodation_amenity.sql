CREATE TABLE accommodation_amenity (
                                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                       accommodation_id BIGINT NOT NULL,
                                       amenity_id BIGINT NOT NULL,
                                       FOREIGN KEY (accommodation_id) REFERENCES accommodation(id),
                                       FOREIGN KEY (amenity_id) REFERENCES amenity(id)
);
