package com.example.E_stack.services.question;

import com.example.E_stack.dtos.*;
import com.example.E_stack.entities.Answer;
import com.example.E_stack.entities.Apprenant;
import com.example.E_stack.entities.Question;
import com.example.E_stack.entities.QuestionVote;
import com.example.E_stack.enums.VoteType;
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
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ApprenantRepository apprenantRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public QuestionDTO addQuestion(QuestionDTO questionDto) {
        Optional<Apprenant> optionalApprenant = apprenantRepository.findById(questionDto.getApprenantId());
        if (optionalApprenant.isPresent()) {
            Question question = new Question();
            question.setTitle(questionDto.getTitle());
            question.setBody(questionDto.getBody());
            question.setCreatedDate(new Date());
            question.setTags(questionDto.getTags());
            question.setApprenant(optionalApprenant.get()); // Set Apprenant instead of User
            Question createdQuestion = questionRepository.save(question);

            QuestionDTO createdQuestionDto = new QuestionDTO();
            createdQuestionDto.setId(createdQuestion.getId());
            createdQuestionDto.setTitle(createdQuestion.getTitle());
            return createdQuestionDto;
        }
        return null;
    }

    @Override
    public AllQuestionResponseDto getAllQuestions(int pageNumber) {
        Sort sort = Sort.by(Sort.Order.desc("createdDate"));
        Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE, sort);
        Page<Question> questionsPage = questionRepository.findAll(paging);

        AllQuestionResponseDto allQuestionResponseDto = new AllQuestionResponseDto();
        allQuestionResponseDto.setQuestionDTOList(
                questionsPage.getContent().stream().map(Question::getQuestionDto).collect(Collectors.toList())
        );
        allQuestionResponseDto.setPageNumber(questionsPage.getPageable().getPageNumber());
        allQuestionResponseDto.setTotalPages(questionsPage.getTotalPages());
        return allQuestionResponseDto;
    }

    @Override
    public SingleQuestionDto getQuestionById(Long apprenantId, Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        if (optionalQuestion.isPresent()) {
            SingleQuestionDto singleQuestionDto = new SingleQuestionDto();

            // Handle vote check
            Question existingQuestion = optionalQuestion.get();
            Optional<QuestionVote> optionalQuestionVote = existingQuestion.getQuestionVoteList().stream()
                    .filter(vote -> vote.getApprenant().getId().equals(apprenantId)) // Updated to getApprenant()
                    .findFirst();

            QuestionDTO questionDto = existingQuestion.getQuestionDto();
            questionDto.setVoted(0);
            if (optionalQuestionVote.isPresent()) {
                if (optionalQuestionVote.get().getVoteType().equals(VoteType.UPVOTE)) {
                    questionDto.setVoted(1);
                } else {
                    questionDto.setVoted(-1);
                }
            }

            singleQuestionDto.setQuestionDTO(questionDto);

            // Handle answers
            List<AnswerDto> answerDtoList = new ArrayList<>();
            List<Answer> answerList = answerRepository.findAllByQuestionId(questionId);
            for (Answer answer : answerList) {
                if (answer.isAppeouved()) { // Assuming isApproved() checks if the answer is approved
                    singleQuestionDto.getQuestionDTO().setHasApprovedAnswer(true); // Ensure this method exists
                }
                AnswerDto answerDto = answer.getAnswerDto();
                answerDto.setFile(imageRepository.findByAnswer(answer));
                answerDtoList.add(answerDto);
            }
            singleQuestionDto.setAnswerDtoList(answerDtoList);
            return singleQuestionDto;
        }
        return null;
    }

    @Override
    public AllQuestionResponseDto getAllQuestionsByApprenantId(Long apprenantId, int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
        Page<Question> questionsPage = questionRepository.findAllByApprenantId(apprenantId, paging); // Changed to match Apprenant

        AllQuestionResponseDto allQuestionResponseDto = new AllQuestionResponseDto();
        allQuestionResponseDto.setQuestionDTOList(
                questionsPage.getContent().stream().map(Question::getQuestionDto).collect(Collectors.toList())
        );
        allQuestionResponseDto.setPageNumber(questionsPage.getPageable().getPageNumber());
        allQuestionResponseDto.setTotalPages(questionsPage.getTotalPages());
        return allQuestionResponseDto;
    }
}
