package com.example.E_stack.mapper;


import com.example.E_stack.dtos.AnswerDto;
import com.example.E_stack.entities.Answer;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapper {

    public AnswerDto toDto(Answer answer) {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setId(answer.getId());
        answerDto.setBody(answer.getBody());
        answerDto.setApprenantId(answer.getApprenant().getId()); // Changed to getApprenant()
        answerDto.setQuestionId(answer.getQuestion().getId());
        answerDto.setUsername(answer.getApprenant().getUsername()); // Changed to getApprenant()
        answerDto.setCreatedDate(answer.getCreatedDate());
        return answerDto;
    }

    public Answer toEntity(AnswerDto answerDto) {
        Answer answer = new Answer();
        answer.setBody(answerDto.getBody());
        // Set other fields if necessary
        return answer;
    }
}
