package com.example.E_stack.dtos;


import com.example.E_stack.enums.VoteType;
import lombok.Data;

@Data
public class QuestionVoteDto {
    private Long id;

    private VoteType voteType;

    private Long userId;

    private Long questionId;
}
