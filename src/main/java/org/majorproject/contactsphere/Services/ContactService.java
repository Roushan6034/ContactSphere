package org.majorproject.contactsphere.Services;

import org.majorproject.contactsphere.entities.Contact;
import org.majorproject.contactsphere.entities.User;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    Contact saveContact(Contact contact);
    Optional<Contact> getUserById(String userId);
    Optional<Contact> updateUser(Contact contact);
    void deleteContact(String userId);
    boolean isContactExist(String userId);
    boolean isContactExistByEmail(String email);
    List<Contact> getAllContact();
    Contact getContactByEmail(String email);
}
