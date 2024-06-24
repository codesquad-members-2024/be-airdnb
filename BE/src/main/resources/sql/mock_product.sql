-- Tx : Manual
DELIMITER $$

DROP PROCEDURE IF EXISTS InsertProducts;
CREATE PROCEDURE InsertProducts(
    IN accommodation_id BIGINT,
    IN startDate DATE,
    IN endDate DATE
)
BEGIN
    DECLARE currentDate DATE;
    DECLARE randomPrice INT;

    SET currentDate = startDate;

    WHILE currentDate <= endDate DO
        SET randomPrice = FLOOR(100000 + RAND() * (1000000 - 100000));
        INSERT INTO PRODUCT (date, price, status, accommodation_id, createdAt, lastModifiedAt)
        VALUES (currentDate, randomPrice, 'OPEN', accommodation_id, NOW(6), NOW(6));
        SET currentDate = DATE_ADD(currentDate, INTERVAL 1 DAY);
    END WHILE;
END $$

-- 모든 숙소에 대한 상품 등록
DROP PROCEDURE IF EXISTS InsertProductsForAllAccommodations $$

-- Create the InsertProductsForAllAccommodations procedure
CREATE PROCEDURE InsertProductsForAllAccommodations(
    IN startDate DATE,
    IN endDate DATE
)
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE accommodation_id BIGINT;
    DECLARE cur CURSOR FOR SELECT id FROM ACCOMMODATION;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO accommodation_id;
        IF done THEN
            LEAVE read_loop;
        END IF;
        CALL InsertProducts(accommodation_id, startDate, endDate);
    END LOOP;

    CLOSE cur;
END $$

DELIMITER ;

# CALL InsertProducts(51, '2024-07-01', '2024-12-31');
CALL InsertProductsForAllAccommodations('2024-07-01', '2024-12-31');
