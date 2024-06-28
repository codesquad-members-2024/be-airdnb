package team10.airdnb.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team10.airdnb.member.constant.MemberType;
import team10.airdnb.oauth.DateTimeUtils;
import team10.airdnb.jwt.dto.JwtTokenDto;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    private String profile;

    private String email;

    private String memberName;

    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    @Builder
    public Member(String email,
                  String id,
                  String memberName,
                  MemberType memberType,
                  String password,
                  String profile
                   ) {
        this.email = email;
        this.id = id;
        this.memberName = memberName;
        this.memberType = memberType;
        this.password = password;
        this.profile = profile;
    }


    public void updateRefreshToken(JwtTokenDto jwtTokenDto) {
        this.refreshToken = jwtTokenDto.getRefreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtTokenDto.getRefreshTokenExpireTime());
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }
}
