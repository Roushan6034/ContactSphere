package org.majorproject.contactsphere.Services;

import org.majorproject.contactsphere.entities.Contact;
import org.majorproject.contactsphere.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    Page<Contact> searchByName(String name,int size, int page,String sortBy,String sortOrder,User user);
    Page<Contact> searchByEmail(String email,int size,int page,String sortBy,String sortOrder,User user);
    Page<Contact> searchByPhoneNumber(String phone,int size,int page,String sortBy,String sortOrder,User user);
    Contact getContactByEmail(String email);
    Page<Contact> getByUser(User user, int page, int size,String sortBy,String direction);
}
