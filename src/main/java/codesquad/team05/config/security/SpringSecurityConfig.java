package codesquad.team05.config.security;

import codesquad.team05.domain.jwt.filter.JwtFilter;
import codesquad.team05.domain.jwt.service.JwtService;
import codesquad.team05.domain.security.login.JwtAuthenticationFilter;
import codesquad.team05.domain.security.oauth2.OAuth2FailureHandler;
import codesquad.team05.domain.security.oauth2.OAuth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SpringSecurityConfig {

    private final JwtService jwtService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
//    private final PrincipalOAuth2Sevice principalOauth2UserService;

    @Bean
    SecurityFilterChain configure(HttpSecurity http,
                                  AuthenticationProvider authenticationProvider) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtService);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        http.csrf(AbstractHttpConfigurer::disable).sessionManagement((s)->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());
//
//        http.oauth2Login(oauth2Login-> oauth2Login.
//                userInfoEndpoint(endPoint->endPoint.
//                        userService(oAuth2UserService)));

//        http.oauth2Login(oauth2Login -> oauth2Login
//                .userInfoEndpoint(userEndpoint -> userEndpoint.
//                                userService(oAuth2UserService))
//                .successHandler(oAuth2LoginSuccessHandler)
//                .failureHandler(oAuth2FailureHandler)
//        );

        http.authenticationProvider(authenticationProvider);

        http.authorizeHttpRequests(c-> c.requestMatchers(HttpMethod.POST,"/user").permitAll().
                requestMatchers("/error").permitAll().
                requestMatchers("/login").permitAll().
                requestMatchers("/**").permitAll());

//                requestMatchers("/oauth2/**").permitAll().
//                requestMatchers(HttpMethod.POST, "/accommodations").hasRole("HOST").
//                requestMatchers(HttpMethod.PUT, "/accommodations").hasRole("HOST").
//                anyRequest().authenticated());


        http.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class).addFilter(jwtAuthenticationFilter);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
