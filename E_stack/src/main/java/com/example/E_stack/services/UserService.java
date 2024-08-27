package com.example.E_stack.services;


import com.example.E_stack.dtos.SignupRequest;
import com.example.E_stack.dtos.UserDTO;

public interface UserService {

    UserDTO createUser(SignupRequest signupDTO);

    boolean hasUserWithEmail(String email);
}
