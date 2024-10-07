package com.example.E_stack.services.vote;

import com.example.E_stack.dtos.QuestionVoteDto;
import com.example.E_stack.entities.Question;
import com.example.E_stack.entities.QuestionVote;
import com.example.E_stack.entities.Apprenant; // Import the Apprenant entity
import com.example.E_stack.enums.VoteType;
import com.example.E_stack.reposeitories.ApprenantRepository;
import com.example.E_stack.reposeitories.QuestionRepository;
import com.example.E_stack.reposeitories.QuestionVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ApprenantRepository apprenantRepository; // Change from UserRepository to ApprenantRepository

    @Autowired
    QuestionVoteRepository questionVoteRepository;

    @Override
    public QuestionVoteDto addVoteToQuestion(QuestionVoteDto questionVoteDto) {
        Optional<Apprenant> optionalApprenant = apprenantRepository.findById(questionVoteDto.getUserId()); // Update to Apprenant
        Optional<Question> optionalQuestion = questionRepository.findById(questionVoteDto.getQuestionId());

        if (optionalQuestion.isPresent() && optionalApprenant.isPresent()) {
            QuestionVote questionVote = new QuestionVote();
            Question existingQuestion = optionalQuestion.get();

            questionVote.setVoteType(questionVoteDto.getVoteType());

            // Update the vote count based on the vote type
            if (questionVote.getVoteType() == VoteType.UPVOTE) {
                existingQuestion.setVoteCount(existingQuestion.getVoteCount() + 1);
            } else {
                existingQuestion.setVoteCount(existingQuestion.getVoteCount() - 1);
            }

            questionVote.setQuestion(existingQuestion);
            questionVote.setApprenant(optionalApprenant.get()); // Update to Apprenant

            // Save the updated question and the new vote
            questionRepository.save(existingQuestion);
            QuestionVote votedQuestion = questionVoteRepository.save(questionVote);
            QuestionVoteDto questionVotedDto = new QuestionVoteDto();
            questionVotedDto.setId(votedQuestion.getId());
            return questionVotedDto;
        }
        return null; // Return null if the question or user is not found
    }
}
