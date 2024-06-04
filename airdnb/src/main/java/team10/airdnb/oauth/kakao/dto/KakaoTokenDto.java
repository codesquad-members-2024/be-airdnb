package team10.airdnb.oauth.kakao.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class KakaoTokenDto {

    @Builder
    @Getter
    public static class Request {
        private String grant_type;
        private String client_id;
        private String redirect_uri;
        private String code;
        private String client_secret;
    }

    @ToString
    @Builder @Getter
    public static class Response {
        private String token_type;
        private String access_token;
        private Integer expires_in;
        private String refresh_token;
        private Integer refresh_token_expires_in;
        private String scope;
    }

}

//토큰 형식
// kakao token : KakaoTokenDto.Response(token_type=bearer,
// access_token= HhybJ8tX1vuiA51H9u8wCCoQUZmmx1P9AAAAAQo9cxcAAAGPruY_WRamEcnPBcmr,
// expires_in= 21599,
// refresh_token= qgReQbUT30-PwhJ_LkMYsRBn82QY0KosAAAAAgo9cxcAAAGPruY_VhamEcnPBcmr,
// refresh_token_expires_in= 5183999,
// scope=profile_image profile_nickname)
