package org.majorproject.contactsphere.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.majorproject.contactsphere.Services.UserService;
import org.majorproject.contactsphere.entities.User;
import org.majorproject.contactsphere.forms.UserForm;
import org.majorproject.contactsphere.helpers.Message;
import org.majorproject.contactsphere.helpers.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class pageController {
    @Autowired
    private UserService userService;
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
    public String register(Model model, HttpSession session){
        System.out.println("This is the register page");
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        Message message = (Message) session.getAttribute("msg");
        if (message != null) {
            model.addAttribute("msg", message);
            session.removeAttribute("msg");
        }
        return "register";
    }
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processForm(@Valid  @ModelAttribute UserForm userForm, BindingResult bindingResult,HttpSession session){
        System.out.println(userForm);
        if (bindingResult.hasErrors()) {
            return "register";
        }
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setName(userForm.getName());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://www.shutterstock.com/image-vector/black-silhouette-man-male-head-260nw-2433765529.jpg");
        User savedUser = userService.saveUser(user);
        Message message = Message.builder().message("Registration Successfully Completed!!!").messageType(MessageType.green).build();
        session.setAttribute("msg", message);
        System.out.println("Saved user: " + savedUser);
        return "redirect:/register";
    }
}

