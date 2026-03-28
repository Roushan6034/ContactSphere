package org.majorproject.contactsphere.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.majorproject.contactsphere.Services.ContactService;
import org.majorproject.contactsphere.Services.EmailService;
import org.majorproject.contactsphere.Services.ImageService;
import org.majorproject.contactsphere.Services.UserService;
import org.majorproject.contactsphere.entities.Contact;
import org.majorproject.contactsphere.entities.User;
import org.majorproject.contactsphere.forms.ContactForm;
import org.majorproject.contactsphere.forms.ContactSearchForm;
import org.majorproject.contactsphere.helpers.Helper;
import org.majorproject.contactsphere.helpers.Message;
import org.majorproject.contactsphere.helpers.MessageType;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.UUID;
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
    @Autowired
    private EmailService emailService;

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
    public String viewContacts(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "4") int size,
            @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
            @RequestParam(value = "direction",defaultValue = "asc") String direction,
            Authentication authentication,Model model) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Page<Contact> pageContact = contactService.getByUser(user,page,size,sortBy,direction);
        model.addAttribute("pageContact",pageContact);
        model.addAttribute("contactSearchForm",new ContactSearchForm());
        return "user/contacts";
    }
    @RequestMapping("/search")
    public String searchHandler(
            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value="size", defaultValue = "4")int size,
            @RequestParam(value="page",defaultValue = "0")int page,
            @RequestParam(value="sortBy",defaultValue = "name")String sortBy,
            @RequestParam(value="sortOrder",defaultValue = "asc") String direction,
            Model model,
            Authentication authentication

    ){
        User user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
        Page<Contact> pageContact = null;
        if(contactSearchForm.getField().equalsIgnoreCase("name")) {
             pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,user);

        }
        else if(contactSearchForm.getField().equalsIgnoreCase("Phone")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        }
        model.addAttribute("pageContact",pageContact);
        model.addAttribute("contactSearchForm",contactSearchForm);


        return "user/search";
    }
    @RequestMapping("/delete/{userId}")
    public String deleteContact(@PathVariable("userId")String userId, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
        System.out.println("Deleting contact with id "+userId);
        contactService.deleteContact(userId);
        String referer = request.getHeader("Referer");
        redirectAttributes.addFlashAttribute("message", Message.builder().message("Contact deleted successfully").messageType(MessageType.green).build());
//        session.setAttribute("message",Message.builder().message("Contact deleted successfully").messageType(MessageType.green).build());
        return "redirect:" + (referer != null ? referer : "/user/contacts");

    }
    @GetMapping("/view/{contactId}")
    public String updateContactForm(@PathVariable("contactId") String contactId, Model model, RedirectAttributes redirectAttributes) {
        Contact contact = contactService.getById(contactId);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setAddress(contact.getAddress());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.getFavorite());
        contactForm.setLinkedinLink(contact.getLinkedinLink());
        contactForm.setInstagramLink(contact.getInstagramLink());
        contactForm.setImage(contact.getPicture());
        model.addAttribute("contactForm",contactForm);
        model.addAttribute("contactId",contactId);

        return "user/update_contact_view";

    }
    @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
    public String updateContact(
            @PathVariable("contactId") String contactId,
            @Valid @ModelAttribute ContactForm contactForm,
            BindingResult bindingResult,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }
        Contact existingContact = contactService.getById(contactId);
        existingContact.setName(contactForm.getName());
        existingContact.setEmail(contactForm.getEmail());
        existingContact.setAddress(contactForm.getAddress());
        existingContact.setPhoneNumber(contactForm.getPhoneNumber());
        existingContact.setDescription(contactForm.getDescription());
        existingContact.setFavorite(contactForm.getFavorite());
        existingContact.setLinkedinLink(contactForm.getLinkedinLink());
        existingContact.setInstagramLink(contactForm.getInstagramLink());
        if (contactForm.getPicture() != null
                && !contactForm.getPicture().isEmpty()
                && contactForm.getPicture().getSize() > 0) {

            String url = imageService.uploadImage(contactForm.getPicture());
            existingContact.setPicture(url);
        }

        contactService.updateUser(existingContact);

        redirectAttributes.addFlashAttribute("message",
                Message.builder()
                        .message("Contact updated successfully")
                        .messageType(MessageType.green)
                        .build());

        return "redirect:/user/contacts/view/" + contactId;
    }

}
