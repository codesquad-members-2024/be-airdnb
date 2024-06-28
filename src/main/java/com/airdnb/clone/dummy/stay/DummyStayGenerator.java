package com.airdnb.clone.dummy.stay;

import static com.airdnb.clone.util.PointUtil.createPoint;

import com.airdnb.clone.domain.member.entity.Member;
import com.airdnb.clone.domain.stay.entity.RoomInformation;
import com.airdnb.clone.domain.stay.entity.Stay;
import com.airdnb.clone.domain.stay.entity.Stay.Status;
import com.airdnb.clone.domain.stay.entity.Stay.StayBuilder;
import com.airdnb.clone.domain.stay.entity.StayFee;

public class DummyStayGenerator {

    public static Stay generateKorea(Member host) {
        return buildStayBeforeSetPoint(host)
                .point(createPoint(DummyLatitudeGenerator.generate(), DummyLongitudeGenerator.generate()))
                .build();
    }

    public static Stay generateBusan(Member host) {
        return buildStayBeforeSetPoint(host)
                .point(createPoint(DummyLatitudeGenerator.generateBusan(), DummyLongitudeGenerator.generateBusan()))
                .build();
    }

    public static Stay generateJeju(Member host) {
        return buildStayBeforeSetPoint(host)
                .point(createPoint(DummyLatitudeGenerator.generateJeju(), DummyLongitudeGenerator.generateJeju()))
                .build();
    }

    public static Stay generateGangwon(Member host) {
        return buildStayBeforeSetPoint(host)
                .point(createPoint(DummyLatitudeGenerator.generateGangwon(), DummyLongitudeGenerator.generateGangwon()))
                .build();
    }

    private static StayBuilder buildStayBeforeSetPoint(Member host) {
        return Stay.builder()
                .host(host)
                .alias(DummyAliasGenerator.generate())
                .checkInTime(DummyCheckInGenerator.generate())
                .checkOutTime(DummyCheckoutGenerator.generate())
                .description(DummyDescriptionGenerator.generate())
                .fee(StayFee.builder()
                        .perNight(DummyStayFeeGenerator.generatePerNightRate())
                        .cleaningFee(DummyStayFeeGenerator.generateCleaningFee())
                        .build())
                .roomInfo(RoomInformation.builder()
                        .bedCount(DummyFacilityGenerator.generateBedCount())
                        .bathCount(DummyFacilityGenerator.generateBathRoomCount())
                        .bedroomCount(DummyFacilityGenerator.generateBedRoomCount())
                        .guestCount(DummyTravelerGenerator.generate())
                        .build())
                .type(DummyTypeGenerator.generate())
                .status(Status.OPEN);
    }
}
