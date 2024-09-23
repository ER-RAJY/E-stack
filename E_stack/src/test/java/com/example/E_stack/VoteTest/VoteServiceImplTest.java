package com.example.E_stack.VoteTest;

import com.example.E_stack.dtos.QuestionVoteDto;
import com.example.E_stack.entities.Question;
import com.example.E_stack.entities.QuestionVote;
import com.example.E_stack.entities.User;
import com.example.E_stack.enums.VoteType;
import com.example.E_stack.reposeitories.QuestionRepository;
import com.example.E_stack.reposeitories.QuestionVoteRepository;
import com.example.E_stack.reposeitories.UserRepository;
import com.example.E_stack.services.vote.VoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoteServiceImplTest {

    @InjectMocks
    private VoteServiceImpl voteService;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuestionVoteRepository questionVoteRepository;

    private QuestionVoteDto questionVoteDto;
    private User user;
    private Question question;
    private QuestionVote questionVote;

    @BeforeEach
    void setUp() {
        // Initialize a sample QuestionVoteDto object
        questionVoteDto = new QuestionVoteDto();
        questionVoteDto.setUserId(1L);
        questionVoteDto.setQuestionId(1L);
        questionVoteDto.setVoteType(VoteType.UPVOTE);

        // Create mock User and Question entities
        user = new User();
        user.setId(1L);

        question = new Question();
        question.setId(1L);
        question.setVoteCount(0);

        questionVote = new QuestionVote();
        questionVote.setId(1L);
        questionVote.setUser(user);
        questionVote.setQuestion(question);
        questionVote.setVoteType(VoteType.UPVOTE);
    }

    @Test
    void testAddVoteToQuestion() {
        // Set up mock behavior
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(questionVoteRepository.save(any(QuestionVote.class))).thenReturn(questionVote);
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        // Call the service method
        QuestionVoteDto result = voteService.addVoteToQuestion(questionVoteDto);

        // Verify the results
        assertNotNull(result);
        assertEquals(1L, result.getId());

        // Check if the vote count is incremented correctly
        assertEquals(1, question.getVoteCount());

        // Verify interactions with the repositories
        verify(userRepository, times(1)).findById(1L);
        verify(questionRepository, times(1)).findById(1L);
        verify(questionVoteRepository, times(1)).save(any(QuestionVote.class));
        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    void testAddVoteToQuestion_UserOrQuestionNotFound() {
        // Set up mock behavior for user not found
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method
        QuestionVoteDto result = voteService.addVoteToQuestion(questionVoteDto);

        // Verify that result is null when the user is not found
        assertEquals(null, result);

        // Set up mock behavior for question not found
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method again
        result = voteService.addVoteToQuestion(questionVoteDto);

        // Verify that result is null when the question is not found
        assertEquals(null, result);

        // Verify that the repositories were called the correct number of times
        verify(userRepository, times(2)).findById(1L);
        verify(questionRepository, times(2)).findById(1L);
    }
}
