package team10.airdnb.oauth.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team10.airdnb.Member.constant.MemberType;
import team10.airdnb.Member.entity.Member;
import team10.airdnb.Member.service.MemberService;
import team10.airdnb.oauth.jwt.dto.JwtTokenDto;
import team10.airdnb.oauth.jwt.service.TokenManager;
import team10.airdnb.oauth.login.dto.OauthLoginDto;
import team10.airdnb.oauth.model.OAuthAttributes;
import team10.airdnb.oauth.service.SocialLoginApiService;
import team10.airdnb.oauth.service.SocialLoginApiServiceFactory;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OauthLoginService {

    private final MemberService memberService;
    private final TokenManager tokenManager;

    public OauthLoginDto.Response oauthLogin(String accessToken, MemberType memberType) {
        SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(memberType);
        OAuthAttributes userInfo = socialLoginApiService.getUserInfo(accessToken);
        log.info("userInfo : {}",  userInfo);

        JwtTokenDto jwtTokenDto;
        Optional<Member> optionalMember = memberService.findMemberByEmail(userInfo.getEmail());
        Member oauthMember;
        if(optionalMember.isEmpty()) { // 신규 회원 가입
            oauthMember = userInfo.toMemberEntity(memberType);
            oauthMember = memberService.registerMember(oauthMember);
        } else { // 기존 회원일 경우
            oauthMember = optionalMember.get();
        }
        // 토큰 생성
        jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.getId());
        oauthMember.updateRefreshToken(jwtTokenDto);

        return OauthLoginDto.Response.of(jwtTokenDto);
    }

}
