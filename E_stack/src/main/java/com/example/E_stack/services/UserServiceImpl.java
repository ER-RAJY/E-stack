package com.example.E_stack.services;


import com.example.E_stack.dtos.SignupRequest;
import com.example.E_stack.dtos.UserDTO;
import com.example.E_stack.entities.User;
import com.example.E_stack.reposeitories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDTO createUser(SignupRequest signupDTO) {
          User user = new User();
          user.setName(signupDTO.getName());
          user.setEmail(signupDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
       User createUser = (User) userRepository.save(user);
       UserDTO createdUserDTO = new UserDTO();
        createdUserDTO.setId(createUser.getId());
       return createdUserDTO;

    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
