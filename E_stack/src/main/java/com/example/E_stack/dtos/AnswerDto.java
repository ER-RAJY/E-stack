package com.example.E_stack.dtos;

import com.example.E_stack.entities.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private Long id; // ID of the answer
    private String body; // Content of the answer
    private Date createdDate; // Date when the answer was created
    private Long questionId; // ID of the question
    private Long apprenantId; // ID of the user
    private String username; // Username of the user
    private Image file; // Optional image file associated with the answer (if applicable)
    private boolean approved = false;
}
