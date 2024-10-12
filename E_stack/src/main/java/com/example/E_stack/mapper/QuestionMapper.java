package com.example.E_stack.mapper;


import com.example.E_stack.dtos.QuestionDTO;
import com.example.E_stack.entities.Apprenant;
import com.example.E_stack.entities.Personne;
import com.example.E_stack.entities.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public static QuestionDTO toQuestionDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setTitle(question.getTitle());
        questionDTO.setBody(question.getBody());
        questionDTO.setCreatedDate(question.getCreatedDate());
        questionDTO.setApprenantId(question.getApprenant().getId());
        questionDTO.setTags(question.getTags());
        questionDTO.setVoteCount(question.getVoteCount());
        questionDTO.setUsername(question.getApprenant().getNom());
        return questionDTO;
    }

    public static Question toQuestion(QuestionDTO questionDTO, Personne apprenant) {
        Question question = new Question();
        question.setTitle(questionDTO.getTitle());
        question.setBody(questionDTO.getBody());
        question.setCreatedDate(questionDTO.getCreatedDate());
        question.setTags(questionDTO.getTags());
        question.setVoteCount(questionDTO.getVoteCount());
        question.setApprenant((Apprenant) apprenant);
        return question;
    }
}
