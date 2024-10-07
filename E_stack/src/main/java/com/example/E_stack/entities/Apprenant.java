package com.example.E_stack.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
@DiscriminatorValue("APPRENANT")
public class Apprenant extends Personne {

}