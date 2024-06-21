package codesquad.airdnb.domain.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Length(min = 4, max = 50)
    @Column(name = "LOGIN_ID")
    private String loginId;

    @NotBlank
    @Length(min = 4, max = 20)
    @Column(name = "LOGIN_PASSWORD")
    private String loginPassword;

    @NotBlank
    @Length(min = 4, max = 50)
    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Column(name = "REGISTERED_BY_OAUTH")
    private boolean registeredByOauth;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void expireRefreshToken() {
        this.refreshToken = null;
    }

    public boolean isPasswordInvalid(String passwordInput) {
        return !loginPassword.equals(passwordInput);
    }
}
