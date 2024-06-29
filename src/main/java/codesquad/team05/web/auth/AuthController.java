package codesquad.team05.web.auth;

import codesquad.team05.domain.jwt.constants.JwtConstants;
import codesquad.team05.domain.jwt.service.JwtService;
import codesquad.team05.domain.jwt.utils.JwtUtils;
import codesquad.team05.service.Oauth2Service;
import codesquad.team05.web.auth.dto.reponse.AuthResponse;
import codesquad.team05.web.auth.dto.request.AuthRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final Oauth2Service oauth2Service;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {

        try {
            String username = authRequest.getUserId();
            String password = authRequest.getPassword();

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String accessToken = jwtService.generateAccessToken(username);
            String refreshToken = jwtService.generateRefreshToken(username);

            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request,
                                         HttpServletResponse response) {

        String header = request.getHeader(JwtConstants.JWT_HEADER);
        String accessToken = JwtUtils.getTokenFromHeader(header);


        // 액세스 토큰이 유효한지 확인하고, 유효하면 로그아웃 처리
        if (accessToken != null && JwtUtils.validateToken(accessToken)) {

            String username = JwtUtils.getUsernameFromToken(accessToken);
            jwtService.deleteRefreshToken(accessToken);
            return new ResponseEntity<>("Successfully logged out", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/reissue")
    public ResponseEntity<String> reissue(HttpServletRequest request,
                                          HttpServletResponse response) {

        String refreshToken = JwtUtils.resolveToken(request, JwtConstants.REFRESH);
        String accessToken = JwtUtils.resolveToken(request, JwtConstants.ACCESS);
        String newAccessToken = jwtService.renewToken(refreshToken, accessToken);
        JwtUtils.setTokenSetHeader(newAccessToken, response);

        return new ResponseEntity<>("The access token was successfully reissued", HttpStatus.OK);
    }

}
