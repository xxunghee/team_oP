package com.teamop.server.service;

import com.teamop.server.domain.User;
import com.teamop.server.repository.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean join(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<User> getUserInfo(String username) {
        return userRepository.findByUsername(username);
    }

    public Boolean changeDeliveryDate(User user, String deliveryDate) {
        try {
            user.setDeliveryDate(deliveryDate);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean changePassword(User user, String encodedPassword) {
        try {
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
