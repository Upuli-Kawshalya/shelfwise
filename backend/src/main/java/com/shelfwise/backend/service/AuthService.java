
package com.shelfwise.backend.service;

import com.shelfwise.backend.dto.LoginRequest;
import com.shelfwise.backend.dto.LoginResponse;
import com.shelfwise.backend.entity.User;
import com.shelfwise.backend.repository.UserRepository;
import com.shelfwise.backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        // Step 1: Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        // Step 2: Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // Step 3: Check whether account is active
        if (!Boolean.TRUE.equals(user.getStatus())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // Step 4: Generate JWT token
        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole()
        );

        // Step 5: Return JWT token
        return new LoginResponse(token);
    }
}
