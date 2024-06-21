package codesquad.airdnb.domain.member;

import codesquad.airdnb.domain.member.dto.request.LoginRequest;
import codesquad.airdnb.domain.member.dto.request.SignUpRequest;
import codesquad.airdnb.domain.member.dto.response.AuthResponse;
import codesquad.airdnb.global.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponse signUp(SignUpRequest request) {
        if(idDuplicatedCheck(request.loginId())){
            throw new IllegalArgumentException();
        }

        // TODO: 비밀번호 encryption
        Member member = request.toEntity();
        String accessToken = jwtTokenProvider.createAccessToken(member.getLoginId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getLoginId());
        member.updateRefreshToken(refreshToken);
        memberRepository.save(member);

        return new AuthResponse(member.getNickname(), accessToken, refreshToken);
    }

    public boolean idDuplicatedCheck(String loginId) {
        return memberRepository.existsMemberByLoginId(loginId);
    }

    public AuthResponse login(LoginRequest request) {
        Member member = memberRepository.findMemberByLoginId(request.loginId());
        if(member.isPasswordInvalid(request.loginPassword())){
            throw new IllegalArgumentException();
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getLoginId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getLoginId());
        member.updateRefreshToken(refreshToken);
        memberRepository.updateRefreshToken(member.getLoginId(), refreshToken);

        return new AuthResponse(member.getNickname(), accessToken, refreshToken);
    }

    @Transactional
    public void logout(String token) {
        Claims claims = jwtTokenProvider.validateToken(token);
        String loginId = claims.getSubject();

        Member member = memberRepository.findMemberByLoginId(loginId);
        member.expireRefreshToken();
        memberRepository.updateRefreshToken(member.getLoginId(), member.getRefreshToken());
    }

    public Auth refresh(String token) {
        Claims claims = jwtTokenProvider.validateToken(token);
        String loginId = claims.getSubject();
        Member member = memberRepository.findMemberByLoginId(loginId);

        // refresh token 검증
        String refreshToken = member.getRefreshToken();
        if(refreshToken == null || !refreshToken.equals(token)) {
            throw new RuntimeException();
        }

        // access token 새로 발급
        String newAccessToken = jwtTokenProvider.createAccessToken(member.getLoginId());

        // refresh token도 새로 발급해야 하는지 체크
        if(jwtTokenProvider.refreshTokenExpired(claims)) {
            refreshToken = jwtTokenProvider.createRefreshToken(member.getLoginId());
            member.updateRefreshToken(refreshToken);
            memberRepository.updateRefreshToken(member.getLoginId(), refreshToken);
        }

        return new Auth(newAccessToken, refreshToken);
    }
}

