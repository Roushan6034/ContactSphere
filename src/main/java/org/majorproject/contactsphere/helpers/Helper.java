package org.majorproject.contactsphere.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {
    public static String getEmailOfLoggedInUser(Authentication authentication){
        if(authentication instanceof OAuth2AuthenticationToken){
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            String authorizedClientRegistrationId = token.getAuthorizedClientRegistrationId();
            OAuth2User user = (OAuth2User) token.getPrincipal();
            String username = "";
            if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
               username = user.getAttribute("email").toString();
                System.out.println("getting from google");
            }
            else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
                username = user.getAttribute("email") != null ? user.getAttribute("email").toString()
                        : user.getAttribute("login").toString() + "@gmail.com";
                System.out.println("getting from github");
            }
            return username;
        }
        else{
            System.out.println("getting from database");
            return authentication.getName();
        }

    }
}
