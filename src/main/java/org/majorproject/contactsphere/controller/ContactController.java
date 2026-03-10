package org.majorproject.contactsphere.controller;

import org.majorproject.contactsphere.Services.ContactService;
import org.majorproject.contactsphere.entities.Contact;
import org.majorproject.contactsphere.forms.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    @Autowired
    private ContactService contactService;
    @RequestMapping("/add")
    public String addContactView(Model model   ) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String saveContact(@ModelAttribute ContactForm contactForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "user/add_contact";
        }
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setAddress(contactForm.getAddress());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        contact.setInstagramLink(contactForm.getInstagramLink());
        contact.setPicture(contactForm.getPicture());
        contact.setFavorite(contactForm.getFavorite());
        contactService.saveContact(contact);
        return "redirect:/user/contacts/add";

    }

}
