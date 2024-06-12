package team10.airdnb.reservation.dto;

import team10.airdnb.member.entity.Member;

public record MemberInformationDto(
        String memberId,
        String memberName
) {
    public static MemberInformationDto from(Member member) {
        return new MemberInformationDto(
                member.getId(),
                member.getMemberName()
        );
    }
}
