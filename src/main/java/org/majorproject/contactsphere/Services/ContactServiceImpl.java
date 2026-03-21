package org.majorproject.contactsphere.Services;

import org.majorproject.contactsphere.entities.Contact;
import org.majorproject.contactsphere.entities.User;
import org.majorproject.contactsphere.helpers.ResourceNotFoundException;
import org.majorproject.contactsphere.reposatories.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepo contactRepo;
    @Autowired
    private PageableArgumentResolver pageableArgumentResolver;

    @Override
    public void deleteContact(String userId) {
        contactRepo.deleteById(userId);
        System.out.println("Contact " + userId + " has been deleted");
    }

    @Override
    public Contact saveContact(Contact contact) {
        String id = UUID.randomUUID().toString();
        contact.setId(id);
        return contactRepo.save(contact);
    }

    @Override
    public Optional<Contact> getUserById(String ContactId) {
        return Optional.of(contactRepo.findById(ContactId).orElseThrow(() -> new ResourceNotFoundException("Contact Not found")));
    }

    @Override
    public Optional<Contact> updateUser(Contact contact) {
        Contact contact1 = contactRepo.findById(contact.getId()).orElseThrow(() -> new ResourceNotFoundException("Contact Not found"));
        contact1.setName(contact.getName());
        contact1.setEmail(contact.getEmail());
        contact1.setAddress(contact.getAddress());
        contact1.setPhoneNumber(contact.getPhoneNumber());
        contact1.setPicture(contact.getPicture());
        contact1.setFavorite(contact.getFavorite());
        contact1.setDescription(contact.getDescription());
        Contact save = contactRepo.save(contact1);
        return Optional.of(save);
    }

    @Override
    public boolean isContactExist(String userId) {
        return contactRepo.existsById(userId);
    }

    @Override
    public boolean isContactExistByEmail(String email) {
        return contactRepo.existsByEmail(email);
    }

    @Override
    public List<Contact> getAllContact() {
        List<Contact> allContact = contactRepo.findAll();
        return allContact;
    }

    @Override
    public Page<Contact> searchByName(String name, int size, int page, String sortBy, String sortOrder,User user) {
        Sort sort=sortOrder.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndNameContainingIgnoreCase(user,name,pageable);
    }


    @Override
    public Page<Contact> searchByEmail(String email, int size,int page, String sortBy, String sortOrder,User user) {
        Sort sort=sortOrder.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndEmailContainingIgnoreCase(user,email,pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phone, int size,int page, String sortBy, String sortOrder,User user) {
        Sort sort=sortOrder.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndPhoneNumberContainingIgnoreCase(user,phone,pageable);
    }

    @Override
    public Contact getContactByEmail(String email) {
        return contactRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Contact Not found"));
    }

    @Override
    public Page<Contact> getByUser(User user,int page,int size,String sortBy,String direction) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(page,size,sort);
        return contactRepo.findByUser(user,pageRequest);
    }

    @Override
    public Contact getById(String contactId) {
        return contactRepo.findById(contactId).orElseThrow(() -> new ResourceNotFoundException("Contact Not found"));
    }
}
