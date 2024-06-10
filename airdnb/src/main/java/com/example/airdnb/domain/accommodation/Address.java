package com.example.airdnb.domain.accommodation;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String country;
    private String state;
    private String city;
    private String address;
    private Point location;

    @Builder
    public Address(String country, String state, String city, String address, Point location) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.address = address;
        this.location = location;
    }
}
