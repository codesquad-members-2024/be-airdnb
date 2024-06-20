package team10.airdnb.member.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import team10.airdnb.member.entity.Member;

public record MemberCreationRequest(
        @Email
        String email,
        @NotBlank
        String name,
        @NotBlank
        String password
) {
        public Member toEntity(){
                return Member.builder()
                        .id(email)
                        .email(email)
                        .memberName(name)
                        .password(password)
                        .profile("https://airdnb-storage.s3.ap-northeast-2.amazonaws.com/profile/profile.jpg")
                        .build();
        }
}
