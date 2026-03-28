package org.majorproject.contactsphere.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.majorproject.contactsphere.helpers.Message;
import org.majorproject.contactsphere.helpers.MessageType;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class AuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(exception instanceof DisabledException) {
            HttpSession session = request.getSession();
            session.setAttribute("message", Message.builder().message("user disabled").messageType(MessageType.green).build());
            response.sendRedirect("/login");
        }
    }
}
