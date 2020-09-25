package com.teamop.server.dto;

import com.teamop.server.domain.User;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDto {

    @Getter
    public static class Request {

        private String username;
        private String password;
        private String name;
        private String birthDate;
        private String phoneNumber;
        private String deliveryDate;

        public User toEntity(PasswordEncoder passwordEncoder) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setName(name);
            user.setBirthDate(birthDate);
            user.setPhoneNumber(phoneNumber);
            user.setDeliveryDate(deliveryDate);
            return user;
        }
    }

    @Getter
    public static class Response {

        private final Long id;
        private final String username;
        private final String deliveryDate;

        public Response(User user) {
            id = user.getId();
            username = user.getUsername();
            deliveryDate = user.getDeliveryDate();
        }
    }
}
