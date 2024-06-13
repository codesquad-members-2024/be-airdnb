INSERT INTO MEMBER(LOGIN_ID, LOGIN_PASSWORD, NICKNAME) VALUES ("test", "test", "test");
INSERT INTO MEMBER(LOGIN_ID, LOGIN_PASSWORD, NICKNAME) VALUES ("test2", "test2", "test2");
INSERT INTO AMENITY(NAME) VALUES ("Air Conditioner");
INSERT INTO AMENITY(NAME) VALUES ("BBQ");
INSERT INTO AMENITY(NAME) VALUES ("CHULBONG");
INSERT INTO AMENITY(NAME) VALUES ("DARAKBANG");
INSERT INTO AMENITY(NAME) VALUES ("ENERGY DRINK");

-- MEMBER table
INSERT INTO `MEMBER` (`LOGIN_ID`, `LOGIN_PASSWORD`, `NICKNAME`) VALUES
('user1', 'password1', 'nickname1'),
('user2', 'password2', 'nickname2'),
('user3', 'password3', 'nickname3'),
('user4', 'password4', 'nickname4'),
('user5', 'password5', 'nickname5'),
('user6', 'password6', 'nickname6'),
('user7', 'password7', 'nickname7'),
('user8', 'password8', 'nickname8'),
('user9', 'password9', 'nickname9'),
('user10', 'password10', 'nickname10'),
('user11', 'password11', 'nickname11'),
('user12', 'password12', 'nickname12'),
('user13', 'password13', 'nickname13'),
('user14', 'password14', 'nickname14'),
('user15', 'password15', 'nickname15'),
('user16', 'password16', 'nickname16'),
('user17', 'password17', 'nickname17'),
('user18', 'password18', 'nickname18'),
('user19', 'password19', 'nickname19'),
('user20', 'password20', 'nickname20');

