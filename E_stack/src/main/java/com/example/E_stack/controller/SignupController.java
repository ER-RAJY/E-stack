package com.example.E_stack.controller;


import com.example.E_stack.dtos.SignupRequest;
import com.example.E_stack.dtos.UserDTO;
import com.example.E_stack.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class SignupController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody SignupRequest signupRequest){
        if (userService.hasUserWithEmail(signupRequest.getEmail()))
            return new ResponseEntity<>("user already exist with this "+signupRequest.getEmail(), HttpStatus.NOT_ACCEPTABLE);
        UserDTO createdUser = userService.createUser(signupRequest);
        if (createdUser == null) {
            return new ResponseEntity<>("User not created. Please try again later.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
