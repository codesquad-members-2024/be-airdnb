package com.team01.airdnb.accommadation.dto;

import com.team01.airdnb.amenity.dto.AmenityShowResponse;
import com.team01.airdnb.comment.dto.CommentShowResponse;
import com.team01.airdnb.image.dto.ImageListResponse;
import com.team01.airdnb.user.dto.UserHostResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record AccommodationDetailResponse(
    String title,
    String content,
    Long price,
    Integer discountRate,
    String address,
    Double latitude,
    Double longitude,
    Integer commentsNum,
    Integer maxAdults,
    Integer maxChildren,
    Integer maxInfants,
    Integer maxPets,
    List<ImageListResponse> images,
    List<CommentShowResponse> comment,
    UserHostResponse host,
    AmenityShowResponse amenity,
    Boolean wishListFlag,
    Double score
) {

}
