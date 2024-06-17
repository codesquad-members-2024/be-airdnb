package com.team01.airdnb.authentication.oauth;

import com.team01.airdnb.authentication.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    TokenProvider tokenProvider;

    @Autowired
    AuthController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/auth/success")
    public ResponseEntity<String> authSuccess(@RequestParam("accessToken") String accessToken) {
        // accessToken 검증 및 처리 로직을 여기에 추가할 수 있습니다.
        return ResponseEntity.ok("Authentication successful! Access Token: " + accessToken);
    }

    @GetMapping("/error")
    public ResponseEntity<String> handleError() {
        return ResponseEntity.status(404).body("Custom error message: Resource not found");
    }

    @GetMapping("/email")
    public ResponseEntity<String> handleEmail(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // "Bearer " 부분 제거
            if (tokenProvider.validateToken(token)) {
                String email = tokenProvider.getEmail(token);
                return ResponseEntity.ok(email);
            }
        }
        return ResponseEntity.badRequest().body("Invalid token");
    }
}
