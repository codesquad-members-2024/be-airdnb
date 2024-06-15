package com.example.airdnb.domain.search;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PriceRange {

    private final Double minPrice;

    private final Double maxPrice;

    private PriceRange(Double minPrice, Double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public static PriceRange of(Double minPrice, Double maxPrice) {
        return new PriceRange(minPrice, maxPrice);
    }
}
