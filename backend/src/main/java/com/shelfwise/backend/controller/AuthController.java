package com.shelfwise.backend.controller;

import com.shelfwise.backend.dto.RegisterRequest;
import com.shelfwise.backend.entity.User;
import com.shelfwise.backend.entity.enums.Role;
import com.shelfwise.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.CUSTOMER)
                .status(true)
                .build();

        return userService.saveUser(user);
    }
}