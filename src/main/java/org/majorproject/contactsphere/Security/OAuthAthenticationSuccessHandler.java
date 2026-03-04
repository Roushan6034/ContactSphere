package org.majorproject.contactsphere.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.majorproject.contactsphere.entities.Providers;
import org.majorproject.contactsphere.entities.User;
import org.majorproject.contactsphere.reposatories.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class OAuthAthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserRepo userRepo;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken authentication1 = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId= authentication1.getAuthorizedClientRegistrationId();
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

          logger.info(user.getName());

          user.getAttributes().forEach((key, value) -> {
          logger.info("{} => {}", key, value);
          });

         logger.info(user.getAuthorities().toString());
        User user1 = new User();
        user1.setPassword(null);
        user1.setUserId(UUID.randomUUID().toString());
        user1.setEnabled(true);
        user1.setEmailVerified(true);
         // data database save:
         if(authorizedClientRegistrationId.equalsIgnoreCase("google")) {
             String email = user.getAttribute("email").toString();
             String name = user.getAttribute("name").toString();
             String picture = user.getAttribute("picture").toString();
             user1.setEmail(email);
             user1.setName(name);
             user1.setProfilePic(picture);
             user1.setProviderUserId(user.getName());
             user1.setRoles(List.of("ROLE_USER"));
             user1.setAbout("This account is created using google..");
         } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
             String email = user.getAttribute("email") != null ? user.getAttribute("email").toString()
                     : user.getAttribute("login").toString() + "@gmail.com";
             String picture = user.getAttribute("avatar_url").toString();
             String name = user.getAttribute("login").toString();
             String providerUserId = user.getName();
             user1.setEmail(email);
             user1.setName(name);
             user1.setProfilePic(picture);
             user1.setProviderUserId(providerUserId);
             user1.setProvider(Providers.GITHUB);
             user1.setAbout("This account is created using github");

         }
        User user2 = userRepo.findByEmail(user1.getEmail()).orElse(null);
          if (user2 == null) {

          userRepo.save(user1);
          logger.info("User saved:" + user1.getEmail());
          }
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
    }
}
