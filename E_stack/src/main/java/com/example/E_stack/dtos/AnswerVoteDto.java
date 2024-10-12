package com.example.E_stack.dtos;

import com.example.E_stack.enums.VoteType;
import lombok.Data;

@Data // This will automatically generate getters, setters, equals, hashCode, and toString methods
public class AnswerVoteDto {
    private Long id;
    private VoteType voteType;
    private Long apprenantId;
    private Long answerId;
}
