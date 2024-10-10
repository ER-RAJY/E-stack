package com.example.E_stack.services.auth;

import com.example.E_stack.entities.Admin;
import com.example.E_stack.entities.Apprenant;
import com.example.E_stack.entities.AuthenticationResponse;
import com.example.E_stack.entities.Personne;
import com.example.E_stack.enums.Role;
import com.example.E_stack.exeption.PersonNotFoundException;
import com.example.E_stack.reposeitories.AdminRepository;
import com.example.E_stack.reposeitories.ApprenantRepository;
import com.example.E_stack.reposeitories.PerssoneRepository;
import com.example.E_stack.services.perssone.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final AdminRepository adminRepository;
    private final ApprenantRepository apprenantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PerssoneRepository perssoneRepository; // Fixed typo here
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(Personne request) {
        try {
            log.debug("Attempting authentication for email: {}", request.getEmail());

            // First find the user
            Personne person = perssoneRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new PersonNotFoundException("No user found with email: " + request.getEmail()));

            // Attempt authentication
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Generate token
            String jwt = jwtService.generateToken(person);
            log.info("Authentication successful for user: {}", request.getEmail());

            // Store the role in the response and set it in the StorageService
            String role = person.getRole().name();
            return new AuthenticationResponse(jwt, "Login successful", person.getId(), role);

        } catch (PersonNotFoundException e) {
            log.warn("Authentication failed - user not found: {}", request.getEmail());
            return new AuthenticationResponse(null, "Invalid email or password", null, ""); // Provide a role value
        } catch (BadCredentialsException e) {
            log.warn("Authentication failed - bad credentials for: {}", request.getEmail());
            return new AuthenticationResponse(null, "Invalid email or password", null, ""); // Provide a role value
        } catch (Exception e) {
            log.error("Authentication error: {}", e.getMessage(), e);
            return new AuthenticationResponse(null, "Authentication failed", null, ""); // Provide a role value
        }
    }


    public AuthenticationResponse registerAdmin(Admin admin) {
        try {
            if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
                return new AuthenticationResponse(null, "Admin already exists", null, ""); // Provide a role value
            }

            Admin newAdmin = new Admin();
            newAdmin.setEmail(admin.getEmail());
            newAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
            newAdmin.setNom(admin.getNom());
            newAdmin.setRole(Role.ADMIN);

            Admin savedAdmin = adminRepository.save(newAdmin);
            String token = jwtService.generateToken(savedAdmin);

            return new AuthenticationResponse(token, "Admin successfully registered", savedAdmin.getId(), savedAdmin.getRole().name()); // Assuming you have a method to get role name
        } catch (Exception e) {
            log.error("Error registering admin: {}", e.getMessage(), e);
            return new AuthenticationResponse(null, "Registration failed", null, ""); // Provide a role value
        }
    }

    public AuthenticationResponse registerApprenant(Apprenant apprenant) {
        try {
            if (apprenantRepository.findByEmail(apprenant.getEmail()).isPresent()) {
                return new AuthenticationResponse(null, "Apprenant already exists", null, ""); // Provide a role value
            }

            Apprenant newApprenant = new Apprenant();
            newApprenant.setEmail(apprenant.getEmail());
            newApprenant.setNom(apprenant.getNom());
            newApprenant.setRole(Role.APPRENANT);
            newApprenant.setPassword(passwordEncoder.encode(apprenant.getPassword()));

            Apprenant savedApprenant = apprenantRepository.save(newApprenant);
            String token = jwtService.generateToken(savedApprenant);

            return new AuthenticationResponse(token, "Apprenant successfully registered", savedApprenant.getId(), savedApprenant.getRole().name()); // Assuming you have a method to get role name
        } catch (Exception e) {
            log.error("Error registering apprenant: {}", e.getMessage(), e);
            return new AuthenticationResponse(null, "Registration failed", null, ""); // Provide a role value
        }
    }
}
