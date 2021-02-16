package com.teamop.server.service;

import com.teamop.server.domain.User;
import com.teamop.server.dto.UserDto;
import com.teamop.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(UserDto.Request dto) {
        User user = User.builder()
            .username(dto.getUsername())
            .password(passwordEncoder.encode(dto.getPassword()))
            .name(dto.getName())
            .birthDate(dto.getBirthDate())
            .phoneNumber(dto.getPhoneNumber())
            .deliveryDate(dto.getDeliveryDate())
            .build();
        userRepository.save(user);
    }

    public User getUserInfo(UserDto.Request dto) {
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(RuntimeException::new);

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException();
        }

        return user;
    }

    public void changeDeliveryDate(String username, String deliveryDate) {
        User user = userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
        user.changeDeliveryDate(deliveryDate);
    }

    public void changePassword(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
        user.changePassword(passwordEncoder.encode(password));
    }
}
