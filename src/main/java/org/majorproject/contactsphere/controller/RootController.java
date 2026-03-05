package org.majorproject.contactsphere.controller;

import org.majorproject.contactsphere.Services.UserService;
import org.majorproject.contactsphere.entities.User;
import org.majorproject.contactsphere.helpers.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {
    Logger log= LoggerFactory.getLogger(RootController.class);
    @Autowired
    private UserService userService;
    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if(authentication==null){
            return;
        }
        String username= Helper.getEmailOfLoggedInUser(authentication);
        User userByEmail = userService.getUserByEmail(username);
        System.out.println(userByEmail.getName());
        System.out.println(userByEmail.getEmail());
        model.addAttribute("loggedInUser",userByEmail);
        log.info("username="+username);

    }


}
