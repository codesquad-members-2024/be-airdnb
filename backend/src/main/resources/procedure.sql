-- 1년치 예약 생성 프로시저
DELIMITER $$

CREATE PROCEDURE createYearlyProduct(IN accoId BIGINT, IN price BIGINT)
BEGIN
     DECLARE startDate DATE;
     DECLARE endDate DATE;

     SET startDate = CURDATE() + INTERVAL 1 DAY;
     SET endDate = CURDATE() + INTERVAL 1 YEAR;

     WHILE startDate <= endDate DO
         INSERT INTO ACCO_PRODUCT (ACCO_ID, RESERVE_DATE, PRICE, IS_RESERVED, CREATED_AT)
         VALUES (accoId, startDate, price, FALSE, NOW());

         SET startDate = startDate + INTERVAL 1 DAY;
     END WHILE;
 END$$
DELIMITER ;

-- 매일 새벽에 돌아가며 1년 후 날짜 숙소상품을 생성하는 프로시저

DELIMITER $$

CREATE PROCEDURE createNextProductForAllAcco()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE accoId BIGINT;
    DECLARE reserveDate DATE;
    DECLARE price BIGINT;
    DECLARE duplicate_key_exception CONDITION FOR 1062;
    DECLARE cur CURSOR FOR SELECT id, base_price_per_night FROM accommodation;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    DECLARE CONTINUE HANDLER FOR duplicate_key_exception BEGIN END;

    -- reserveDate를 1년 후로 설정
    SET reserveDate = DATE_ADD(CURDATE(), INTERVAL 1 YEAR);

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO accoId, price;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- 하루치 예약 추가
        INSERT INTO ACCO_PRODUCT (ACCO_ID, RESERVE_DATE, PRICE, IS_RESERVED, CREATED_AT)
        VALUES (accoId, reserveDate, price, FALSE, NOW());

    END LOOP;

    CLOSE cur;
END$$

DELIMITER ;
