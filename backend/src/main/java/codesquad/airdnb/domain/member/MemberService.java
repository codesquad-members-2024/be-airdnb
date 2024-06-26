package codesquad.airdnb.domain.member;

import codesquad.airdnb.domain.member.dto.request.LoginRequest;
import codesquad.airdnb.domain.member.dto.request.RegisterRequest;
import codesquad.airdnb.domain.member.dto.response.AuthResponse;
import codesquad.airdnb.domain.member.oauth.*;
import codesquad.airdnb.domain.member.oauth.kakao.KaKaoLoginParam;
import codesquad.airdnb.global.security.JwtTokenProvider;
import codesquad.airdnb.domain.member.util.RandomStringUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final RequestOAuthInfoService requestOAuthInfoService;

    // Logout
    private final OAuthRepository oauthRepository;

    public AuthResponse register(RegisterRequest request, LoginType loginType) {
        if(idDuplicatedCheck(request.accountName())){
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        // TODO: 비밀번호 encryption
        Member member = request.toEntity(loginType);
        String accessToken = jwtTokenProvider.createAccessToken(member.getAccountName());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getAccountName());
        member.updateRefreshToken(refreshToken);
        memberRepository.save(member);

        return new AuthResponse(member.getNickname(), accessToken, refreshToken, member.getId());
    }

    public boolean idDuplicatedCheck(String accountName) {
        return memberRepository.existsMemberByAccountName(accountName);
    }

    public AuthResponse login(LoginRequest request) {
        Member member = memberRepository.findMemberByAccountName(request.accountName());
        if(member.isPasswordInvalid(request.password())){
            throw new IllegalArgumentException();
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getAccountName());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getAccountName());
        member.updateRefreshToken(refreshToken);
        memberRepository.updateRefreshToken(member.getAccountName(), refreshToken);

        return new AuthResponse(member.getNickname(), accessToken, refreshToken, member.getId());
    }

    @Transactional
    public void logout(String token) {
        Claims claims = jwtTokenProvider.validateToken(token);
        String accountName = claims.getSubject();
        Member member = memberRepository.findMemberByAccountName(accountName);

        if (member.getLoginType().equals(LoginType.OAUTH)) {
            // 멤버의 OauthType을 매개로 OAuth
            OAuth oauth = oauthRepository.findOauthByMemberId(member.getId());
            ResponseEntity<String> response = requestOAuthInfoService.requestExpireOAuthTokens(oauth.getAccessToken(), oauth.getProvider());

            // 정상동작 시 Oauth 관련 토큰 제거
            if (response.getStatusCode().is2xxSuccessful()) {
                oauth.expireAllToken();
                oauthRepository.save(oauth);
            }
            else {
                // 로그아웃 api 요청에서 뭔가 문제 발생. 2xx이 아닌 응답이 날아오는 경우.
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        member.expireRefreshToken();
        memberRepository.updateRefreshToken(member.getAccountName(), member.getRefreshToken());
    }
  
    public Auth refresh(String token) {
        Claims claims = jwtTokenProvider.validateToken(token);
        String accountName = claims.getSubject();
        Member member = memberRepository.findMemberByAccountName(accountName);

        // refresh token 검증
        // TODO: 예외처리 로직 개선
        String refreshToken = member.getRefreshToken();
        if(!refreshToken.equals(token)) {
            throw new RuntimeException();
        }

         // TODO: OAUTH 로그인의 Refresh 로직


        // access token 새로 발급
        String newAccessToken = jwtTokenProvider.createAccessToken(member.getAccountName());

        // refresh token도 새로 발급해야 하는지 체크
        if(jwtTokenProvider.refreshTokenExpired(claims)) {
            refreshToken = jwtTokenProvider.createRefreshToken(member.getAccountName());
            member.updateRefreshToken(refreshToken);
            memberRepository.updateRefreshToken(member.getAccountName(), refreshToken);
        }

        return new Auth(newAccessToken, refreshToken);
    }

    @Transactional
    public AuthResponse authenticateByKakao(String authCode) {
        OAuthLoginParams oAuthLoginParams = new KaKaoLoginParam(authCode);
        OAuthUserInfoWithToken oAuthUserInfoWithToken = requestOAuthInfoService.requestWithToken(oAuthLoginParams);

        Member member = memberRepository.findMemberByAccountName(oAuthUserInfoWithToken.getEmail());
        if(member == null) {
            RegisterRequest registerRequest = new RegisterRequest(oAuthUserInfoWithToken.getEmail(),
                    RandomStringUtil.generateRandomPassword(),
                    oAuthUserInfoWithToken.getNickname());
            AuthResponse authResponse = register(registerRequest, LoginType.OAUTH);
            member = memberRepository.findById(authResponse.memberId())
                    .orElseThrow(() -> new NoSuchElementException("해당하는 Id를 갖는 회원이 없습니다."));
        }

        // JWT 저장
        String accessToken = jwtTokenProvider.createAccessToken(member.getAccountName());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getAccountName());
        member.updateRefreshToken(refreshToken);
        memberRepository.updateRefreshToken(member.getAccountName(), refreshToken);

        // Oauth 토큰 저장
        OAuth oauth = oauthRepository.findOauthByMemberId(member.getId());
        if (oauth == null) {
            oauth = new OAuth(member, oAuthUserInfoWithToken, OAuthProvider.KAKAO);
        }
        oauthRepository.save(oauth);

        return new AuthResponse(member.getNickname(), accessToken, refreshToken, member.getId());
    }
}