-- ACCOMMODATION table
INSERT INTO `ACCOMMODATION` (`HOST_ID`, `TITLE`, `PLACE_CATEGORY`, `BASE_PRICE_PER_NIGHT`, `DESCRIPTION`, `MAX_GUEST_COUNT`, `MAX_INFANT_COUNT`, `BEDROOM_COUNT`, `BED_COUNT`, `BATHROOM_COUNT`, `CHECK_IN_TIME`, `CHECK_OUT_TIME`, `COUNTRY`, `PROVINCE`, `CITY`, `DISTRICT`, `STREET_ADDRESS`, `STREET_ADDRESS_DETAIL`, `POSTAL_CODE`, `COORDINATE`) VALUES
(1, 'Cozy Cottage', 'Cottage', 100, 'A cozy cottage in the countryside.', 4, 1, 2, 3, 1, '15:00:00', '10:00:00', 'US', 'California', 'San Francisco', 'District 1', '123 Main St', 'Apt 4', '94101', ST_PointFromText('POINT(37.7749 -122.4194)', 4326)),
(2, 'Modern Apartment', 'Apartment', 200, 'A modern apartment in the city center.', 2, 0, 1, 1, 1, '16:00:00', '11:00:00', 'US', 'New York', 'New York', 'District 2', '456 Broadway', 'Suite 5B', '10012', ST_PointFromText('POINT(40.7128 -74.0060)', 4326)),
(3, 'Beach House', 'House', 300, 'A house by the beach with stunning views.', 6, 1, 3, 4, 2, '14:00:00', '11:00:00', 'US', 'Florida', 'Miami', 'District 3', '789 Ocean Dr', '', '33139', ST_PointFromText('POINT(25.7617 -80.1918)', 4326)),
(4, 'Mountain Cabin', 'Cabin', 150, 'A secluded cabin in the mountains.', 5, 2, 2, 3, 1, '17:00:00', '10:00:00', 'US', 'Colorado', 'Denver', 'District 4', '101 Mountain Rd', 'Cabin A', '80202', ST_PointFromText('POINT(39.7392 -104.9903)', 4326)),
(5, 'Downtown Loft', 'Loft', 250, 'A stylish loft in downtown.', 2, 0, 1, 1, 1, '15:00:00', '11:00:00', 'US', 'Texas', 'Austin', 'District 5', '202 Main St', 'Loft 2', '73301', ST_PointFromText('POINT(30.2672 -97.7431)', 4326)),
(6, 'Suburban Home', 'House', 180, 'A comfortable home in the suburbs.', 4, 1, 2, 3, 2, '16:00:00', '10:00:00', 'US', 'Illinois', 'Chicago', 'District 6', '303 Elm St', '', '60601', ST_PointFromText('POINT(41.8781 -87.6298)', 4326)),
(7, 'Riverside Bungalow', 'Bungalow', 220, 'A charming bungalow by the river.', 3, 1, 1, 2, 1, '14:00:00', '12:00:00', 'US', 'Oregon', 'Portland', 'District 7', '404 River Rd', '', '97201', ST_PointFromText('POINT(45.5051 -122.6750)', 4326)),
(8, 'Urban Studio', 'Studio', 120, 'A compact studio in the heart of the city.', 1, 0, 1, 1, 1, '15:00:00', '11:00:00', 'US', 'Washington', 'Seattle', 'District 8', '505 Pine St', 'Studio 3', '98101', ST_PointFromText('POINT(47.6062 -122.3321)', 4326)),
(9, 'Countryside Villa', 'Villa', 400, 'A luxurious villa in the countryside.', 8, 2, 4, 5, 3, '13:00:00', '10:00:00', 'US', 'Virginia', 'Richmond', 'District 9', '606 Country Ln', '', '23220', ST_PointFromText('POINT(37.5407 -77.4360)', 4326)),
(10, 'Historic Townhouse', 'Townhouse', 270, 'A historic townhouse with modern amenities.', 5, 1, 3, 3, 2, '16:00:00', '12:00:00', 'US', 'Massachusetts', 'Boston', 'District 10', '707 Beacon St', '', '02215', ST_PointFromText('POINT(42.3601 -71.0589)', 4326)),
(11, 'Luxury Penthouse', 'Penthouse', 500, 'A luxury penthouse with city views.', 4, 1, 2, 2, 2, '15:00:00', '11:00:00', 'US', 'Nevada', 'Las Vegas', 'District 11', '808 Strip Blvd', 'Penthouse 1', '89109', ST_PointFromText('POINT(36.1699 -115.1398)', 4326)),
(12, 'Eco-friendly Cottage', 'Cottage', 130, 'A green cottage with sustainable features.', 4, 1, 2, 3, 1, '14:00:00', '11:00:00', 'US', 'Oregon', 'Eugene', 'District 12', '909 Green St', '', '97401', ST_PointFromText('POINT(44.0521 -123.0868)', 4326)),
(13, 'Rustic Barn', 'Barn', 140, 'A rustic barn converted into a home.', 3, 1, 1, 2, 1, '15:00:00', '10:00:00', 'US', 'Kentucky', 'Lexington', 'District 13', '1010 Farm Rd', '', '40507', ST_PointFromText('POINT(38.0406 -84.5037)', 4326)),
(14, 'Seaside Condo', 'Condo', 210, 'A condo with a view of the sea.', 4, 1, 2, 3, 2, '14:00:00', '10:00:00', 'US', 'California', 'Los Angeles', 'District 14', '1111 Ocean Ave', 'Condo 5', '90001', ST_PointFromText('POINT(34.0522 -118.2437)', 4326)),
(15, 'Artistic Loft', 'Loft', 230, 'A loft with an artistic vibe.', 3, 1, 1, 2, 1, '16:00:00', '12:00:00', 'US', 'New York', 'Brooklyn', 'District 15', '1212 Art St', 'Loft 6', '11201', ST_PointFromText('POINT(40.6782 -73.9442)', 4326)),
(16, 'Garden Apartment', 'Apartment', 190, 'An apartment with a garden.', 2, 0, 1, 1, 1, '15:00:00', '11:00:00', 'US', 'Washington', 'Tacoma', 'District 16', '1313 Garden Rd', 'Apt 7', '98402', ST_PointFromText('POINT(47.2529 -122.4443)', 4326)),
(17, 'Downtown Suite', 'Suite', 260, 'A suite in downtown with great amenities.', 2, 0, 1, 1, 1, '15:00:00', '11:00:00', 'US', 'Georgia', 'Atlanta', 'District 17', '1414 Peachtree St', 'Suite 8', '30303', ST_PointFromText('POINT(33.7490 -84.3880)', 4326)),
(18, 'Farmhouse', 'House', 160, 'A traditional farmhouse.', 5, 2, 3, 3, 2, '16:00:00', '11:00:00', 'US', 'Iowa', 'Des Moines', 'District 18', '1515 Farm St', '', '50309', ST_PointFromText('POINT(41.5868 -93.6250)', 4326)),
(19, 'Treehouse', 'Unique', 180, 'A unique treehouse experience.', 2, 0, 1, 1, 1, '14:00:00', '10:00:00', 'US', 'North Carolina', 'Asheville', 'District 19', '1616 Forest Rd', '', '28801', ST_PointFromText('POINT(35.5951 -82.5515)', 4326)),
(20, 'City View Apartment', 'Apartment', 240, 'An apartment with a stunning city view.', 3, 1, 2, 2, 1, '15:00:00', '11:00:00', 'US', 'Illinois', 'Chicago', 'District 20', '1717 Cityview St', 'Apt 9', '60602', ST_PointFromText('POINT(41.8781 -87.6298)', 4326));

