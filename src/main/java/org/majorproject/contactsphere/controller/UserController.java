package org.majorproject.contactsphere.controller;

import org.majorproject.contactsphere.helpers.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/dashboard")
    public String userDashboard(){
        return "user/dashboard";
    }
    @RequestMapping("/profile")
    public String userProfile(Authentication authentication){
        String username=Helper.getEmailOfLoggedInUser(authentication);
        log.info("username="+username);
        return "user/profile";
    }
}
