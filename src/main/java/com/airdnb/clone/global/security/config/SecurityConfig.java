package com.airdnb.clone.global.security.config;

import com.airdnb.clone.domain.oauth2.AirdnbOAuth2SuccessHandler;
import com.airdnb.clone.domain.oauth2.AirdnbOAuth2UserService;
import com.airdnb.clone.domain.oauth2.JwtTokenService;
import com.airdnb.clone.global.security.filter.JwtTokenGeneratorFilter;
import com.airdnb.clone.global.security.filter.JwtTokenValidatorFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true) // FIXME: 개발 중에만 사용
public class SecurityConfig {

    private final AirdnbOAuth2UserService userService;
    private final AirdnbOAuth2SuccessHandler successHandler;
    private final JwtTokenService jwtTokenService;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration corsConfig = new CorsConfiguration();
                        corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                        corsConfig.setAllowedMethods(Collections.singletonList("*"));
                        corsConfig.setAllowedHeaders(Collections.singletonList("*"));
                        corsConfig.setAllowCredentials(true);
                        corsConfig.setExposedHeaders(List.of("Authorization"));
                        corsConfig.setMaxAge(3600L);
                        return corsConfig;
                    }
                })).csrf(csrf -> csrf.disable())
                .addFilterAfter(new JwtTokenGeneratorFilter(jwtTokenService), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidatorFilter(jwtTokenService), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/v1/reservations/**").hasRole("USER")
                        .requestMatchers("/stays").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2Configurer -> oauth2Configurer
                        .loginPage("http://localhost:5173/login")
                        .successHandler(successHandler)
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(userService))
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
