package com.example.E_stack.services.vote;


import com.example.E_stack.dtos.QuestionVoteDto;
import com.example.E_stack.entities.QuestionVote;
import com.example.E_stack.entities.User;
import com.example.E_stack.enums.VoteType;
import com.example.E_stack.reposeitories.QuestionVoteRepository;
import com.example.E_stack.reposeitories.UserRepository;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService{

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionVoteRepository questionVoteRepository;


    @Override
    public QuestionVoteDto addVoteToQuestion(QuestionVoteDto questionVoteDto) {
        Optional<User> optionalUser = userRepository.findById(questionVoteDto.getUserId());
        Optional<TypePatternQuestions.Question> optionalQuestion = questionRepository.findById(questionVoteDto.getQuestionId());

        if (optionalQuestion.isPresent() && optionalUser.isPresent()){
            QuestionVote questionVote = new QuestionVote();

            TypePatternQuestions.Question existingQuestion = optionalQuestion.get();

            questionVote.setVoteType(questionVoteDto.getVoteType());

            if(questionVote.getVoteType() == VoteType.UPVOTE){
                existingQuestion.setVoteCount(existingQuestion.getVoteCount() + 1);
            }else {
                existingQuestion.setVoteCount(existingQuestion.getVoteCount() - 1);
            }

            questionVote.setQuestion(optionalQuestion.get());
            questionVote.setUser(optionalUser.get());

            questionRepository.save(existingQuestion);

            QuestionVote votedQuestion = questionVoteRepository.save(questionVote);
            QuestionVoteDto questionVotedDto = new QuestionVoteDto();
            questionVotedDto.setId(votedQuestion.getId());
            return questionVotedDto;
        }
        return null;
    }
}
