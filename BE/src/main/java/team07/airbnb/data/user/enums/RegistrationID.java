package team07.airbnb.data.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegistrationID {
    GOOGLE("google"),
    GITHUB("github");

    private final String name;
}
