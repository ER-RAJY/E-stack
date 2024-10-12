package com.example.E_stack.services.vote;


import com.example.E_stack.dtos.AnswerVoteDto;
import com.example.E_stack.dtos.QuestionVoteDto;

public interface VoteService {
    public QuestionVoteDto addVoteToQuestion(QuestionVoteDto questionVoteDto);
   // AnswerVoteDto addVoteToAnswer(AnswerVoteDto answerVoteDto);
}
