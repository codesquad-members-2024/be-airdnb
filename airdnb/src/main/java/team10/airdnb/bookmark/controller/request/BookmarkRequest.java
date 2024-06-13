package team10.airdnb.bookmark.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.bookmark.entity.Bookmark;
import team10.airdnb.member.entity.Member;

public record BookmarkRequest(
        @NotBlank
        String memberId,

        @NotNull
        Long accommodationId
) {
    public Bookmark toEntity(Member member, Accommodation accommodation) {
        return Bookmark.builder()
                .member(member)
                .accommodation(accommodation)
                .build();
    }
}
