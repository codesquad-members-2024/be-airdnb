-- data_insert.sql

-- Insert data into discount_policy
INSERT INTO squadbnb.discount_policy (created_at, last_modified_at, description)
VALUES
    (NOW(), NOW(), 'Early Bird Discount'),
    (NOW(), NOW(), 'Last Minute Discount'),
    (NOW(), NOW(), 'Seasonal Discount');

-- Insert data into users
INSERT INTO squadbnb.users (created_at, last_modified_at, profile_image)
VALUES
    (NOW(), NOW(), 'profile1.jpg'),
    (NOW(), NOW(), 'profile2.jpg'),
    (NOW(), NOW(), 'profile3.jpg');

-- Insert data into accomodation
INSERT INTO squadbnb.accomodation (type, zip_code, created_at, last_modified_at, host_id, address, name, point)
VALUES
    (1, 12345, NOW(), NOW(), 1, '123 Main St', 'Cozy Cottage', ST_GeomFromText('POINT(77.5945627 12.9715987)', 4326)),
    (2, 67890, NOW(), NOW(), 2, '456 Elm St', 'Luxury Villa', ST_GeomFromText('POINT(77.5970 13.0358)', 4326)),
    (3, 10111, NOW(), NOW(), 3, '789 Pine St', 'Budget Apartment', ST_GeomFromText('POINT(77.6245 12.9352)', 4326)),
    (1, 54321, NOW(), NOW(), 1, '321 Maple St', 'Modern Loft', ST_GeomFromText('POINT(78.4867 17.3850)', 4326)),
    (2, 98765, NOW(), NOW(), 2, '654 Oak St', 'Beach House', ST_GeomFromText('POINT(72.8777 19.0760)', 4326)),
    (3, 20202, NOW(), NOW(), 3, '987 Birch St', 'Country Cottage', ST_GeomFromText('POINT(77.1025 28.7041)', 4326)),
    (1, 30303, NOW(), NOW(), 1, '111 Cedar St', 'Urban Apartment', ST_GeomFromText('POINT(77.216721 28.644800)', 4326)),
    (2, 40404, NOW(), NOW(), 2, '222 Walnut St', 'Mountain Cabin', ST_GeomFromText('POINT(88.3639 22.5726)', 4326)),
    (3, 50505, NOW(), NOW(), 3, '333 Chestnut St', 'City Studio', ST_GeomFromText('POINT(80.2707 13.0827)', 4326)),
    (1, 60606, NOW(), NOW(), 1, '444 Sycamore St', 'Suburban House', ST_GeomFromText('POINT(85.3240 23.3441)', 4326));


-- Insert data into accomodation_picture
INSERT INTO squadbnb.accomodation_picture (accomodation_id, url)
VALUES
    (1, 'cottage1.jpg'),
    (1, 'cottage2.jpg'),
    (2, 'villa1.jpg'),
    (2, 'villa2.jpg'),
    (3, 'apartment1.jpg'),
    (3, 'apartment2.jpg');

-- Insert data into accomodation_product
INSERT INTO squadbnb.accomodation_product (date, max_headcount, price, status, accomodation_id, created_at, last_modified_at, discount_policy_id)
VALUES
    ('2024-07-01', 4, 100, 1, 1, NOW(), NOW(), 1),
    ('2024-07-02', 4, 100, 1, 1, NOW(), NOW(), 2),
    ('2024-07-01', 6, 200, 1, 2, NOW(), NOW(), 2),
    ('2024-07-02', 6, 200, 1, 2, NOW(), NOW(), 3),
    ('2024-07-01', 2, 50, 1, 3, NOW(), NOW(), 1),
    ('2024-07-02', 2, 50, 1, 3, NOW(), NOW(), 3);

-- Insert data into booking
INSERT INTO squadbnb.booking (adult_headcount, checkin, checkout, kid_headcount, status, booker_id, created_at, last_modified_at)
VALUES
    (2, '2024-07-01', '2024-07-05', 1, 1, 1, NOW(), NOW()),
    (4, '2024-07-02', '2024-07-06', 2, 1, 2, NOW(), NOW());

-- Insert data into booking_products
INSERT INTO squadbnb.booking_products (booking_entity_id, products_id)
VALUES
    (1, 1),
    (2, 3);

-- Insert data into payment
INSERT INTO squadbnb.payment (booking_fee, cancellation_fee, service_fee, status, taxes, total_fee, booking_id, created_at, last_modified_at)
VALUES
    (100, 10, 20, 1, 5, 135, 1, NOW(), NOW()),
    (200, 20, 40, 1, 10, 270, 2, NOW(), NOW());

-- Insert data into review
INSERT INTO squadbnb.review (score, booking_id, created_at, last_modified_at, writer_id, content)
VALUES
    (5, 1, NOW(), NOW(), 1, 'Great stay!'),
    (4, 2, NOW(), NOW(), 2, 'Good experience!');

-- Insert data into user_likes_product
INSERT INTO squadbnb.user_likes_product (user_id, product_id)
VALUES
    (1, 1),
    (2, 3),
    (3, 2);