-- AMENITY table
INSERT INTO `AMENITY` (`NAME`) VALUES
('Wi-Fi'),
('Air Conditioning'),
('Heating'),
('Kitchen'),
('Washer'),
('Dryer'),
('TV'),
('Indoor Fireplace'),
('Hot Tub'),
('Pool'),
('Gym'),
('Free Parking'),
('Breakfast'),
('Laptop-friendly workspace'),
('Private entrance'),
('Smoke alarm'),
('Carbon monoxide alarm'),
('Fire extinguisher'),
('First aid kit'),
('Hair dryer');

-- ACCO_AMEN table
INSERT INTO `ACCO_AMEN` (`ACCO_ID`, `AMEN_ID`) VALUES
(1, 1),
(1, 2),
(2, 1),
(3, 3),
(3, 4),
(4, 5),
(4, 6),
(5, 7),
(5, 8),
(6, 9),
(6, 10),
(7, 11),
(7, 12),
(8, 13),
(8, 14),
(9, 15),
(9, 16),
(10, 17),
(10, 18),
(11, 19);

-- ACCO_IMAGE table
INSERT INTO `ACCO_IMAGE` (`ACCO_ID`, `URL`) VALUES
(1, 'http://example.com/images/cottage1.jpg'),
(1, 'http://example.com/images/cottage2.jpg'),
(2, 'http://example.com/images/apartment1.jpg'),
(3, 'http://example.com/images/beachhouse1.jpg'),
(3, 'http://example.com/images/beachhouse2.jpg'),
(4, 'http://example.com/images/cabin1.jpg'),
(4, 'http://example.com/images/cabin2.jpg'),
(5, 'http://example.com/images/loft1.jpg'),
(5, 'http://example.com/images/loft2.jpg'),
(6, 'http://example.com/images/home1.jpg'),
(6, 'http://example.com/images/home2.jpg'),
(7, 'http://example.com/images/bungalow1.jpg'),
(7, 'http://example.com/images/bungalow2.jpg'),
(8, 'http://example.com/images/studio1.jpg'),
(8, 'http://example.com/images/studio2.jpg'),
(9, 'http://example.com/images/villa1.jpg'),
(9, 'http://example.com/images/villa2.jpg'),
(10, 'http://example.com/images/townhouse1.jpg'),
(10, 'http://example.com/images/townhouse2.jpg');

