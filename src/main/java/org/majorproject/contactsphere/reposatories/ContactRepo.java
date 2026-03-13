package org.majorproject.contactsphere.reposatories;

import org.majorproject.contactsphere.entities.Contact;

import org.majorproject.contactsphere.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactRepo extends JpaRepository<Contact,String> {
    Optional<Contact> findByEmail(String email);
    boolean existsByEmail(String email);

    List<Contact> findByUser(User user);
}
