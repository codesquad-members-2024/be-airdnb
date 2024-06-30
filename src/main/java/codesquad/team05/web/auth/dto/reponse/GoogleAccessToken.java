package codesquad.team05.web.auth.dto.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GoogleAccessToken {

    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("expires_in")
    private final int expiresIn;

    @JsonProperty("scope")
    private final String scope;

    @JsonProperty("token_type")
    private final String tokenType;

    @JsonProperty("id_token")
    private final String idToken;
}
