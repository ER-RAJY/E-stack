package com.example.E_stack.entities;

import com.example.E_stack.enums.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@DiscriminatorValue("ADMIN")
public class Admin extends Personne {

}