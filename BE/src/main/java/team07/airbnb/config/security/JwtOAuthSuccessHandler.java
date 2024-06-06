package team07.airbnb.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import team07.airbnb.domain.user.util.JwtUserDetails;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Base64;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtOAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        StringBuilder redirectURL = new StringBuilder();
        redirectURL.append("/api/auth/token?");

        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();

            try {
                URI targetUri = new URI(targetUrl);
                String path = targetUri.getPath();

                redirectURL.append("requestPath=").append(getEncodingString(path));

                String query = targetUri.getQuery();
                if (query != null) {
                    redirectURL.append("&requestQuery=").append(getEncodingString(query));
                }
            } catch (URISyntaxException e) {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }

        getRedirectStrategy().sendRedirect(request, response, redirectURL.toString());
    }

    private String getEncodingString(String target) {
        StringBuilder result = new StringBuilder();

        byte[] encode = Base64.getUrlEncoder().encode(target.getBytes());
        for (byte b : encode) {
            result.append(b).append(".");
        }
        return result.toString();
    }

}
