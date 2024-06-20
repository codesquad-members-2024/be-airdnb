package com.example.airdnb.dto.accommodation;

public record AddressRequest(String country, String state, String city, String detail, Double latitude, Double longitude) {
}
