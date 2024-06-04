package team10.airdnb.Member.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team10.airdnb.Member.constant.MemberType;
import team10.airdnb.Member.constant.Role;
import team10.airdnb.oauth.DateTimeUtils;
import team10.airdnb.oauth.jwt.dto.JwtTokenDto;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Member {

    @Id
    private String id;

    private String password;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    private String profile;

    private String email;

    private String memberName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    @Builder
    public Member(String email,
                  String id,
                  String memberName,
                  MemberType memberType,
                  String password,
                  String profile,
                  String refreshToken,
                  Role role,
                  LocalDateTime tokenExpirationTime) {
        this.email = email;
        this.id = id;
        this.memberName = memberName;
        this.memberType = memberType;
        this.password = password;
        this.profile = profile;
        this.refreshToken = refreshToken;
        this.role = role;
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public void updateRefreshToken(JwtTokenDto jwtTokenDto) {
        this.refreshToken = jwtTokenDto.getRefreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtTokenDto.getRefreshTokenExpireTime());
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }
}
