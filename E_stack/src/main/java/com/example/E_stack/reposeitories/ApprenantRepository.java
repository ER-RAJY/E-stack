package com.example.E_stack.reposeitories;


import com.example.E_stack.entities.Apprenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprenantRepository extends JpaRepository<Apprenant, Long> {
    Optional<Apprenant> findById(Long id);
    Optional<Apprenant> findByEmail (String email);

}