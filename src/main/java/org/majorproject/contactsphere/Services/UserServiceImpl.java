package org.majorproject.contactsphere.Services;

import org.majorproject.contactsphere.entities.User;
import org.majorproject.contactsphere.helpers.ResourceNotFoundException;
import org.majorproject.contactsphere.reposatories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
   private UserRepo userRepo;
    @Override
    public User saveUser(User user) {
        String uuid = UUID.randomUUID().toString();
        user.setUserId(uuid);
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String userId) {
        return userRepo.findById(userId);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user1 = userRepo.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User" + user.getName() + "not found"));
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        user1.setAbout(user.getAbout());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setProfilePic(user.getProfilePic());
        user1.setProviderUserId(user.getProviderUserId());
        user1.setEnabled(user.isEnabled());
        user1.setEmailVerified(user.isEmailVerified());
        User save = userRepo.save(user1);
        return Optional.of(save);
    }

    @Override
    public void deleteUser(String userId) {
        userRepo.deleteById(userId);
        System.out.println("User " + userId + " has been deleted");
    }

    @Override
    public boolean isUserExist(String userId) {
        return userRepo.existsById(userId);
    }
    @Override
    public boolean isUserExistByEmail(String email) {
        return userRepo.existsById(email);
    }

    @Override
    public List<User> getAllUsers() {
        return  userRepo.findAll();
    }
}
