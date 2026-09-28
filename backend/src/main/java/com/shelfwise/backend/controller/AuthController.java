package com.shelfwise.backend.controller;

import com.shelfwise.backend.dto.LoginRequest;
import com.shelfwise.backend.dto.RegisterRequest;
import com.shelfwise.backend.entity.User;
import com.shelfwise.backend.entity.enums.Role;
import com.shelfwise.backend.service.AuthService;
import com.shelfwise.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService,
                          AuthService authService) {
        this.userService = userService;
        this.authService = authService;
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

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}