-- Insert sample data into DISCOUNT_POLICY
INSERT INTO DISCOUNT_POLICY (description, policyBeanName, createdAt, lastModifiedAt) VALUES
('Early Bird Discount', 'EarlyBirdPolicy', NOW(), NOW()),
('Last Minute Discount', 'LastMinutePolicy', NOW(), NOW());

-- Insert sample data into USERS
INSERT INTO USERS (email, name, picture, registrationId, role, createdAt, lastModifiedAt) VALUES
('admin@example.com', 'Admin User', 'admin_pic.jpg', 'admin_reg', 'ADMIN', NOW(), NOW()),
('host1@example.com', 'Host One', 'host1_pic.jpg', 'host1_reg', 'HOST', NOW(), NOW()),
('host2@example.com', 'Host Two', 'host2_pic.jpg', 'host2_reg', 'HOST', NOW(), NOW()),
('host3@example.com', 'Host Three', 'host3_pic.jpg', 'host3_reg', 'HOST', NOW(), NOW()),
('user1@example.com', 'User One', 'user1_pic.jpg', 'user1_reg', 'USER', NOW(), NOW()),
('user2@example.com', 'User Two', 'user2_pic.jpg', 'user2_reg', 'USER', NOW(), NOW());

-- Insert sample data into ACCOMMODATION
INSERT INTO ACCOMMODATION (address, point, zipCode, basePricePerDay, description, name, bathroomCount, bedCount, bedroomCount, isPrivate, maxOccupancy, type, host_id, createdAt, lastModifiedAt) VALUES
('서울특별시 강남구 역삼동 123-1', ST_GeomFromText('POINT(37.532600 127.024612)', 4326), 06241, 100000, '서울의 멋진 장소', '서울 스테이 1', 1, 1, 1, 1, 2, 1, 2, NOW(), NOW()),
('서울특별시 강남구 역삼동 456-2', ST_GeomFromText('POINT(37.533000 127.025000)', 4326), 06242, 105000, '서울의 아늑한 장소', '서울 스테이 2', 1, 1, 1, 0, 3, 1, 2, NOW(), NOW()),
('서울특별시 강남구 역삼동 789-3', ST_GeomFromText('POINT(37.534000 127.026000)', 4326), 06243, 110000, '서울의 럭셔리 장소', '서울 스테이 3', 2, 2, 2, 1, 4, 1, 2, NOW(), NOW()),
('서울특별시 마포구 합정동 1-1', ST_GeomFromText('POINT(37.549800 126.914600)', 4326), 04084, 90000, '서울의 편안한 장소', '서울 스테이 4', 1, 1, 1, 1, 2, 1, 2, NOW(), NOW()),
('서울특별시 마포구 합정동 2-2', ST_GeomFromText('POINT(37.550000 126.915000)', 4326), 04085, 95000, '서울의 멋진 장소', '서울 스테이 5', 1, 1, 1, 0, 3, 1, 2, NOW(), NOW()),
-- Repeat for other 15 entries in Seoul
('부산광역시 해운대구 우동 123-1', ST_GeomFromText('POINT(35.179554 129.075641)', 4326), 48094, 80000, '부산의 아름다운 장소', '부산 스테이 1', 1, 1, 1, 0, 4, 2, 2, NOW(), NOW()),
('부산광역시 해운대구 우동 456-2', ST_GeomFromText('POINT(35.180000 129.076000)', 4326), 48095, 85000, '부산의 아늑한 장소', '부산 스테이 2', 1, 1, 1, 0, 3, 2, 2, NOW(), NOW()),
('부산광역시 해운대구 우동 789-3', ST_GeomFromText('POINT(35.181000 129.077000)', 4326), 48096, 90000, '부산의 럭셔리 장소', '부산 스테이 3', 2, 2, 2, 1, 5, 2, 2, NOW(), NOW()),
('부산광역시 해운대구 중동 1-1', ST_GeomFromText('POINT(35.170000 129.163000)', 4326), 48097, 75000, '부산의 편안한 장소', '부산 스테이 4', 1, 1, 1, 1, 2, 2, 2, NOW(), NOW()),
('부산광역시 해운대구 중동 2-2', ST_GeomFromText('POINT(35.171000 129.164000)', 4326), 48098, 80000, '부산의 멋진 장소', '부산 스테이 5', 1, 1, 1, 0, 3, 2, 2, NOW(), NOW()),
-- Repeat for other 15 entries in Busan
('제주특별자치도 제주시 애월읍 123-1', ST_GeomFromText('POINT(33.499621 126.531188)', 4326), 63001, 120000, '제주의 평화로운 장소', '제주 스테이 1', 2, 2, 2, 1, 5, 1, 3, NOW(), NOW()),
('제주특별자치도 제주시 애월읍 456-2', ST_GeomFromText('POINT(33.500000 126.532000)', 4326), 63002, 125000, '제주의 아늑한 장소', '제주 스테이 2', 1, 1, 1, 0, 4, 1, 3, NOW(), NOW()),
('제주특별자치도 제주시 애월읍 789-3', ST_GeomFromText('POINT(33.501000 126.533000)', 4326), 63003, 130000, '제주의 럭셔리 장소', '제주 스테이 3', 2, 2, 2, 1, 6, 1, 3, NOW(), NOW()),
('제주특별자치도 서귀포시 중문동 1-1', ST_GeomFromText('POINT(33.248000 126.415000)', 4326), 63004, 100000, '제주의 편안한 장소', '제주 스테이 4', 1, 1, 1, 1, 2, 1, 3, NOW(), NOW()),
('제주특별자치도 서귀포시 중문동 2-2', ST_GeomFromText('POINT(33.249000 126.416000)', 4326), 63005, 105000, '제주의 멋진 장소', '제주 스테이 5', 1, 1, 1, 0, 3, 1, 3, NOW(), NOW()),
-- Repeat for other 15 entries in Jeju
('인천광역시 연수구 송도동 123-1', ST_GeomFromText('POINT(37.456256 126.705206)', 4326), 21942, 75000, '인천의 아늑한 장소', '인천 스테이 1', 1, 1, 1, 1, 3, 1, 2, NOW(), NOW()),
('인천광역시 연수구 송도동 456-2', ST_GeomFromText('POINT(37.457000 126.706000)', 4326), 21943, 80000, '인천의 멋진 장소', '인천 스테이 2', 1, 1, 1, 0, 4, 1, 2, NOW(), NOW()),
('인천광역시 연수구 송도동 789-3', ST_GeomFromText('POINT(37.458000 126.707000)', 4326), 21944, 85000, '인천의 럭셔리 장소', '인천 스테이 3', 2, 2, 2, 1, 5, 1, 2, NOW(), NOW()),
('인천광역시 남동구 논현동 1-1', ST_GeomFromText('POINT(37.400000 126.737000)', 4326), 21945, 70000, '인천의 편안한 장소', '인천 스테이 4', 1, 1, 1, 1, 2, 1, 2, NOW(), NOW()),
('인천광역시 남동구 논현동 2-2', ST_GeomFromText('POINT(37.401000 126.738000)', 4326), 21946, 75000, '인천의 멋진 장소', '인천 스테이 5', 1, 1, 1, 0, 3, 1, 2, NOW(), NOW()),
-- Repeat for other 15 entries in Incheon
('광주광역시 동구 충장로 123-1', ST_GeomFromText('POINT(35.159545 126.851338)', 4326), 61475, 85000, '광주의 모던한 장소', '광주 스테이 1', 1, 1, 1, 0, 3, 2, 2, NOW(), NOW()),
('광주광역시 동구 충장로 456-2', ST_GeomFromText('POINT(35.160000 126.852000)', 4326), 61476, 90000, '광주의 아늑한 장소', '광주 스테이 2', 1, 1, 1, 0, 4, 2, 2, NOW(), NOW()),
('광주광역시 동구 충장로 789-3', ST_GeomFromText('POINT(35.161000 126.853000)', 4326), 61477, 95000, '광주의 럭셔리 장소', '광주 스테이 3', 2, 2, 2, 1, 5, 2, 2, NOW(), NOW()),
('광주광역시 북구 운암동 1-1', ST_GeomFromText('POINT(35.177000 126.895000)', 4326), 61478, 80000, '광주의 편안한 장소', '광주 스테이 4', 1, 1, 1, 1, 2, 2, 2, NOW(), NOW()),
('광주광역시 북구 운암동 2-2', ST_GeomFromText('POINT(35.178000 126.896000)', 4326), 61479, 85000, '광주의 멋진 장소', '광주 스테이 5', 1, 1, 1, 0, 3, 2, 2, NOW(), NOW());
-- Repeat for other 15 entries in Gwangju


