INSERT INTO users (id, username, password, role, age)
VALUES
    ('user1', 'username1', 'password1', 'HOST', 100),
    ('user2', 'username2', 'password2', 'HOST', 35),
    ('user3', 'username3', 'password3', 'HOST', 25),
    ('user4', 'username4', 'password4', 'HOST', 30),
    ('user5', 'username5', 'password5', 'HOST', 40),
    ('user6', 'username6', 'password6', 'CLIENT', 45),
    ('user7', 'username7', 'password7', 'CLIENT', 55),
    ('user8', 'username8', 'password8', 'CLIENT', 60),
    ('user9', 'username9', 'password9', 'CLIENT', 70),
    ('user10', 'username10', 'password10', 'CLIENT', 75);

INSERT INTO accommodations ( title, content, price, discount_rate, address, latitude, longitude, comments_num, max_adults, max_children, max_infants, max_pets, user_id )
VALUES
    ( 'House1', 'content1', 100000, 10, 'address', 25.7617, -80.1918, 0, 2, 0, 0, 0, 'user1'),
    ( 'House2', 'content2', 200000, 20, 'address', 25.7617, -80.1918, 0, 1, 2, 2, 0, 'user1'),
    ( 'House3', 'content3', 300000, 30, 'address', 25.7617, -80.1918, 0, 5, 1, 0, 0, 'user1'),
    ( 'House4', 'content4', 400000, 40, 'address', 25.7617, -80.1918, 0, 7, 2, 0, 0, 'user2'),
    ( 'House5', 'content5', 500000, 50, 'address', 25.7617, -80.1918, 0, 3, 4, 0, 0, 'user2'),
    ( 'House6', 'content6', 600000, 60, 'address', 25.7617, -80.1918, 0, 1, 0, 0, 0, 'user3'),
    ( 'House7', 'content7', 700000, 70, 'address', 25.7617, -80.1918, 0, 2, 0, 0, 0, 'user3'),
    ( 'House8', 'content8', 800000, 80, 'address', 25.7617, -80.1918, 0, 4, 3, 2, 1, 'user4'),
    ( 'House9', 'content9', 900000, 90, 'address', 25.7617, -80.1918, 0, 1, 0, 0, 4, 'user4'),
    ( 'House10', 'content10', 1000000, 90, 'address', 25.7617, -80.1918, 0, 2, 0, 0, 3, 'user5');

INSERT INTO comments (score, content, user_id, accommodation_id, created_at)
VALUES
    (4.5, 'Great accommodation!', 'user1', 1, NOW()),
    (3.8, 'Nice place, but could be cleaner.', 'user1', 2, NOW()),
    (5.0, 'Perfect location and amenities.', 'user1', 3, NOW()),
    (4.2, 'Friendly host and comfortable stay.', 'user9', 4, NOW()),
    (4.9, 'Absolutely fantastic!', 'user1', 5, NOW()),
    (4.0, 'Good value for the price.', 'user2', 6, NOW()),
    (3.5, 'Decent place, but noisy neighborhood.', 'user7', 7, NOW()),
    (4.7, 'Wonderful experience overall.', 'user3', 8, NOW()),
    (4.3, 'Very cozy and well-maintained.', 'user2', 9, NOW()),
    (4.6, 'Highly recommend this place!', 'user4', 10, NOW());

INSERT INTO amenities (beds, bathrooms, kitchen, dedicated_workspace, tv, washing_machine, air_conditioner, wireless_internet, free_parking, paid_parking, accommodation_id)
VALUES
    (2, 1, 'AVAILABLE', 'NOT_AVAILABLE', 'AVAILABLE', 'AVAILABLE', 'NOT_AVAILABLE', 'AVAILABLE', 'NOT_AVAILABLE', 'AVAILABLE', 1),
    (3, 2, 'AVAILABLE', 'AVAILABLE', 'AVAILABLE', 'AVAILABLE', 'AVAILABLE', 'AVAILABLE', 'AVAILABLE', 'NOT_AVAILABLE', 2),
    (1, 1, 'AVAILABLE', 'NOT_AVAILABLE', 'AVAILABLE', 'NOT_AVAILABLE', 'AVAILABLE', 'AVAILABLE', 'AVAILABLE', 'AVAILABLE', 3);

INSERT INTO wishlists (user_id, accommodation_id)
VALUES
    ('user1', 1),
    ('user2', 2),
    ('user1', 3);

INSERT INTO reservations (adults, children, infants, pets, price, start_date, end_date, user_id, accommodation_id)
VALUES
    (2, 1, 0, 0, 150000, '2024-07-01', '2024-07-05', 'user1', 1),
    (4, 2, 1, 1, 250000, '2024-08-10', '2024-08-15', 'user2', 2),
    (1, 0, 0, 0, 100000, '2024-09-20', '2024-09-25', 'user3', 3);
