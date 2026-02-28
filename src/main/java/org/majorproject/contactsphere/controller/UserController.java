package org.majorproject.contactsphere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value = "/dashboard")
    public String userDashboard(){
        return "user/dashboard";
    }
}