-- Insert sample data into ACCOMMODATION_PICTURE
INSERT INTO ACCOMMODATION_PICTURE (url, accommodation_id) VALUES
('seoul_stay_pic1.jpg', 1),
('seoul_stay_pic2.jpg', 2),
('seoul_stay_pic3.jpg', 3),
-- Repeat for other 57 entries in Seoul
('busan_stay_pic1.jpg', 21),
('busan_stay_pic2.jpg', 22),
('busan_stay_pic3.jpg', 23);

-- Insert sample data into PAYMENT
INSERT INTO PAYMENT (accommodationFee, serviceFee, status, totalPrice, createdAt, lastModifiedAt) VALUES
(90000, 10000, 'COMPLETE', 100000, NOW(), NOW()),
(85000, 15000, 'COMPLETE', 100000, NOW(), NOW()),
(120000, 20000, 'COMPLETE', 140000, NOW(), NOW()),
(75000, 5000, 'COMPLETE', 80000, NOW(), NOW()),
(70000, 10000, 'COMPLETE', 80000, NOW(), NOW());
-- Repeat for other 15 payments

-- Insert sample data into REVIEW
INSERT INTO REVIEW (content, rating, createdAt, lastModifiedAt) VALUES
('Great place!', 5, NOW(), NOW()),
('Not bad.', 3, NOW(), NOW()),
('Could be better.', 4, NOW(), NOW()),
('Excellent stay!', 5, NOW(), NOW()),
('Would not recommend.', 2, NOW(), NOW());
-- Repeat for other 15 reviews

