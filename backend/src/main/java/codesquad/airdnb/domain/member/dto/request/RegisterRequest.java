package codesquad.airdnb.domain.member.dto.request;

import codesquad.airdnb.domain.member.LoginType;
import codesquad.airdnb.domain.member.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank
        @Size(min = 4, max = 50)
        String accountName,

        @NotBlank
        @Size(min = 4, max = 20)
        String password,

        @NotBlank
        @Size(min = 1, max = 200)
        String nickname
) {

    public Member toEntity(LoginType loginType) {
        return Member.builder()
                .accountName(accountName)
                .password(password)
                .nickname(nickname)
                .loginType(loginType)
                .build();
    }
}
