package codesquad.team05.service;

import codesquad.team05.web.auth.dto.reponse.GoogleAccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
public class Oauth2Service {


    private String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private static final String GOOGLE_GRANT_TYPE = "authorization_code";

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    private String GOOGLE_REDIRECT_URI = "http://localhost:8080/oauth2/google/token";

    private final RestTemplate restTemplate;

    public String getAccessToken(String code) {

        final String decodedCode = URLDecoder.decode(code, UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "code=" + URLEncoder.encode(decodedCode, StandardCharsets.UTF_8) +
                "&client_id=" + URLEncoder.encode(GOOGLE_CLIENT_ID, StandardCharsets.UTF_8) +
                "&client_secret=" + URLEncoder.encode(GOOGLE_CLIENT_SECRET, StandardCharsets.UTF_8) +
                "&redirect_uri=" + URLEncoder.encode(GOOGLE_REDIRECT_URI, StandardCharsets.UTF_8) +
                "&grant_type=" + URLEncoder.encode(GOOGLE_GRANT_TYPE, StandardCharsets.UTF_8);


        HttpEntity<String> httpEntity = new HttpEntity<>(body,
                headers
        );

        ResponseEntity<GoogleAccessToken> response = restTemplate.exchange(
                GOOGLE_TOKEN_URL,
                HttpMethod.POST,
                httpEntity,
                GoogleAccessToken.class
        );

        return response.getBody().getAccessToken();
    }

    public String getUserInfo(String accessToken){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String url = "https://www.googleapis.com/oauth2/v2/userinfo";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        throw new RuntimeException("Failed to fetch user info");
    }
}
