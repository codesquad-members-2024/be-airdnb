-- User table
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      user_name VARCHAR(16),
                      user_password VARCHAR(16),
                      user_email VARCHAR(30),
                      user_phone_number VARCHAR(11),
                      role VARCHAR(255),
                      oauth_type VARCHAR(5)
);

-- Hostroom table
CREATE TABLE hostroom (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          host_id BIGINT,
                          hostroom_name VARCHAR(20),
                          bed_number INT,
                          restroom_number INT,
                          bathroom_number INT,
                          region VARCHAR(16),
                          limited_adults INT,
                          limited_children INT,
                          limited_infants INT,
                          limited_pet INT,
                          is_pet BOOLEAN,
                          is_instantbook BOOLEAN,
                          is_selfcheckin BOOLEAN,
                          price INT,
                          checkin_date TIMESTAMP,
                          checkout_date TIMESTAMP,
                          is_reserved TINYINT(1),
                          FOREIGN KEY (host_id) REFERENCES user(id)
);

-- Reservedroom table
CREATE TABLE reservedroom (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              guest_id BIGINT,
                              adults INT,
                              children INT,
                              infants INT,
                              total_price INT,
                              checkin_date TIMESTAMP,
                              checkout_date TIMESTAMP,
                              hostroom_id BIGINT,
                              FOREIGN KEY (guest_id) REFERENCES user(id),
                              FOREIGN KEY (hostroom_id) REFERENCES hostroom(hostroom_id)
);

-- OfferedItem table
CREATE TABLE offeredItem (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             item_name VARCHAR(16)
);

-- HostroomItems table
CREATE TABLE hostroomItems (
                               hostroom_id BIGINT,
                               item_id BIGINT,
                               PRIMARY KEY (hostroom_id, item_id),
                               FOREIGN KEY (hostroom_id) REFERENCES hostroom(id),
                               FOREIGN KEY (item_id) REFERENCES offeredItem(id)
);

-- Tag table
CREATE TABLE tag (
                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                     tag_name VARCHAR(12)
);

-- HostroomTag table
CREATE TABLE hostroomTag (
                             hostroom_id BIGINT,
                             tag_id BIGINT,
                             PRIMARY KEY (hostroom_id, tag_id),
                             FOREIGN KEY (hostroom_id) REFERENCES hostroom(id),
                             FOREIGN KEY (tag_id) REFERENCES tag(id)
);

-- Review table
CREATE TABLE review (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        hostroom_id BIGINT,
                        guest_id BIGINT,
                        content TEXT,
                        rating INT,
                        FOREIGN KEY (hostroom_id) REFERENCES hostroom(id),
                        FOREIGN KEY (guest_id) REFERENCES user(id)
);
