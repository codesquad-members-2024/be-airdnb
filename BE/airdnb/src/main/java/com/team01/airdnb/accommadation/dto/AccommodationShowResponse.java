package com.team01.airdnb.accommadation.dto;

import com.team01.airdnb.amenity.Amenity;
import com.team01.airdnb.comment.Comment;
import com.team01.airdnb.image.Image;
import com.team01.airdnb.reservation.Reservation;
import com.team01.airdnb.user.User;
import java.util.List;
import lombok.Builder;

@Builder
public record AccommodationShowResponse(
    Long id,
    String title,
    String content,
    Long price,
    Integer discountRate,
    String address,
    Double latitude,
    Double longitude,
    Integer commentsNum,
    User user,
    List<Comment> comments,
    List<Image> images,
    List<Reservation> reservations,
    Amenity amenity
) {

}
