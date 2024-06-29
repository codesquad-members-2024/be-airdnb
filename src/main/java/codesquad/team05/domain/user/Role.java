package codesquad.team05.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    HOST("ROLE_HOST"),
    USER("ROLE_USER"),
    GUEST("ROLE_GUEST");

    private final String name;



}
