package team8.airbnb.oauth2.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team8.airbnb.oauth2.dto.CustomOAuth2User;
import team8.airbnb.oauth2.dto.NaverResponse;
import team8.airbnb.oauth2.dto.OAuth2Response;
import team8.airbnb.oauth2.dto.UserDTO;
import team8.airbnb.user.User;
import team8.airbnb.user.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
  private final UserRepository userRepository;

  public CustomOAuth2UserService(UserRepository userRepository) {

    this.userRepository = userRepository;
  }

  //  Spring Security에서 OAuth2 로그인 시 사용되는 인터페이스인 OAuth2UserService의 메서드
  //  이 메서드는 OAuth2 프로토콜을 사용하여 인증된 사용자의 정보를 가져오는 역할
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    // 기본 사용자 정보 로드
    // 기존의 loadUser 메서드를 통해 OAuth2 제공자와 통신하여 사용자 정보를 가져옴
    OAuth2User oAuth2User = super.loadUser(userRequest);

    System.out.println(oAuth2User);

    // 어디 서비스에서 온 요청인지 확인하기해
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    OAuth2Response oAuth2Response = null;
    if (registrationId.equals("naver")) {
      oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
    } else {
      return null;
    }

    //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듬
//    String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
    System.out.println("oAuth2Response = " + oAuth2Response.getUsername());

    String username = oAuth2Response.getUsername();
    String providerId = oAuth2Response.getProviderId();
    System.out.println("providerId = " + providerId);

    User existData = userRepository.findByUsername(username);
//    // 이름이 null 이 아니면
//    if(oAuth2Response.getUsername() != null) {

      // 새로운 유저라면
    if (existData == null) {
      System.out.println("신규 유저입니다");
      User user = new User();
      user.setEmail(oAuth2Response.getEmail());
      user.setUsername(username);
      user.setPassword(null);
      user.setOauthType(oAuth2Response.getProvider());
      user.setRole("ROLE_USER");

      userRepository.save(user);

      UserDTO userDTO = new UserDTO();
//        userDTO.setUsername(username);
//        userDTO.setName(oAuth2Response.getUsername());
      userDTO.setUsername(username);
      userDTO.setRole("ROLE_USER");
//      userDTO.setOauthType(oAuth2Response.getProvider());

      return new CustomOAuth2User(userDTO);
    }
      // 기존 유저라면
      else {
        System.out.println("기존 유저입니다");
        existData.setEmail(oAuth2Response.getEmail());
//        existData.setUsername(oAuth2Response.getName());
        userRepository.save(existData);

        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername(existData.getUsername());
        userDTO.setUsername(existData.getUsername());
//        userDTO.setName(oAuth2Response.getUsername());
        userDTO.setRole(existData.getRole());

        return new CustomOAuth2User(userDTO);
      }
    }
//  }
}
