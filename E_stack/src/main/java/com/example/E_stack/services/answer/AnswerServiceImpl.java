package com.example.E_stack.services.answer;

import com.example.E_stack.dtos.AnswerDto;
import com.example.E_stack.entities.Answer;
import com.example.E_stack.entities.Question;
import com.example.E_stack.entities.Apprenant;

import com.example.E_stack.reposeitories.AnswerRepository;
import com.example.E_stack.reposeitories.ApprenantRepository;
import com.example.E_stack.reposeitories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * Implementation of the AnswerService interface.
 * Provides services for handling operations related to answers such as posting, editing, deleting, and approving answers.
 */
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    // Repositories for interacting with database entities
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ApprenantRepository apprenantRepository;

    /**
     * Posts an answer to a question.
     *
     * @param answerDto The data transfer object containing the answer details.
     * @return AnswerDto representing the created answer.
     * @throws IllegalArgumentException if the required fields are missing.
     * @throws RuntimeException if the apprenant or question is not found.
     */
    @Override
    public AnswerDto postAnswer(AnswerDto answerDto) {
        // Validate the input
        if (answerDto == null || answerDto.getBody() == null || answerDto.getApprenantId() == null || answerDto.getQuestionId() == null) {
            throw new IllegalArgumentException("Answer details are incomplete. Body, Apprenant ID, and Question ID are required.");
        }

        // Fetch the apprenant and question
        Optional<Apprenant> optionalApprenant = apprenantRepository.findById(answerDto.getApprenantId());
        Optional<Question> optionalQuestion = questionRepository.findById(answerDto.getQuestionId());

        if (optionalApprenant.isPresent() && optionalQuestion.isPresent()) {
            // Map DTO to Entity
            Answer answer = new Answer();
            answer.setBody(answerDto.getBody());
            answer.setCreatedDate(new Date());
            answer.setQuestion(optionalQuestion.get());
            answer.setApprenant(optionalApprenant.get());

            // Save the answer
            Answer createdAnswer = answerRepository.save(answer);

            // Handle automatic approval logic (if needed)
            if (createdAnswer.isApproved()) {
                optionalApprenant.get().submitAnswer(createdAnswer);
            }

            // Return the created answer as a DTO
            return mapToDto(createdAnswer);
        }

        throw new RuntimeException("Apprenant or Question not found with the provided IDs.");
    }

    /**
     * Edits an existing answer.
     *
     * @param answerId  The ID of the answer to be edited.
     * @param answerDto The updated answer details.
     * @return AnswerDto representing the updated answer.
     */
    @Override
    public AnswerDto editAnswer(Long answerId, AnswerDto answerDto) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if (optionalAnswer.isPresent()) {
            // Update the answer entity with the new details
            Answer existingAnswer = optionalAnswer.get();
            existingAnswer.setBody(answerDto.getBody());
            existingAnswer.setCreatedDate(new Date()); // Update timestamp

            // Save the updated answer
            Answer updatedAnswer = answerRepository.save(existingAnswer);

            // Return the updated answer as a DTO
            return mapToDto(updatedAnswer);
        }
        return null;
    }

    /**
     * Deletes an existing answer by its ID.
     *
     * @param answerId The ID of the answer to be deleted.
     * @throws RuntimeException if the answer is not found.
     */
    @Override
    public void deleteAnswer(Long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if (optionalAnswer.isPresent()) {
            answerRepository.deleteById(answerId); // Delete the answer
        } else {
            throw new RuntimeException("Answer not found");
        }
    }

    /**
     * Approves an existing answer.
     *
     * @param answerId The ID of the answer to be approved.
     * @return AnswerDto representing the approved answer.
     */
    @Override
    public AnswerDto aprouveAnswer(Long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if (optionalAnswer.isPresent()) {
            // Mark the answer as approved
            Answer answer = optionalAnswer.get();
            answer.setApproved(true);

            // Save the updated answer
            Answer updatedAnswer = answerRepository.save(answer);

            // Return the approved answer as a DTO
            return mapToDto(updatedAnswer);
        }
        return null;
    }

    /**
     * Utility method to map an Answer entity to an AnswerDto.
     *
     * @param answer The Answer entity to be mapped.
     * @return AnswerDto containing the answer details.
     */
    private AnswerDto mapToDto(Answer answer) {
        AnswerDto dto = new AnswerDto();
        dto.setId(answer.getId());
        dto.setBody(answer.getBody());
        dto.setCreatedDate(answer.getCreatedDate());
        dto.setQuestionId(answer.getQuestion().getId());
        dto.setApprenantId(answer.getApprenant().getId());
        dto.setUsername(answer.getApprenant().getNom());
        return dto;
    }
}
