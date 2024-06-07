package com.airdnb.stay.dto;

import com.airdnb.tag.dto.TagQueryResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StayDetailQueryResponse {
    private final Long id;
    private final String name;
    private final Integer price;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Integer maxGuests;
    private final String address;
    private final Double latitude;
    private final Double longitude;
    private final String hostName;
    private final String imageUrl;
    private final String type;
    private final Double rating;
    private final List<TagQueryResponse> tags;
    private final List<StayCommentQueryResponse> comments;
}
