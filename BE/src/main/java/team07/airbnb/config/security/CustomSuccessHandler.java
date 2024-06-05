package team07.airbnb.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        String targetUrl = savedRequest.getRedirectUrl();

        try {
            URI targetUri = new URI(targetUrl);
            String path = targetUri.getPath();
            String query = targetUri.getQuery();
            String newTargetUrl = request.getContextPath() + "/api" + path;
            if (query != null) {
                newTargetUrl += "?" + query;
            }

            getRedirectStrategy().sendRedirect(request, response, newTargetUrl);
        } catch (URISyntaxException e) {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
