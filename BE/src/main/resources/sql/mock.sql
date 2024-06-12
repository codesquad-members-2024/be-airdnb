-- accommodation_amenity 데이터 삽입
INSERT INTO accommodation_amenity (name)
VALUES ('WiFi'),
       ('Pool'),
       ('Parking'),
       ('Gym'),
       ('Breakfast');

-- discount_policy 데이터 삽입
INSERT INTO discount_policy (created_at, last_modified_at, description)
VALUES (NOW(), NOW(), '10% off for early birds'),
       (NOW(), NOW(), '5% off for members');

-- users 데이터 삽입
INSERT INTO users (created_at, last_modified_at, email, name, picture, registration_id, role)
VALUES (NOW(), NOW(), 'alice@example.com', 'Alice', 'alice.jpg', 'reg1', 'USER'),
       (NOW(), NOW(), 'bob@example.com', 'Bob', 'bob.jpg', 'reg2', 'USER'),
       (NOW(), NOW(), 'carol@example.com', 'Carol', 'carol.jpg', 'reg3', 'ADMIN');

-- accommodation 데이터 삽입
INSERT INTO accommodation (base_price_per_day, bathroom_count, bed_count, bedroom_count, is_private, max_occupancy,
                           type, zip_code, created_at, last_modified_at, address, description, name, point, host_id)
VALUES (100, 1, 2, 1, 1, 2, 1, 12345, NOW(), NOW(), '123 Main St', 'Cozy apartment in the city center',
        'City Center Apartment', ST_GeomFromText('POINT(10 20)', 4326), 1),
       (150, 2, 3, 2, 1, 4, 1, 54321, NOW(), NOW(), '456 Elm St', 'Spacious house with a garden', 'Garden House',
        ST_GeomFromText('POINT(30 40)', 4326), 2),
       (80, 1, 1, 1, 1, 1, 1, 67890, NOW(), NOW(), '789 Oak St', 'Small studio for solo travelers', 'Solo Studio',
        ST_GeomFromText('POINT(50 60)', 4326), 3);

-- accommodation 추가 데이터 삽입 (한국 주소 사용)
INSERT INTO accommodation (base_price_per_day, bathroom_count, bed_count, bedroom_count, is_private, max_occupancy,
                           type, zip_code, created_at, last_modified_at, address, description, name, point, host_id)
VALUES (120, 2, 3, 2, 1, 4, 1, 12345, NOW(), NOW(), '서울특별시 강남구 테헤란로 123', '모던 아파트', 'Gangnam Modern Apartment',
        ST_GeomFromText('POINT(37.497942 127.027621)', 4326), 1),
       (90, 1, 2, 1, 1, 2, 1, 54321, NOW(), NOW(), '서울특별시 마포구 홍익로 50', '홍대 근처 아파트', 'Hongdae Cozy Apartment',
        ST_GeomFromText('POINT(37.555138 126.923834)', 4326), 2),
       (200, 2, 4, 3, 1, 6, 1, 67890, NOW(), NOW(), '서울특별시 송파구 올림픽로 300', '럭셔리 펜트하우스', 'Songpa Luxury Penthouse',
        ST_GeomFromText('POINT(37.514543 127.105986)', 4326), 3),
       (70, 1, 1, 1, 1, 2, 1, 13579, NOW(), NOW(), '서울특별시 종로구 세종대로 99', '편안한 스튜디오', 'Jongno Comfortable Studio',
        ST_GeomFromText('POINT(37.570029 126.976849)', 4326), 1),
       (110, 1, 2, 1, 1, 3, 1, 24680, NOW(), NOW(), '부산광역시 해운대구 해운대해변로 140', '해운대 바다 전망 아파트',
        'Haeundae Ocean View Apartment', ST_GeomFromText('POINT(35.158698 129.160384)', 4326), 2),
       (95, 1, 2, 1, 1, 2, 1, 13579, NOW(), NOW(), '인천광역시 연수구 송도국제대로 123', '송도 국제 도시 아파트',
        'Songdo International City Apartment', ST_GeomFromText('POINT(37.379789 126.666413)', 4326), 3),
       (150, 2, 3, 2, 1, 4, 1, 54321, NOW(), NOW(), '대구광역시 수성구 범어천로 200', '수성구 대형 아파트', 'Suseong Large Apartment',
        ST_GeomFromText('POINT(35.855100 128.629729)',4326), 1),
       (80, 1, 2, 1, 1, 3, 1, 98765, NOW(), NOW(), '경기도 성남시 분당구 탄천로 100', '분당 쾌적한 아파트', 'Bundang Cozy Apartment',
        ST_GeomFromText('POINT(37.381634 127.126217)' ,4326), 2),
       (105, 1, 2, 1, 1, 2, 1, 13579, NOW(), NOW(), '제주특별자치도 제주시 탑동로 50', '제주도 해변 근처 아파트', 'Jeju Beachside Apartment',
        ST_GeomFromText('POINT(33.512905 126.529874)',4326), 3),
       (130, 1, 3, 2, 1, 4, 1, 67890, NOW(), NOW(), '광주광역시 서구 상무대로 150', '광주 시티 센터 아파트',
        'Gwangju City Center Apartment', ST_GeomFromText('POINT(35.159545 126.851578)' ,4326), 1);

