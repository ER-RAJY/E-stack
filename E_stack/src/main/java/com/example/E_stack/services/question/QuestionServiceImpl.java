package com.example.E_stack.services.question;

import com.example.E_stack.dtos.*;
import com.example.E_stack.entities.Answer;
import com.example.E_stack.entities.Apprenant;
import com.example.E_stack.entities.Question;
import com.example.E_stack.entities.QuestionVote;
import com.example.E_stack.enums.VoteType;
import com.example.E_stack.exeption.ApprenantNotFoundException;
import com.example.E_stack.exeption.QuestionNotFoundException;
import com.example.E_stack.reposeitories.AnswerRepository;
import com.example.E_stack.reposeitories.ApprenantRepository;
import com.example.E_stack.reposeitories.ImageRepository;
import com.example.E_stack.reposeitories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    public static final int SEARCH_RESULT_PER_PAGE = 5;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ApprenantRepository apprenantRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public QuestionDTO addQuestion(QuestionDTO questionDto) {
        validateQuestionDto(questionDto);
        Apprenant apprenant = apprenantRepository.findById(questionDto.getApprenantId())
                .orElseThrow(() -> new ApprenantNotFoundException("Apprenant not found with ID: " + questionDto.getApprenantId()));

        Question question = new Question();
        question.setTitle(questionDto.getTitle());
        question.setBody(questionDto.getBody());
        question.setCreatedDate(new Date());
        question.setTags(questionDto.getTags());
        question.setApprenant(apprenant);

        Question createdQuestion = questionRepository.save(question);

        return convertToDto(createdQuestion);
    }

    @Override
    public AllQuestionResponseDto getAllQuestions(int pageNumber) {
        Sort sort = Sort.by(Sort.Order.desc("createdDate"));
        Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE, sort);
        Page<Question> questionsPage = questionRepository.findAll(paging);

        return new AllQuestionResponseDto(
                questionsPage.getContent().stream().map(Question::getQuestionDto).collect(Collectors.toList()),
                questionsPage.getPageable().getPageNumber(),
                questionsPage.getTotalPages()
        );
    }

    @Override
    public SingleQuestionDto getQuestionById(Long apprenantId, Long questionId) {
        Question existingQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with ID: " + questionId));

        SingleQuestionDto singleQuestionDto = new SingleQuestionDto();
        singleQuestionDto.setQuestionDTO(handleVoteCheck(existingQuestion, apprenantId));
        singleQuestionDto.setAnswerDtoList(getApprovedAnswersForQuestion(questionId));
        return singleQuestionDto;
    }

    @Override
    public AllQuestionResponseDto getAllQuestionsByApprenantId(Long apprenantId, int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
        Page<Question> questionsPage = questionRepository.findAllByApprenantId(apprenantId, paging);

        return new AllQuestionResponseDto(
                questionsPage.getContent().stream().map(Question::getQuestionDto).collect(Collectors.toList()),
                questionsPage.getPageable().getPageNumber(),
                questionsPage.getTotalPages()
        );
    }

    @Override
    public void deleteQuestionById(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new QuestionNotFoundException("Question not found with ID: " + questionId);
        }
        questionRepository.deleteById(questionId);
    }

    // Private helper methods
    private void validateQuestionDto(QuestionDTO questionDto) {
        if (questionDto == null || questionDto.getTitle() == null || questionDto.getBody() == null || questionDto.getApprenantId() == null) {
            throw new IllegalArgumentException("Question details are incomplete. Title, body, and Apprenant ID are required.");
        }
    }

    private QuestionDTO convertToDto(Question question) {
        QuestionDTO questionDto = new QuestionDTO();
        questionDto.setId(question.getId());
        questionDto.setTitle(question.getTitle());
        questionDto.setBody(question.getBody());
        questionDto.setCreatedDate(question.getCreatedDate());
        questionDto.setTags(question.getTags());
        return questionDto;
    }

    private QuestionDTO handleVoteCheck(Question question, Long apprenantId) {
        QuestionDTO questionDto = question.getQuestionDto();
        questionDto.setVoted(0);
        question.getQuestionVoteList().stream()
                .filter(vote -> vote.getApprenant().getId().equals(apprenantId))
                .findFirst()
                .ifPresent(vote -> questionDto.setVoted(vote.getVoteType() == VoteType.UPVOTE ? 1 : -1));
        return questionDto;
    }

    private List<AnswerDto> getApprovedAnswersForQuestion(Long questionId) {
        List<AnswerDto> answerDtoList = new ArrayList<>();
        List<Answer> answerList = answerRepository.findAllByQuestionId(questionId);
        for (Answer answer : answerList) {
            if (answer.isApproved()) {
                answerDtoList.add(answer.getAnswerDto());
            }
        }
        return answerDtoList;
    }
}
