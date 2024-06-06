package com.airdnb.tag.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class TagQueryResponse {
    private final Long id;
    private final String name;
}
