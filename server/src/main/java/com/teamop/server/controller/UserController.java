package com.teamop.server.controller;

import com.teamop.server.domain.User;
import com.teamop.server.dto.UserDto;
import com.teamop.server.dto.UserDto.Response;
import com.teamop.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
        PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public UserDto.Response getUserInfo(@RequestBody UserDto.Request request) {
        User user = userService
            .getUserInfo(request.getUsername())
            .orElseThrow(RuntimeException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException();
        }

        return new Response(user);
    }

    @PostMapping
    public ResponseEntity<?> join(@RequestBody UserDto.Request request) {
        User user = request.toEntity(passwordEncoder);

        if (!userService.join(user)) {
            throw new RuntimeException();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{username}/delivery-date")
    public ResponseEntity<?> changeDeliveryDate(@PathVariable String username,
        @RequestBody UserDto.Request request) {
        if (request.getDeliveryDate() == null) {
            throw new RuntimeException();
        }

        User user = userService.getUserInfo(username).orElseThrow(RuntimeException::new);
        if (!userService.changeDeliveryDate(user, request.getDeliveryDate())) {
            throw new RuntimeException();
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PatchMapping("/{username}/password")
    public ResponseEntity<?> changePassword(@PathVariable String username,
        @RequestBody UserDto.Request request) {
        if (request.getPassword() == null) {
            throw new RuntimeException();
        }

        User user = userService.getUserInfo(username).orElseThrow(RuntimeException::new);
        if (!userService.changePassword(user, passwordEncoder.encode(request.getPassword()))) {
            throw new RuntimeException();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
