package team10.airdnb.bookmark.controller.response;

import team10.airdnb.accommodation.dto.AccommodationInformationDto;
import team10.airdnb.bookmark.entity.Bookmark;
import team10.airdnb.member.dto.MemberInformationDto;

public record BookmarkSummaryResponse(
        long bookmarkId,
        MemberInformationDto memberInformation,
        AccommodationInformationDto accommodationInformation
) {
    public static BookmarkSummaryResponse from(Bookmark bookmark) {
        return new BookmarkSummaryResponse(
                bookmark.getId(),
                MemberInformationDto.from(bookmark.getMember()),
                AccommodationInformationDto.from(bookmark.getAccommodation())
        );
    }
}
