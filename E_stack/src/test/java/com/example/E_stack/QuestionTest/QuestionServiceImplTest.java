package com.example.E_stack.QuestionTest;

import com.example.E_stack.dtos.AllQuestionResponseDto;
import com.example.E_stack.dtos.QuestionDTO;
import com.example.E_stack.entities.Apprenant;
import com.example.E_stack.entities.Question;
import com.example.E_stack.reposeitories.AnswerRepository;
import com.example.E_stack.reposeitories.ApprenantRepository;
import com.example.E_stack.reposeitories.QuestionRepository;
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
    private ApprenantRepository apprenantRepository;

    @InjectMocks
    private QuestionServiceImpl questionService;

    private Question question1;
    private Question question2;
    private Apprenant apprenant;

    @BeforeEach
    public void setup() {
        apprenant = new Apprenant();
        apprenant.setId(1L);

        question1 = new Question();
        question1.setId(1L);
        question1.setTitle("Question 1");
        question1.setBody("Body 1");
        question1.setCreatedDate(new Date());
        question1.setApprenant(apprenant);

        question2 = new Question();
        question2.setId(2L);
        question2.setTitle("Question 2");
        question2.setBody("Body 2");
        question2.setCreatedDate(new Date());
        question2.setApprenant(apprenant);
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

    @Test
    public void testGetAllQuestionsByUserId_Success() {
        List<Question> questionsList = Arrays.asList(question1, question2);
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Question> questionPage = new PageImpl<>(questionsList, pageRequest, questionsList.size());

        when(questionRepository.findAllByApprenantId(1L, pageRequest)).thenReturn(questionPage);

        AllQuestionResponseDto responseDto = questionService.getAllQuestionsByApprenantId(1L, 0);

        assertEquals(2, responseDto.getQuestionDTOList().size());
        assertEquals("Question 1", responseDto.getQuestionDTOList().get(0).getTitle());
    }
}
