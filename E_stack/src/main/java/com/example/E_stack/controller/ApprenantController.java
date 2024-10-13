package com.example.E_stack.controller;

import com.example.E_stack.dtos.ApprenantDTO;
import com.example.E_stack.dtos.ScoreDto;
import com.example.E_stack.entities.Apprenant;
import com.example.E_stack.entities.AuthenticationResponse;
import com.example.E_stack.services.apprenant.ApprenantsService;
import com.example.E_stack.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/apprenants")
public class ApprenantController {

    private final ApprenantsService apprenantsService;

    private AuthenticationService authenticationService;

    @Autowired
    public ApprenantController(ApprenantsService apprenantsService) {
        this.apprenantsService = apprenantsService;
    }



    // Endpoint to register a new Apprenant
    @PostMapping("/add")
    public ResponseEntity<AuthenticationResponse> registerApprenant(@RequestBody Apprenant apprenant) {
        AuthenticationResponse response = authenticationService.registerApprenant(apprenant);
        if ("Apprentant already exist".equals(response.getMessage())) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countApprenants() {
        long count = apprenantsService.countApprenants();
        return ResponseEntity.ok(count);
    }
    // Get an Apprenant by ID
    @GetMapping("/getApprenants/{id}")
    public ResponseEntity<ApprenantDTO> getApprenantById(@PathVariable Long id) {
        ApprenantDTO apprenantDTO = apprenantsService.getApprenantById(id);
        return new ResponseEntity<>(apprenantDTO, HttpStatus.OK);
    }

    // Get all Apprenants
    @GetMapping("/all")
    public ResponseEntity<List<ApprenantDTO>> getAllApprenants() {
        List<ApprenantDTO> apprenantsDTO = apprenantsService.getAllApprenants();
        return new ResponseEntity<>(apprenantsDTO, HttpStatus.OK);
    }

    // Update an Apprenant by ID
    @PutMapping("/edit/{id}")
    public ResponseEntity<ApprenantDTO> updateApprenant(@PathVariable Long id, @RequestBody ApprenantDTO apprenantDTO) {
        ApprenantDTO updatedApprenant = apprenantsService.updateApprenant(id, apprenantDTO);
        return new ResponseEntity<>(updatedApprenant, HttpStatus.OK);
    }

    // Delete an Apprenant by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteApprenant(@PathVariable Long id) {
        apprenantsService.deleteApprenant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    // Find Apprenant by ID with Optional handling
    @GetMapping("/FindAPPbyId/{id}")
    public ResponseEntity<ApprenantDTO> findApprenantById(@PathVariable Long id) {
        Optional<ApprenantDTO> apprenantDTO = apprenantsService.findById(id);
        return apprenantDTO.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/{id}/score")
    public ResponseEntity<ScoreDto> getScore(@PathVariable Long id) {
        ScoreDto score = apprenantsService.getScoreForApprenant(id);
        return ResponseEntity.ok(score);
    }
}