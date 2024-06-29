package codesquad.team05.web.auth.dto.reponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthResponse {

    private final String accessToken;
    private final String refreshToken;
}
