package com.airdnb.stay.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StayCreateRequest {
    @NotBlank
    private final String name;
    @NotNull
    private final Integer price;
    @NotNull
    private final LocalDateTime startDate;
    @NotNull
    private final LocalDateTime endDate;
    @NotBlank
    private final String hostId;
    @NotNull
    private final Long imageId;
    private final List<Long> tagIds;
    @NotNull
    private final Integer maxGuests;
    @NotBlank
    private final String location;
    @NotBlank
    private final String type;
}
