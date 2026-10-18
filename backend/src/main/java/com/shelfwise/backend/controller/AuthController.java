package com.shelfwise.backend.controller;

import com.shelfwise.backend.dto.LoginRequest;
import com.shelfwise.backend.dto.RegisterRequest;
import com.shelfwise.backend.entity.User;
import com.shelfwise.backend.entity.enums.Role;
import com.shelfwise.backend.service.AuthService;
import com.shelfwise.backend.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.shelfwise.backend.dto.LoginResponse;
import com.shelfwise.backend.exception.DuplicateEmailException;
import com.shelfwise.backend.repository.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(UserService userService,
                          AuthService authService,
                          UserRepository userRepository) {

        this.userService = userService;
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already registered");
        }

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
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}