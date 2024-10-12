package com.example.E_stack.services.vote;

import com.example.E_stack.dtos.AnswerVoteDto;
import com.example.E_stack.dtos.QuestionVoteDto;
import com.example.E_stack.entities.*;
import com.example.E_stack.enums.VoteType;

import com.example.E_stack.reposeitories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ApprenantRepository apprenantRepository;

    @Autowired
    private QuestionVoteRepository questionVoteRepository;



    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public QuestionVoteDto addVoteToQuestion(QuestionVoteDto questionVoteDto) {
        Optional<Apprenant> optionalApprenant = apprenantRepository.findById(questionVoteDto.getApprenantId());
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
            questionVote.setApprenant(optionalApprenant.get());

            // Save the updated question and the new vote
            questionRepository.save(existingQuestion);
            QuestionVote votedQuestion = questionVoteRepository.save(questionVote);
            QuestionVoteDto questionVotedDto = new QuestionVoteDto();
            questionVotedDto.setId(votedQuestion.getId());
            return questionVotedDto;
        }
        return null;
    }

//    @Override
//    public AnswerVoteDto addVoteToAnswer(AnswerVoteDto answerVoteDto) {
//        Optional<Apprenant> optionalApprenant = apprenantRepository.findById(answerVoteDto.getApprenantId());
//        Optional<Answer> optionalAnswer = answerRepository.findById(answerVoteDto.getAnswerId());
//
//        if (optionalAnswer.isPresent() && optionalApprenant.isPresent()) {
//            Answer existingAnswer = optionalAnswer.get();
//            Apprenant existingApprenant = optionalApprenant.get();
//            AnswerVote answerVote = new AnswerVote();
//            answerVote.setVoteType(answerVoteDto.getVoteType());
//            answerVote.setAnswer(existingAnswer);
//            answerVote.setApprenant(existingApprenant);
//
//            // Update the vote count based on the vote type
//            if (answerVoteDto.getVoteType() == VoteType.UPVOTE) {
//                existingAnswer.setVoteCount(existingAnswer.getVoteCount() + 1);
//            } else if (answerVoteDto.getVoteType() == VoteType.DOWNVOTE) {
//                existingAnswer.setVoteCount(existingAnswer.getVoteCount() - 1);
//            }
//
//            // Save the updated answer and the new vote
//            answerRepository.save(existingAnswer);
//            AnswerVote votedAnswer = answerVoteRepository.save(answerVote);
//            AnswerVoteDto answerVotedDto = new AnswerVoteDto();
//            answerVotedDto.setId(votedAnswer.getId());
//            return answerVotedDto;
//        }
//        return null; // Return null if the answer or user is not found
//    }
}
