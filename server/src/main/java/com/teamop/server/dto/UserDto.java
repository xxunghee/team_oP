package com.teamop.server.dto;

import com.teamop.server.domain.User;
import lombok.Getter;

public class UserDto {

    @Getter
    public static class Request {

        private String username;
        private String password;
        private String name;
        private String birthDate;
        private String phoneNumber;
        private String deliveryDate;
    }

    @Getter
    public static class Response {

        private final Long id;
        private final String name;
        private final String deliveryDate;

        public Response(User user) {
            id = user.getId();
            name = user.getName();
            deliveryDate = user.getDeliveryDate();
        }
    }
}
