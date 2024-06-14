package team10.airdnb.oauth.kakao.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team10.airdnb.member.constant.MemberType;
import team10.airdnb.oauth.kakao.dto.KakaoTokenDto;
import team10.airdnb.oauth.kakao.client.KakaoTokenClient;
import team10.airdnb.oauth.login.dto.OauthLoginDto;
import team10.airdnb.oauth.login.service.OauthLoginService;

//@Controller
@RestController
@RequiredArgsConstructor
public class KakaoTokenController {

    private final KakaoTokenClient kakaoTokenClient;
    private final OauthLoginService oauthLoginService;

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @GetMapping("/oauth/kakao/callback")
    public OauthLoginDto.Response loginCallback(@RequestParam String code) {
        // 1. 카카오 인증서버가 콜백으로 code를 준다.(내가 받음)
        String contentType = "application/x-www-form-urlencoded;charset=utf-8";
        KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .grant_type("authorization_code")
                .code(code)
                .redirect_uri("http://localhost:8080/oauth/kakao/callback")
                .build();

        // 2. 카카오 인증 서버로 방금 받은 code를 보내서 해당 유저의 정보를 받을 수 잇도록 카카오 전용 accessToken을 받는다.
        KakaoTokenDto.Response kakaoToken = kakaoTokenClient.requestKakaoToken(contentType, kakaoTokenRequestDto);


        // 3. 카카오 유저 서버로 위에서 받은 accessToken을 보내면 유저 서버에서 정보를 나한테 준다.(내가 받음)
        OauthLoginDto.Response response = oauthLoginService.oauthLogin(kakaoToken.getAccess_token(), MemberType.KAKAO);
        // 4. 그 정보 가지고 회원가입 + 우리의 accessToken을 프론트에 반환 -> 위에 3번에 포함

        return response;
//      return "kakao token : " + kakaoToken;
    }

}
