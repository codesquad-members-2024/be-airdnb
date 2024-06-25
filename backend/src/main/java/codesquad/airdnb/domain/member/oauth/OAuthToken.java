package codesquad.airdnb.domain.member.oauth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthToken {
    private String accessToken;
    private String refreshToken;
}
