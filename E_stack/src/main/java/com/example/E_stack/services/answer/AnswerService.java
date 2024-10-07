package com.example.E_stack.services.answer;

import com.example.E_stack.dtos.AnswerDto;

public interface AnswerService {
    AnswerDto postAnswer(AnswerDto answerDto);
    AnswerDto editAnswer(Long answerId, AnswerDto answerDto);
    void deleteAnswer(Long answerId);

    AnswerDto aprouveAnswer(Long answerId);
}
