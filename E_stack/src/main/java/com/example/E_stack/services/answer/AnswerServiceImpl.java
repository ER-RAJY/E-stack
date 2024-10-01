package com.example.E_stack.services.answer;


import com.example.E_stack.dtos.AnswerDto;
import com.example.E_stack.entities.Answer;
import com.example.E_stack.entities.Question;
import com.example.E_stack.entities.User;
import com.example.E_stack.reposeitories.AnswerRepository;
import com.example.E_stack.reposeitories.QuestionRepository;
import com.example.E_stack.reposeitories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Override
    public AnswerDto postAnswer(AnswerDto answerDto) {
        Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
        Optional<Question> optionalQuestion = questionRepository.findById(answerDto.getQuestionId());

        if (optionalUser.isPresent() && optionalQuestion.isPresent()) {
            Answer answer = new Answer();
            answer.setBody(answerDto.getBody());
            answer.setCreatedDate(new Date()); // Consider using @PrePersist in the Answer entity
            answer.setQuestion(optionalQuestion.get());
            answer.setUser(optionalUser.get());

            Answer createdAnswer = answerRepository.save(answer);

            // Convert the created Answer entity to AnswerDto
            return createdAnswer.getAnswerDto(); // Assuming you have a getAnswerDto() method in Answer entity
        }

        // Consider throwing an exception instead of returning null
        throw new RuntimeException("User or Question not found");
    }
}