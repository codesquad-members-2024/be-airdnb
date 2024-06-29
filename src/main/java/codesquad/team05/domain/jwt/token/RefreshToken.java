package codesquad.team05.domain.jwt.token;

import lombok.Getter;


@Getter
public class RefreshToken {

    private String token;
    private String userId;

    public RefreshToken(String userId, String token) {
        this.token = token;
        this.userId = userId;
    }
}
