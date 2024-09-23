package com.example.E_stack.AnswerServiceImplTest;

import com.example.E_stack.dtos.AnswerDto;
import com.example.E_stack.entities.Answer;
import com.example.E_stack.entities.Question;
import com.example.E_stack.entities.User;
import com.example.E_stack.reposeitories.AnswerRepository;
import com.example.E_stack.reposeitories.QuestionRepository;
import com.example.E_stack.reposeitories.UserRepository;
import com.example.E_stack.services.answer.AnswerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceImplTest {

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AnswerServiceImpl answerService;

    private AnswerDto answerDto;
    private User user;
    private Question question;

    @BeforeEach
    void setUp() {
        // Initialize the DTO object
        answerDto = new AnswerDto();
        answerDto.setBody("Sample answer body");
        answerDto.setUserId(1L);
        answerDto.setQuestionId(1L);

        // Initialize the User and Question objects
        user = new User();
        user.setId(1L);

        question = new Question();
        question.setId(1L);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostAnswer_Success() {
        // Mock the repositories
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(answerRepository.save(any(Answer.class))).thenAnswer(invocation -> {
            Answer answer = invocation.getArgument(0);
            answer.setId(1L); // Mock the save operation to return an Answer with an ID
            return answer;
        });

        // Call the method to test
        AnswerDto result = answerService.postAnswer(answerDto);

        // Verify the result
        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    void testPostAnswer_UserNotFound() {
        // Mock the user repository to return an empty Optional
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the method to test
        AnswerDto result = answerService.postAnswer(answerDto);

        // Verify the result
        assertNull(result);
    }

    @Test
    void testPostAnswer_QuestionNotFound() {
        // Mock the user repository to return a valid user
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Mock the question repository to return an empty Optional
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the method to test
        AnswerDto result = answerService.postAnswer(answerDto);

        // Verify the result
        assertNull(result);
    }
}
