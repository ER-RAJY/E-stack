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

    private Long id;

    private String body;

    private Date createdDate;

    private  Long questionId;

    private Long userId;

    private String username;
    private Image file;

}