-- Insert sample data into BOOKING
INSERT INTO BOOKING (checkin, checkout, headCount, status, booker_id, host_id, payment_id, review_id, createdAt, lastModifiedAt) VALUES
('2023-06-01', '2023-06-10', 2, 'COMPLETE', 5, 2, 1, 1, NOW(), NOW()),
('2023-07-01', '2023-07-05', 1, 'COMPLETE', 5, 2, 2, 2, NOW(), NOW()),
('2023-08-01', '2023-08-10', 4, 'COMPLETE', 5, 3, 3, 3, NOW(), NOW()),
('2023-09-01', '2023-09-05', 3, 'COMPLETE', 5, 3, 4, 4, NOW(), NOW()),
('2023-10-01', '2023-10-10', 2, 'COMPLETE', 5, 3, 5, 5, NOW(), NOW());
-- Repeat for other 15 bookings

-- Insert sample data into PRODUCT
INSERT INTO PRODUCT (date, price, status, accommodation_id, booking_id, createdAt, lastModifiedAt) VALUES
('2023-06-01', 100000, 'BOOKED', 1, 1, NOW(), NOW()),
('2023-07-01', 105000, 'BOOKED', 2, 2, NOW(), NOW()),
('2023-08-01', 110000, 'BOOKED', 3, 3, NOW(), NOW()),
('2023-09-01', 95000, 'BOOKED', 4, 4, NOW(), NOW()),
('2023-10-01', 90000, 'BOOKED', 5, 5, NOW(), NOW());
-- Repeat for other 15 products

-- Insert sample data into REVIEW_REPLY
INSERT INTO REVIEW_REPLY (content, review_id, writer_id) VALUES
('Thank you for the feedback!', 1, 2),
('We will improve.', 2, 2),
('Glad you enjoyed your stay!', 3, 3),
('We appreciate your comments.', 4, 3),
('Sorry to hear that.', 5, 3);
-- Repeat for other 15 replies

-- Insert sample data into USER_LIKES_PRODUCT
INSERT INTO USER_LIKES_PRODUCT (product_id, user_id, createdAt, lastModifiedAt) VALUES
(1, 5, NOW(), NOW()),
(2, 5, NOW(), NOW()),
(3, 5, NOW(), NOW()),
(4, 5, NOW(), NOW()),
(5, 5, NOW(), NOW());
-- Repeat for other 15 likes
