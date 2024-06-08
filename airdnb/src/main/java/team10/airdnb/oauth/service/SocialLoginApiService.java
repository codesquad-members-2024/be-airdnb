package team10.airdnb.oauth.service;

import team10.airdnb.oauth.model.OAuthAttributes;

public interface SocialLoginApiService {

    OAuthAttributes getUserInfo(String accessToken);

}
