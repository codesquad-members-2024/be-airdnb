package team07.airbnb.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegistrationID {
    GOOGLE("google"),
    GITHUB("github");

    private final String name;
}
