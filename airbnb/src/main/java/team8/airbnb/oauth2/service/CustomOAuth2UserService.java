package team8.airbnb.oauth2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team8.airbnb.oauth2.dto.CustomOAuth2User;
import team8.airbnb.oauth2.dto.GoogleResponse;
import team8.airbnb.oauth2.dto.KakaoResponse;
import team8.airbnb.oauth2.dto.NaverResponse;
import team8.airbnb.oauth2.dto.OAuth2Response;
import team8.airbnb.oauth2.dto.UserDTO;
import team8.airbnb.entity.User;
import team8.airbnb.repository.UserRepository;

@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;

  public CustomOAuth2UserService(UserRepository userRepository) {

    this.userRepository = userRepository;
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);
    String registrationId = userRequest.getClientRegistration().getRegistrationId();

    OAuth2Response oAuth2Response = null;
    if (registrationId.equals("naver")) {
      oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
    } else if (registrationId.equals("google")) {
      oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
    } else if (registrationId.equals("kakao")) {
      oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
    } else {
      log.warn("Unsupported OAuth2 provider: {}", registrationId);
      return null;
    }

    log.debug("oAuth2Response : {}", oAuth2Response.getUsername());

    String username = oAuth2Response.getUsername();

    User existData = userRepository.findByUsername(username);

    if (existData == null) {
      log.debug("신규 유저입니다.");
      User user = new User();
      user.setEmail(oAuth2Response.getEmail());
      user.setUsername(username);
      user.setPassword(null);
      user.setOauthType(oAuth2Response.getProvider());
      user.setRole("ROLE_USER");

      userRepository.save(user);

      UserDTO userDTO = new UserDTO();
      userDTO.setUsername(username);
      userDTO.setRole("ROLE_USER");
      log.debug("New user created: {}", userDTO);
      return new CustomOAuth2User(userDTO);
    } else {
      log.debug("기존 유저입니다.");
      existData.setEmail(oAuth2Response.getEmail());
      userRepository.save(existData);

      UserDTO userDTO = new UserDTO();
      userDTO.setUsername(existData.getUsername());
      userDTO.setRole(existData.getRole());
      log.debug("Existing user updated: {}", userDTO);
      return new CustomOAuth2User(userDTO);
    }
  }

}
