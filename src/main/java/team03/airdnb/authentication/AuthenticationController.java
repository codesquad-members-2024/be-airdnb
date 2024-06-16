package team03.airdnb.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team03.airdnb.authentication.dto.request.LoginDto;
import team03.airdnb.authentication.dto.response.JwtResultDto;
import team03.airdnb.authentication.jwt.JwtUtil;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private static final String LOGIN_SUCCESS_MESSAGE = "로그인 성공!";
    private static final String LOGIN_FAILURE_MESSAGE = "잘못된 로그인 정보 입니다.";

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResultDto> login(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getName(), loginDto.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new JwtResultDto(null, LOGIN_FAILURE_MESSAGE));
        }

        final String jwt = jwtUtil.generateToken(loginDto.getName());

        return ResponseEntity
                .ok()
                .body(new JwtResultDto(jwt, LOGIN_SUCCESS_MESSAGE));
    }
}
