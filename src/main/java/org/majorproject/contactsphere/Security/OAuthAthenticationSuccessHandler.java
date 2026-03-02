package org.majorproject.contactsphere.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuthAthenticationSuccessHandler implements AuthenticationSuccessHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
        user.getAttributes().forEach((key, value) -> {
            logger.info(key + ": " + value);

        });
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
    }
}
