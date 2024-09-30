package com.example.E_stack.QuestionTest;

import com.example.E_stack.dtos.AllQuestionResponseDto;
import com.example.E_stack.dtos.QuestionDTO;
import com.example.E_stack.dtos.SingleQuestionDto;
import com.example.E_stack.entities.Question;
import com.example.E_stack.entities.User;
import com.example.E_stack.reposeitories.AnswerRepository;
import com.example.E_stack.reposeitories.QuestionRepository;
import com.example.E_stack.reposeitories.UserRepository;
import com.example.E_stack.services.question.QuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceImplTest {

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private QuestionServiceImpl questionService;

    private Question question1;
    private Question question2;
    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);

        question1 = new Question();
        question1.setId(1L);
        question1.setTitle("Question 1");
        question1.setBody("Body 1");
        question1.setCreatedDate(new Date());
        question1.setUser(user);

        question2 = new Question();
        question2.setId(2L);
        question2.setTitle("Question 2");
        question2.setBody("Body 2");
        question2.setCreatedDate(new Date());
        question2.setUser(user);
    }

//    @Test
//    public void testAddQuestion_Success() {
//        QuestionDTO questionDto = new QuestionDTO();
//        questionDto.setTitle("New Question");
//        questionDto.setBody("New Question Body");
//        questionDto.setUserId(1L);
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(questionRepository.save(question1)).thenReturn(question1);
//
//        QuestionDTO createdQuestionDto = questionService.addQuestion(questionDto);
//
//        assertNotNull(createdQuestionDto);
//        assertEquals("New Question", createdQuestionDto.getTitle());
//    }

    @Test
    public void testAddQuestion_UserNotFound() {
        QuestionDTO questionDto = new QuestionDTO();
        questionDto.setTitle("New Question");
        questionDto.setBody("New Question Body");
        questionDto.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        QuestionDTO createdQuestionDto = questionService.addQuestion(questionDto);

        assertNull(createdQuestionDto);
    }

    @Test
    public void testGetAllQuestions_Success() {
        List<Question> questionsList = Arrays.asList(question1, question2);
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("createdDate")));
        Page<Question> questionPage = new PageImpl<>(questionsList, pageRequest, questionsList.size());

        when(questionRepository.findAll(pageRequest)).thenReturn(questionPage);

        AllQuestionResponseDto responseDto = questionService.getAllQuestions(0);

        assertEquals(2, responseDto.getQuestionDTOList().size());
        assertEquals("Question 1", responseDto.getQuestionDTOList().get(0).getTitle());
    }
//
//    @Test
//    public void testGetQuestionById_Success() {
//        when(questionRepository.findById(1L)).thenReturn(Optional.of(question1));
//
//        // Assuming getQuestionById is complete, uncomment below lines to test
//        SingleQuestionDto singleQuestionDto = questionService.getQuestionById(1L, 1L);
//
//        assertNull(singleQuestionDto); // As the method is commented out in actual code
//    }

    @Test
    public void testGetAllQuestionsByUserId_Success() {
        List<Question> questionsList = Arrays.asList(question1, question2);
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Question> questionPage = new PageImpl<>(questionsList, pageRequest, questionsList.size());

        when(questionRepository.findAllByUserId(1L, pageRequest)).thenReturn(questionPage);

        AllQuestionResponseDto responseDto = questionService.getAllQuestionsByUserId(1L, 0);

        assertEquals(2, responseDto.getQuestionDTOList().size());
        assertEquals("Question 1", responseDto.getQuestionDTOList().get(0).getTitle());
    }
}
