package org.majorproject.contactsphere.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.majorproject.contactsphere.Services.ContactService;
import org.majorproject.contactsphere.Services.ImageService;
import org.majorproject.contactsphere.Services.UserService;
import org.majorproject.contactsphere.entities.Contact;
import org.majorproject.contactsphere.entities.User;
import org.majorproject.contactsphere.forms.ContactForm;
import org.majorproject.contactsphere.helpers.Helper;
import org.majorproject.contactsphere.helpers.Message;
import org.majorproject.contactsphere.helpers.MessageType;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ContactService contactService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    public String addContactView(Model model   ) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String saveContact(@Valid  @ModelAttribute ContactForm contactForm, BindingResult bindingResult , HttpSession session,Authentication authentication) {
        if(bindingResult.hasErrors()) {
            session.setAttribute("msg", Message.builder().message("Please correct the following errors").messageType(MessageType.red).build());
            return "user/add_contact";
        }
        logger.info("file-information: {}", contactForm.getPicture().getOriginalFilename());
        String fileURL= imageService.uploadImage(contactForm.getPicture());
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setAddress(contactForm.getAddress());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        contact.setInstagramLink(contactForm.getInstagramLink());
        contact.setPicture(fileURL);
        contact.setUser(user);
        contact.setFavorite(contactForm.getFavorite());
        contactService.saveContact(contact);
        session.setAttribute("msg", Message.builder().message("Contact added successfully").messageType(MessageType.green).build());
        return "redirect:/user/contacts/add";

    }
    @RequestMapping
    public String viewContacts(Authentication authentication,Model model) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        List<Contact> contacts = contactService.getByUser(user);
        model.addAttribute("contacts", contacts);
        return "user/contacts";
    }

}
