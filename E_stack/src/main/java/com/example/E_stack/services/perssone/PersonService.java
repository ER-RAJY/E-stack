package com.example.E_stack.services.perssone;



import com.example.E_stack.exeption.PersonNotFoundException;
import com.example.E_stack.reposeitories.PerssoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService implements UserDetailsService {

    private final PerssoneRepository personRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personRepository.findByEmail(email).orElseThrow(()->(new PersonNotFoundException("No character has "+ email +" as a email")));
    }
}