-- RESERVATION table
INSERT INTO `RESERVATION` (`MEMBER_ID`, `CREATED_AT`, `ADULT_COUNT`, `CHILD_COUNT`, `INFANT_COUNT`, `CHECK_IN_DATE`, `CHECK_OUT_DATE`, `FINAL_PRICE`, `STATUS`) VALUES
(1, NOW(), 2, 1, 1, '2024-07-01', '2024-07-07', 700, 'Confirmed'),
(2, NOW(), 1, 0, 0, '2024-08-01', '2024-08-05', 800, 'Pending'),
(3, NOW(), 3, 2, 1, '2024-09-01', '2024-09-10', 1500, 'Confirmed'),
(4, NOW(), 2, 0, 0, '2024-10-01', '2024-10-05', 1200, 'Cancelled'),
(5, NOW(), 1, 1, 0, '2024-11-01', '2024-11-07', 900, 'Pending'),
(6, NOW(), 4, 2, 1, '2024-12-01', '2024-12-15', 2000, 'Confirmed'),
(7, NOW(), 2, 1, 1, '2024-06-01', '2024-06-07', 1000, 'Confirmed'),
(8, NOW(), 3, 0, 0, '2024-05-01', '2024-05-05', 1300, 'Pending'),
(9, NOW(), 1, 0, 0, '2024-04-01', '2024-04-07', 1100, 'Cancelled'),
(10, NOW(), 2, 1, 1, '2024-03-01', '2024-03-07', 950, 'Confirmed'),
(11, NOW(), 1, 1, 0, '2024-02-01', '2024-02-05', 850, 'Pending'),
(12, NOW(), 3, 2, 1, '2024-01-01', '2024-01-10', 1800, 'Confirmed'),
(13, NOW(), 2, 0, 0, '2023-12-01', '2023-12-05', 1250, 'Cancelled'),
(14, NOW(), 1, 1, 0, '2023-11-01', '2023-11-07', 950, 'Pending'),
(15, NOW(), 4, 2, 1, '2023-10-01', '2023-10-15', 2300, 'Confirmed'),
(16, NOW(), 2, 1, 1, '2023-09-01', '2023-09-07', 1150, 'Confirmed'),
(17, NOW(), 3, 0, 0, '2023-08-01', '2023-08-05', 1350, 'Pending'),
(18, NOW(), 1, 0, 0, '2023-07-01', '2023-07-07', 950, 'Cancelled'),
(19, NOW(), 2, 1, 1, '2023-06-01', '2023-06-07', 1050, 'Confirmed'),
(20, NOW(), 1, 1, 0, '2023-05-01', '2023-05-05', 950, 'Pending');

-- ACCO_PRODUCT table
INSERT INTO `ACCO_PRODUCT` (`ACCO_ID`, `RESERVE_DATE`, `PRICE`, `IS_RESERVED`, `CREATED_AT`, `MODIFIED_AT`) VALUES
(1, '2024-07-01', 100, false, NOW(), NULL),
(1, '2024-07-02', 100, false, NOW(), NULL),
(2, '2024-08-01', 200, false, NOW(), NULL),
(2, '2024-08-02', 200, false, NOW(), NULL),
(3, '2024-09-01', 300, false, NOW(), NULL),
(3, '2024-09-02', 300, false, NOW(), NULL),
(4, '2024-10-01', 150, false, NOW(), NULL),
(4, '2024-10-02', 150, false, NOW(), NULL),
(5, '2024-11-01', 250, false, NOW(), NULL),
(5, '2024-11-02', 250, false, NOW(), NULL),
(6, '2024-12-01', 180, false, NOW(), NULL),
(6, '2024-12-02', 180, false, NOW(), NULL),
(7, '2024-06-01', 220, false, NOW(), NULL),
(7, '2024-06-02', 220, false, NOW(), NULL),
(8, '2024-05-01', 120, false, NOW(), NULL),
(8, '2024-05-02', 120, false, NOW(), NULL),
(9, '2024-04-01', 400, false, NOW(), NULL),
(9, '2024-04-02', 400, false, NOW(), NULL),
(10, '2024-03-01', 270, false, NOW(), NULL),
(10, '2024-03-02', 270, false, NOW(), NULL);

-- RESERVATION_PRODUCT table
INSERT INTO `RESERVATION_PRODUCT` (`RESERVE_ID`, `PRODUCT_ID`) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 5),
(3, 6),
(4, 7),
(4, 8),
(5, 9),
(5, 10),
(6, 11),
(6, 12),
(7, 13),
(7, 14),
(8, 15),
(8, 16),
(9, 17),
(9, 18),
(10, 19),
(10, 20),
(2, 4);