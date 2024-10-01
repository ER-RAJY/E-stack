package com.example.E_stack.services.jwt;

import com.example.E_stack.entities.User;
import com.example.E_stack.reposeitories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Write the logic to get user from db
        Optional<User> userOptional=userRepository.findFirstByEmail(email);
        if (userOptional.isEmpty())
            throw new UsernameNotFoundException("Username not found");
        return new org.springframework.security.core.userdetails.User(userOptional.get().getEmail(),userOptional.get().getPassword(),new ArrayList<>());
    }
}
