package team8.airbnb.securityConfig;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import team8.airbnb.oauth2.CustomSuccessHandler;
import team8.airbnb.oauth2.service.CustomOAuth2UserService;
import team8.airbnb.jwt.jwtToken.JwtFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity

public class SecurityConfig {

  private final JwtFilter jwtFilter;
  private final CustomOAuth2UserService customOAuth2UserService;
  private final CustomSuccessHandler customSuccessHandler;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf((auth) -> auth.disable());
    http.formLogin((auth) -> auth.disable());
    http.httpBasic((auth) -> auth.disable());
    http.authorizeHttpRequests((requests) -> requests
        .requestMatchers(POST, "/auth/login", "/auth/registration").permitAll()
        .requestMatchers(GET, "/", "/total/login","/success").permitAll()
        .anyRequest().authenticated()
    );
    http
        .sessionManagement(
            (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    /*
    oauth2
     */
    http
        .oauth2Login((oauth2) -> oauth2
//            .loginPage("/total/login") // 전체 로그인 페이지
            .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                .userService(customOAuth2UserService))
            .successHandler(customSuccessHandler)
        );

    return http.build();
  }
}