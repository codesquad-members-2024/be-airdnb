package com.team01.airdnb.accommadation.dto;

import com.querydsl.core.annotations.QueryProjection;

public record AccommodationSearchResponse(
    Long id,
    String title,
    Long price,
    String address,
    Integer commentsNum,
    Integer MenCount,
    String image
) {
  @QueryProjection
  public AccommodationSearchResponse {}
}
