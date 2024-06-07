package team07.airbnb.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import team07.airbnb.domain.user.service.JwtAndOAuthUserService;
import team07.airbnb.domain.auth.JwtAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAndOAuthUserService oAuth2UserService;
    private final JwtOAuthSuccessHandler successHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(
                        AbstractHttpConfigurer::disable
                )
                .headers(
                        (headerConfig) -> headerConfig.frameOptions(
                                HeadersConfigurer.FrameOptionsConfig::disable
                        )
                )
                .authorizeHttpRequests((authorizeRequest) -> authorizeRequest
//                        .requestMatchers("/review/**", "/payment/**", "/booking/**").authenticated()
//                        .anyRequest().authenticated()
                            .anyRequest().permitAll()
                )
                .logout(
                        (logoutConfig) -> logoutConfig.logoutSuccessUrl("/")
                )
                // OAuth2 로그인 기능에 대한 여러 설정
                // oauth2 로그인 추가
                .oauth2Login(
                        oAuth -> {
                            oAuth.userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService));
                            oAuth.successHandler(successHandler);
                        }
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        ;

        return http.build();
    }

}

