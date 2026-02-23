package org.majorproject.contactsphere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class pageController {
    @RequestMapping("/home")
    public String home(){
        return "home";
    }
    @RequestMapping("/about")
    public String about(){
        System.out.println("This is the about page");
        return "about";
    }
    @RequestMapping("/contacts")
    public String contacts(){
        System.out.println("This is the contact page");
        return "contacts";
    }
    @RequestMapping("/services")
    public String services(){
        System.out.println("This is the service page");
        return "services";
    }
    @RequestMapping("/login")
    public String login(){
        System.out.println("This is the login page");
        return "login";
    }
    @RequestMapping("register")
    public String register(){
        System.out.println("This is the register page");
        return "register";
    }
}

