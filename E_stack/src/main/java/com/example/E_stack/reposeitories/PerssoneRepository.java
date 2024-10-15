package com.example.E_stack.reposeitories;


import com.example.E_stack.entities.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerssoneRepository extends JpaRepository<Personne, Long> {
      Optional<Personne> findByEmail(String email);
}