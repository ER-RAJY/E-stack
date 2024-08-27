package com.example.E_stack.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for auto-increment behavior
    private Long id;
    private String name;
    private String email;
    private String password;
}
