package codesquad.airdnb.domain.member.oauth;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    // 더 추가되면 ENUM으로 관리?
    OAuthProvider oAuthprovider();
    MultiValueMap<String, String> makeBody();
}
