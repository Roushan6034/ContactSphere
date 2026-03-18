package org.majorproject.contactsphere.reposatories;

import org.majorproject.contactsphere.entities.Contact;

import org.majorproject.contactsphere.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactRepo extends JpaRepository<Contact,String> {
    Optional<Contact> findByEmail(String email);
    boolean existsByEmail(String email);

    Page<Contact> findByUser(User user, Pageable pageable);
    Page<Contact>findByUserAndNameContainingIgnoreCase(User user,String name, Pageable pageable);
    Page<Contact>findByUserAndEmailContainingIgnoreCase(User user,String email, Pageable pageable);
    Page<Contact>findByUserAndPhoneNumberContainingIgnoreCase(User user,String PhoneNumber, Pageable pageable);
}
