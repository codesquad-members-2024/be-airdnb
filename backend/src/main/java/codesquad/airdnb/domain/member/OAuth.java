package codesquad.airdnb.domain.member;


import codesquad.airdnb.domain.member.oauth.OAuthProvider;
import codesquad.airdnb.domain.member.oauth.OAuthUserInfoWithToken;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OAUTH")
public class OAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "ACCESS_TOKEN")
    private String accessToken;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER")
    private OAuthProvider provider;

    public OAuth(Member member, OAuthUserInfoWithToken oAuthUserInfoWithToken, OAuthProvider provider) {
        this.member = member;
        this.accessToken = oAuthUserInfoWithToken.getAccessToken();
        this.refreshToken = oAuthUserInfoWithToken.getRefreshToken();
        this.provider = provider;
    }

    public void expireAllToken() {
        this.accessToken = "";
        this.refreshToken = "";
    }
}
