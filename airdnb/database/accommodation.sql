CREATE TABLE accommodation (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               member_id VARCHAR(255) NOT NULL,
                               name VARCHAR(255) NOT NULL,
                               max_capacity BIGINT NOT NULL,
                               accommodation_type BIGINT NOT NULL,
                               room_type BIGINT NOT NULL,
                               bedroom_count BIGINT NOT NULL,
                               bathroom_count BIGINT NOT NULL,
                               bed_count BIGINT NOT NULL,
                               per_price BIGINT NOT NULL
);
