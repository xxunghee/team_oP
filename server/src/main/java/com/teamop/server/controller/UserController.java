package com.teamop.server.controller;

import com.teamop.server.domain.User;
import com.teamop.server.dto.UserDto;
import com.teamop.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public UserDto.Response getUserInfo(@RequestBody UserDto.Request request) {
        User user = userService.getUserInfo(request);
        return new UserDto.Response(user);
    }

    @PostMapping
    public ResponseEntity<?> join(@RequestBody UserDto.Request request) {
        userService.join(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{username}/delivery-date")
    public ResponseEntity<?> changeDeliveryDate(@PathVariable String username,
        @RequestBody UserDto.Request request) {
        userService.changeDeliveryDate(username, request.getDeliveryDate());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PatchMapping("/{username}/password")
    public ResponseEntity<?> changePassword(@PathVariable String username,
        @RequestBody UserDto.Request request) {
        userService.changePassword(username, request.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
