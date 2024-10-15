package com.example.E_stack.controller;

import com.example.E_stack.entities.Admin;
import com.example.E_stack.entities.AuthenticationResponse;
import com.example.E_stack.entities.Personne;
import com.example.E_stack.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class); // Add logger instance

    private final AuthenticationService authenticationService;

    @PostMapping("/register-admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody Admin admin) {
        logger.info("Received admin registration request for email: {}", admin.getEmail());
        try {
            AuthenticationResponse response = authenticationService.registerAdmin(admin);
            if ("Admin already exists".equals(response.getMessage())) {
                logger.warn("Admin registration failed - admin already exists: {}", admin.getEmail());
                return ResponseEntity.badRequest().body(response);
            }
            logger.info("Admin registration successful for email: {}", admin.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error during admin registration: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(new AuthenticationResponse(null, "Registration failed: " + e.getMessage(), null, "")); // Provide a role value
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Personne personne) {
        logger.info("Received login request for user: {}", personne.getUsername());
        try {
            AuthenticationResponse response = authenticationService.authenticate(personne);
            logger.info("Login successful for user: {}", personne.getUsername());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Login failed for user {}: {}", personne.getUsername(), e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .body(new AuthenticationResponse(null, "Login failed: " + e.getMessage(), null, "")); // Provide a role value
        }
    }
}
