package com.example.E_stack.services.question;


import com.example.E_stack.dtos.QuestionDTO;

public interface QuestionService {
    QuestionDTO addQuestion(QuestionDTO questionDto);

    AllQuestionResponseDto getAllQuestions(int pageNumber);

    SingleQuestionDto getQuestionById(Long userId, Long questionId);

    AllQuestionResponseDto getAllQuestionsByUserId(Long userId, int pageNumber);
}
