package com.example.airdnb.domain.search;

import lombok.Builder;

public record SearchCondition(StayPeriod stayPeriod, PriceRange priceRange, Integer guestCount) {
    @Builder
    public SearchCondition {
    }
}
