package codesquad.airdnb.domain.member.request;

import codesquad.airdnb.domain.member.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberCreateRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String loginPassword;

    @NotBlank
    private String nickname;

    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .loginPassword(loginPassword)
                .nickname(nickname)
                .build();
    }
}
