package com.example.E_stack.services.question;

import com.example.E_stack.dtos.AllQuestionResponseDto;
import com.example.E_stack.dtos.QuestionDTO;
import com.example.E_stack.dtos.SingleQuestionDto;
import com.example.E_stack.entities.Question;

import java.util.List;

public interface QuestionService {
    QuestionDTO addQuestion(QuestionDTO questionDto);

    AllQuestionResponseDto getAllQuestions(int pageNumber);

    SingleQuestionDto getQuestionById(Long apprenantId, Long questionId);  // Updated from userId to apprenantId

    AllQuestionResponseDto getAllQuestionsByApprenantId(Long apprenantId, int pageNumber);  // Updated from userId to apprenantId
    void deleteQuestionById(Long questionId);

    long countQuestions();
    List<Question> searchByTitleOrBody(String keyword);
}
