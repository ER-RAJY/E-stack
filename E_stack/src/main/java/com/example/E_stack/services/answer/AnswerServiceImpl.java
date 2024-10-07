package com.example.E_stack.services.answer;

import com.example.E_stack.dtos.AnswerDto;
import com.example.E_stack.entities.Answer;
import com.example.E_stack.entities.Question;
import com.example.E_stack.entities.Apprenant; // Import the Apprenant entity
import com.example.E_stack.reposeitories.AnswerRepository;
import com.example.E_stack.reposeitories.ApprenantRepository;
import com.example.E_stack.reposeitories.QuestionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ApprenantRepository apprenantRepository; // Change from UserRepository to ApprenantRepository

    @Override
    public AnswerDto postAnswer(AnswerDto answerDto) {
        Optional<Apprenant> optionalApprenant = apprenantRepository.findById(answerDto.getApprenantId()); // Update to Apprenant
        Optional<Question> optionalQuestion = questionRepository.findById(answerDto.getQuestionId());

        if (optionalApprenant.isPresent() && optionalQuestion.isPresent()) {
            // Convert DTO to Entity
            Answer answer = new Answer();
            answer.setBody(answerDto.getBody());
            answer.setCreatedDate(new Date());
            answer.setQuestion(optionalQuestion.get());
            answer.setApprenant(optionalApprenant.get()); // Update to Apprenant

            // Save entity
            Answer createdAnswer = answerRepository.save(answer);

            // Convert saved entity to DTO
            AnswerDto createdAnswerDto = mapToDto(createdAnswer);

            return createdAnswerDto;
        }
        return null;
    }

    @Override
    public AnswerDto editAnswer(Long answerId, AnswerDto answerDto) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if (optionalAnswer.isPresent()) {
            Answer existingAnswer = optionalAnswer.get();
            existingAnswer.setBody(answerDto.getBody()); // Update the answer body
            existingAnswer.setCreatedDate(new Date()); // Update createdDate or keep it unchanged if desired

            Answer updatedAnswer = answerRepository.save(existingAnswer);

            // Convert the updated entity back to DTO
            AnswerDto updatedAnswerDto = mapToDto(updatedAnswer);

            return updatedAnswerDto;
        }
        return null;
    }

    @Override
    public void deleteAnswer(Long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if (optionalAnswer.isPresent()) {
            System.out.println("Deleting Answer: " + optionalAnswer.get()); // Print answer being deleted
            answerRepository.deleteById(answerId);
        } else {
            throw new RuntimeException("Answer not found"); // Optional: Handle not found
        }
    }

    // Utility method for mapping entity to DTO
    private AnswerDto mapToDto(Answer answer) {
        AnswerDto dto = new AnswerDto();
        dto.setId(answer.getId());
        dto.setBody(answer.getBody());
        dto.setCreatedDate(answer.getCreatedDate());
        dto.setQuestionId(answer.getQuestion().getId());
        dto.setApprenantId(answer.getApprenant().getId()); // Update to Apprenant
        dto.setUsername(answer.getApprenant().getNom()); // Update to Apprenant
        return dto;
    }

    @Override
    public AnswerDto aprouveAnswer(Long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if (optionalAnswer.isPresent()) {
            Answer answer = optionalAnswer.get();
            answer.setAppeouved(true); // Approve the answer
            Answer updatedAnswer = answerRepository.save(answer); // Save the updated entity

            // Use the mapToDto method to convert the updated entity to a DTO
            return mapToDto(updatedAnswer);
        }
        return null;
    }
}