-- accommodation_picture 데이터 삽입
INSERT INTO accommodation_picture (accommodation_id, url)
VALUES (1, 'city_center_apartment_1.jpg'),
       (1, 'city_center_apartment_2.jpg'),
       (2, 'garden_house_1.jpg'),
       (2, 'garden_house_2.jpg'),
       (3, 'solo_studio_1.jpg');

-- accommodation_product 데이터 삽입
INSERT INTO accommodation_product (date, price, status, accommodation_id, created_at, last_modified_at)
VALUES ('2024-06-01', 100, 0, 2, NOW(), NOW()),
       ('2024-06-02', 100, 0, 2, NOW(), NOW()),
       ('2024-06-03', 100, 0, 2, NOW(), NOW()),
       ('2024-06-04', 100, 0, 2, NOW(), NOW()),
       ('2024-06-01', 150, 0, 2, NOW(), NOW()),
       ('2024-06-02', 150, 0, 2, NOW(), NOW()),
       ('2024-06-03', 150, 0, 2, NOW(), NOW()),
       ('2024-06-04', 150, 0, 2, NOW(), NOW()),
       ('2024-06-01', 80, 0, 3, NOW(), NOW()),
       ('2024-06-02', 80, 0, 3, NOW(), NOW()),
       ('2024-06-03', 80, 0, 3, NOW(), NOW()),
       ('2024-06-04', 80, 0, 3, NOW(), NOW());

