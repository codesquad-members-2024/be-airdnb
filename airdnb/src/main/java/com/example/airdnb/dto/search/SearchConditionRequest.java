package com.example.airdnb.dto.search;

import com.example.airdnb.domain.search.PriceRange;
import com.example.airdnb.domain.search.SearchCondition;
import com.example.airdnb.domain.search.SearchCondition.SearchConditionBuilder;
import com.example.airdnb.domain.search.StayPeriod;

public class SearchConditionRequest {
    private final StayPeriod stayPeriod;
    private final PriceRange priceRange;
    private final Integer guestCount;

    public SearchConditionRequest(StayPeriod stayPeriod, PriceRange priceRange, Integer guestCount) {
        this.stayPeriod = stayPeriod;
        this.priceRange = priceRange;
        this.guestCount = guestCount;
    }

    public SearchCondition toEntity() {
        SearchConditionBuilder builder = SearchCondition.builder();

        if (this.stayPeriod != null) {
            builder.stayPeriod(this.stayPeriod);
        }
        if (this.priceRange != null) {
            builder.priceRange(this.priceRange);
        }
        if (this.guestCount != null) {
            builder.guestCount(this.guestCount);
        }

        return builder.build();
    }
}
