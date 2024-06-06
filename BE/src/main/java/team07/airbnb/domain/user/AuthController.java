package team07.airbnb.domain.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.domain.user.util.JwtUserDetails;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/token")
    public ResponseEntity<Map<String, String>> getJwt(@RequestParam String requestPath, @RequestParam String requestQuery) {
        Map<String, String> response = new HashMap<>();
        JwtUserDetails principal = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        response.put("jwt", principal.getPassword());

        response.put("requestPath", requestPath == null ? "/" : requestPath);

        if (requestQuery != null) {
            response.put("requestQuery", requestQuery);
        }

        return ResponseEntity.ok(response);
    }
}
