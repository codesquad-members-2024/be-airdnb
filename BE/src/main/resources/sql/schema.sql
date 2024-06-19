DROP TABLE IF EXISTS USER_LIKES_PRODUCT;
DROP TABLE IF EXISTS REVIEW_REPLY;
DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS BOOKING;
DROP TABLE IF EXISTS ACCOMMODATION_PICTURE;
DROP TABLE IF EXISTS ACCOMMODATION;
DROP TABLE IF EXISTS REVIEW;
DROP TABLE IF EXISTS PAYMENT;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS DISCOUNT_POLICY;

CREATE TABLE DISCOUNT_POLICY
(
    id             bigint auto_increment primary key,
    description    varchar(255) null,
    policyBeanName varchar(255) not null,

    createdAt      datetime(6)  null,
    lastModifiedAt datetime(6)  null
);

CREATE TABLE PAYMENT
(
    id               bigint auto_increment primary key,
    accommodationFee int                          null,
    serviceFee       int                          null,
    status           enum ('COMPLETE', 'FENDING') null,
    totalPrice       int                          not null,

    createdAt        datetime(6)                  null,
    lastModifiedAt   datetime(6)                  null
);

CREATE TABLE REVIEW
(
    id             bigint auto_increment primary key,
    content        varchar(255) null,
    rating         int          not null,

    createdAt      datetime(6)  null,
    lastModifiedAt datetime(6)  null
);

CREATE TABLE USERS
(
    id             bigint auto_increment primary key,
    email          varchar(255)                   null,
    name           varchar(255)                   null,
    picture        varchar(255)                   null,
    registrationId varchar(255)                   null,
    role           enum ('ADMIN', 'HOST', 'USER') null,

    createdAt      datetime(6)                    null,
    lastModifiedAt datetime(6)                    null
);

CREATE TABLE ACCOMMODATION
(
    id              bigint auto_increment primary key,
    address         varchar(255) null,
    point           geometry     null,
    zipCode         int          null,
    basePricePerDay int          not null,
    description     varchar(255) null,
    name            varchar(255) null,
    bathroomCount   int          null,
    bedCount        int          null,
    bedroomCount    int          null,
    isPrivate       bit          null,
    maxOccupancy    int          null,
    type            tinyint      null,
    host_id         bigint       null,

    createdAt       datetime(6)  null,
    lastModifiedAt  datetime(6)  null,

    constraint accommodation_host_id foreign key (host_id) references USERS (id)
);

CREATE TABLE ACCOMMODATION_PICTURE
(
    id               bigint auto_increment primary key,
    url              varchar(255) null,
    accommodation_id bigint       null,
    constraint picture_accommodation_id foreign key (accommodation_id) references ACCOMMODATION (id)
);

CREATE TABLE BOOKING
(
    id             bigint auto_increment primary key,
    checkin        date                                                null,
    checkout       date                                                null,
    headCount      int                                                 null,
    status         enum ('CANCEL', 'COMPLETE', 'CONFIRM', 'REQUESTED') null,
    booker_id      bigint                                              null,
    host_id        bigint                                              null,
    payment_id     bigint                                              null,
    review_id      bigint                                              null,

    createdAt      datetime(6)                                         null,
    lastModifiedAt datetime(6)                                         null,

    constraint unique_review_id unique (review_id),
    constraint unique_payment_id unique (payment_id),
    constraint booking_host_id foreign key (host_id) references USERS (id),
    constraint booker_id foreign key (booker_id) references USERS (id),
    constraint payment_id foreign key (payment_id) references PAYMENT (id),
    constraint booking_review_id foreign key (review_id) references REVIEW (id)
);

CREATE TABLE PRODUCT
(
    id               bigint auto_increment primary key,
    date             date                             null,
    price            int                              null,
    status           enum ('BOOKED', 'CLOSE', 'OPEN') null,
    accommodation_id bigint                           null,
    booking_id       bigint                           null,

    createdAt        datetime(6)                      null,
    lastModifiedAt   datetime(6)                      null,

    constraint booking_id foreign key (booking_id) references BOOKING (id),
    constraint product_accommodation_id foreign key (accommodation_id) references ACCOMMODATION (id)
);

CREATE TABLE REVIEW_REPLY
(
    id        bigint auto_increment primary key,
    content   varchar(255) null,
    review_id bigint       null,
    writer_id bigint       null,

    constraint reply_review_id foreign key (review_id) references REVIEW (id),
    constraint writer_id foreign key (writer_id) references USERS (id)
);

CREATE TABLE USER_LIKES_PRODUCT
(
    id             bigint auto_increment
        primary key,
    product_id     bigint      null,
    user_id        bigint      null,

    createdAt      datetime(6) null,
    lastModifiedAt datetime(6) null,

    constraint product_id foreign key (product_id) references PRODUCT (id),
    constraint user_id foreign key (user_id) references USERS (id)
);

ALTER TABLE ACCOMMODATION MODIFY point POINT NOT NULL SRID 4326;
CREATE SPATIAL INDEX point_spatial_index ON ACCOMMODATION(point);

insert into DISCOUNT_POLICY values (null, "주단위할인", 'weeklyRateDiscountPolicy', null, null);
