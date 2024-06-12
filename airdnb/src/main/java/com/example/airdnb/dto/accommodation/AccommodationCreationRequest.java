package com.example.airdnb.dto.accommodation;

import java.util.Set;

public record AccommodationCreationRequest(
        AddressRequest address, // String country, String state, String city, String detail
        Long userId,
        String name,
        String description,
        Long pricePerNight,
        Integer maxGuests,
        Set<String> imageUrls) {
}