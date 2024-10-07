package com.example.E_stack.dtos;


import com.example.E_stack.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApprenantDTO {
    private Long id;
    private String nom;
    private String email;
    private String password;
    private Role role;


}