-- accommodation_product 추가 데이터 삽입
INSERT INTO accommodation_product (date, price, status, accommodation_id, created_at, last_modified_at)
VALUES ('2024-07-01', 120, 0, 4, NOW(), NOW()),
       ('2024-07-02', 120, 0, 4, NOW(), NOW()),
       ('2024-07-03', 120, 0, 4, NOW(), NOW()),
       ('2024-07-04', 120, 0, 4, NOW(), NOW()),
       ('2024-07-05', 120, 0, 4, NOW(), NOW()),
       ('2024-07-01', 90, 0, 5, NOW(), NOW()),
       ('2024-07-02', 90, 0, 5, NOW(), NOW()),
       ('2024-07-03', 90, 0, 5, NOW(), NOW()),
       ('2024-07-04', 90, 0, 5, NOW(), NOW()),
       ('2024-07-05', 90, 0, 5, NOW(), NOW()),
       ('2024-07-01', 200, 0, 6, NOW(), NOW()),
       ('2024-07-02', 200, 0, 6, NOW(), NOW()),
       ('2024-07-03', 200, 0, 6, NOW(), NOW()),
       ('2024-07-04', 200, 0, 6, NOW(), NOW()),
       ('2024-07-05', 200, 0, 6, NOW(), NOW()),
       ('2024-07-01', 70, 0, 7, NOW(), NOW()),
       ('2024-07-02', 70, 0, 7, NOW(), NOW()),
       ('2024-07-03', 70, 0, 7, NOW(), NOW()),
       ('2024-07-04', 70, 0, 7, NOW(), NOW()),
       ('2024-07-05', 70, 0, 7, NOW(), NOW()),
       ('2024-07-01', 110, 0, 8, NOW(), NOW()),
       ('2024-07-02', 110, 0, 8, NOW(), NOW()),
       ('2024-07-03', 110, 0, 8, NOW(), NOW()),
       ('2024-07-04', 110, 0, 8, NOW(), NOW()),
       ('2024-07-05', 110, 0, 8, NOW(), NOW()),
       ('2024-07-01', 95, 0, 9, NOW(), NOW()),
       ('2024-07-02', 95, 0, 9, NOW(), NOW()),
       ('2024-07-03', 95, 0, 9, NOW(), NOW()),
       ('2024-07-04', 95, 0, 9, NOW(), NOW()),
       ('2024-07-05', 95, 0, 9, NOW(), NOW()),
       ('2024-07-01', 150, 0, 10, NOW(), NOW()),
       ('2024-07-02', 150, 0, 10, NOW(), NOW()),
       ('2024-07-03', 150, 0, 10, NOW(), NOW()),
       ('2024-07-04', 150, 0, 10, NOW(), NOW()),
       ('2024-07-05', 150, 0, 10, NOW(), NOW()),
       ('2024-07-01', 80, 1, 11, NOW(), NOW()),
       ('2024-07-02', 80, 1, 11, NOW(), NOW()),
       ('2024-07-03', 80, 1, 11, NOW(), NOW()),
       ('2024-07-04', 80, 1, 11, NOW(), NOW()),
       ('2024-07-05', 80, 1, 11, NOW(), NOW()),
       ('2024-07-01', 105, 1, 12, NOW(), NOW()),
       ('2024-07-02', 105, 1, 12, NOW(), NOW()),
       ('2024-07-03', 105, 1, 12, NOW(), NOW()),
       ('2024-07-04', 105, 1, 12, NOW(), NOW()),
       ('2024-07-05', 105, 1, 12, NOW(), NOW()),
       ('2024-07-01', 130, 1, 13, NOW(), NOW()),
       ('2024-07-02', 130, 1, 13, NOW(), NOW()),
       ('2024-07-03', 130, 1, 13, NOW(), NOW()),
       ('2024-07-04', 130, 1, 13, NOW(), NOW()),
       ('2024-07-05', 130, 1, 13, NOW(), NOW());

-- booking 데이터 삽입
INSERT INTO booking (headcount, checkin, checkout,status, booker_id, created_at, last_modified_at)
VALUES (2, '2024-06-01', '2024-06-03', 0, 1, 1, NOW(), NOW()),
       (4, '2024-06-01', '2024-06-02', 2, 1, 2, NOW(), NOW());

-- payment 데이터 삽입
INSERT INTO payment (booking_fee, cancellation_fee, service_fee, status, taxes, total_fee, booking_id, created_at,
                     last_modified_at)
VALUES (100, 0, 10, 1, 5, 115, 1, NOW(), NOW()),
       (150, 0, 15, 1, 10, 175, 2, NOW(), NOW());

-- review 데이터 삽입
INSERT INTO review (score, booking_id, created_at, last_modified_at, writer_id, content)
VALUES (5, 1, NOW(), NOW(), 1, 'Great place!'),
       (4, 2, NOW(), NOW(), 2, 'Nice and cozy.');

-- user_likes_product 데이터 삽입
INSERT INTO user_likes_product (product_id, user_id)
VALUES (1, 1),
       (2, 1),
       (5, 2);