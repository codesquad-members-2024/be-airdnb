package codesquad.airdnb.domain.member;


import codesquad.airdnb.domain.member.dto.request.LoginRequest;
import codesquad.airdnb.domain.member.dto.request.SignUpRequest;
import codesquad.airdnb.domain.member.dto.response.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

import static codesquad.airdnb.global.security.JwtTokenProvider.ACCESS_TOKEN_EXP_TIME;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        AuthResponse authResponse = memberService.signUp(request);
        HttpHeaders headers = createAuthResponseHeader(authResponse.accessToken());

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = memberService.login(request);
        HttpHeaders headers = createAuthResponseHeader(authResponse.accessToken());

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value = "Authorization", required = false) String token) {
        String accessToken = token.replace("Bearer ", "");
        memberService.logout(accessToken);
        HttpHeaders headers = createAccessTokenExpiredHeader();

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String,String>> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        Auth auth = memberService.refresh(refreshToken);
        HttpHeaders headers = createAuthResponseHeader(auth.accessToken());

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(Collections.singletonMap("accessToken", auth.accessToken()));
    }

    private HttpHeaders createAuthResponseHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,
                String.format(
                        "%s=%s; Path=%s; Max-Age=%d; HttpOnly; Secure; SameSite=None",
                        "accessToken", accessToken, "/", ACCESS_TOKEN_EXP_TIME / 1000)
        );

        return headers;
    }

    private HttpHeaders createAccessTokenExpiredHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,
                String.format(
                        "%s=%s; Path=%s; Max-Age=%d; HttpOnly; Secure; SameSite=None",
                        "accessToken", null, "/", 0)
        );

        return headers;
    }
}