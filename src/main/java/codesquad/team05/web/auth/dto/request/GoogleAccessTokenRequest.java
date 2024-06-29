package codesquad.team05.web.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GoogleAccessTokenRequest {

    @JsonProperty("code")
    private final String code;

    @JsonProperty("client_id")
    private final String clientId;

    @JsonProperty("client_secret")
    private final String clientSecret;

    @JsonProperty("redirect_uri")
    private final String redirectUri;

    @JsonProperty("grant_type")
    private final String grantType;

}
