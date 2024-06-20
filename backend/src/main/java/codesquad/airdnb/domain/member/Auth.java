package codesquad.airdnb.domain.member;

public record Auth(
        String accessToken,

        String refreshToken
) {
}
