package codesquad.airdnb.domain.member.dto.response;

public record AuthResponse(
        String nickname,

        String accessToken,

        String refreshToken,

        Long memberId
) {
}
