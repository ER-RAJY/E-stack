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
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {
    // Constant to define the number of questions per page
    public static final int SEARCH_RESULT_PER_PAGE = 5;

    // Repositories for data access
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ApprenantRepository apprenantRepository;
    private final ImageRepository imageRepository;

    /**
     * Adds a new question based on the provided QuestionDTO.
     *
     * @param questionDto DTO containing question details (title, body, tags, and Apprenant ID).
     * @return The created QuestionDTO.
     * @throws ApprenantNotFoundException if the Apprenant ID does not exist.
     */
    @Override
    public QuestionDTO addQuestion(QuestionDTO questionDto) {
        validateQuestionDto(questionDto); // Validate the input DTO
        Apprenant apprenant = apprenantRepository.findById(questionDto.getApprenantId())
                .orElseThrow(() -> new ApprenantNotFoundException("Apprenant not found with ID: " + questionDto.getApprenantId()));

        // Create a new Question entity
        Question question = new Question();
        question.setTitle(questionDto.getTitle());
        question.setBody(questionDto.getBody());
        question.setCreatedDate(new Date());
        question.setTags(questionDto.getTags());
        question.setApprenant(apprenant);

        // Save the question to the repository
        Question createdQuestion = questionRepository.save(question);
        return convertToDto(createdQuestion); // Convert and return the created question as DTO
    }

    /**
     * Retrieves a paginated list of all questions.
     *
     * @param pageNumber The page number to fetch (zero-based).
     * @return An AllQuestionResponseDto containing the list of questions and pagination details.
     */
    @Override
    public AllQuestionResponseDto getAllQuestions(int pageNumber) {
        Sort sort = Sort.by(Sort.Order.desc("createdDate")); // Sort by creation date
        Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE, sort);
        Page<Question> questionsPage = questionRepository.findAll(paging); // Fetch questions with pagination

        return new AllQuestionResponseDto(
                questionsPage.getContent().stream().map(Question::getQuestionDto).collect(Collectors.toList()),
                questionsPage.getPageable().getPageNumber(),
                questionsPage.getTotalPages()
        );
    }


    /**
     * Fetches a specific question by its ID along with its answers.
     *
     * @param apprenantId The ID of the Apprenant.
     * @param questionId  The ID of the question to retrieve.
     * @return A SingleQuestionDto containing the question details and its associated answers.
     */
    @Override
    public SingleQuestionDto getQuestionById(Long apprenantId, Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        if (optionalQuestion.isPresent()) {
            SingleQuestionDto singleQuestionDto = new SingleQuestionDto();
            Question existingQuestion = optionalQuestion.get();

            // Check if the Apprenant has voted on this question
            Optional<QuestionVote> optionalQuestionVote = existingQuestion.getQuestionVoteList().stream()
                    .filter(vote -> vote.getApprenant().getId().equals(apprenantId))
                    .findFirst();
            QuestionDTO questionDto = optionalQuestion.get().getQuestionDto();
            questionDto.setVoted(0); // Default voted status

            // Set voted status based on the user's vote
            if (optionalQuestionVote.isPresent()) {
                if (optionalQuestionVote.get().getVoteType().equals(VoteType.UPVOTE)) {
                    questionDto.setVoted(1);
                } else {
                    questionDto.setVoted(-1);
                }
            }

            singleQuestionDto.setQuestionDTO(questionDto);
            List<AnswerDto> answerDtoList = new ArrayList<>();
            List<Answer> answerList = answerRepository.findAllByQuestionId(questionId);
            // Retrieve answers and their associated images
            for (Answer answer : answerList) {
                AnswerDto answerDto = answer.getAnswerDto();
                answerDto.setFile(imageRepository.findByAnswer(answer)); // Attach file to answer DTO
                answerDtoList.add(answerDto);
            }
            singleQuestionDto.setAnswerDtoList(answerDtoList);
            return singleQuestionDto; // Return the question with answers
        }
        return null; // Return null if question not found
    }

    /**
     * Retrieves a paginated list of questions associated with a specific Apprenant.
     *
     * @param apprenantId The ID of the Apprenant.
     * @param pageNumber  The page number to fetch (zero-based).
     * @return An AllQuestionResponseDto containing the list of questions and pagination details.
     */
    @Override
    public AllQuestionResponseDto getAllQuestionsByApprenantId(Long apprenantId, int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE); // Create pageable object
        Page<Question> questionsPage = questionRepository.findAllByApprenantId(apprenantId, paging); // Fetch questions for the given Apprenant

        return new AllQuestionResponseDto(
                questionsPage.getContent().stream().map(Question::getQuestionDto).collect(Collectors.toList()),
                questionsPage.getPageable().getPageNumber(),
                questionsPage.getTotalPages()
        );
    }

    /**
     * Deletes a question by its ID.
     *
     * @param questionId The ID of the question to delete.
     * @throws QuestionNotFoundException if the question does not exist.
     */
    @Override
    public void deleteQuestionById(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new QuestionNotFoundException("Question not found with ID: " + questionId);
        }
        questionRepository.deleteById(questionId); // Delete the question
    }

    /**
     * Counts the total number of questions in the repository.
     *
     * @return The total number of questions.
     */
    @Override
    public long countQuestions() {
        return questionRepository.count(); // Return the count of questions
    }

    @Override
    public List<Question> searchByTitleOrBody(String keyword) {
       return questionRepository.findQuestionsByTitleContaining(keyword);
    }

    // Private helper methods

    /**
     * Validates the provided QuestionDTO to ensure it contains all necessary fields.
     *
     * @param questionDto The DTO to validate.
     * @throws IllegalArgumentException if any required fields are missing.
     */
    private void validateQuestionDto(QuestionDTO questionDto) {
        if (questionDto == null || questionDto.getTitle() == null || questionDto.getBody() == null || questionDto.getApprenantId() == null) {
            throw new IllegalArgumentException("Question details are incomplete. Title, body, and Apprenant ID are required.");
        }
    }

    /**
     * Converts a Question entity to its corresponding QuestionDTO.
     *
     * @param question The question entity to convert.
     * @return The converted QuestionDTO.
     */
    private QuestionDTO convertToDto(Question question) {
        QuestionDTO questionDto = new QuestionDTO();
        questionDto.setId(question.getId());
        questionDto.setTitle(question.getTitle());
        questionDto.setBody(question.getBody());
        questionDto.setCreatedDate(question.getCreatedDate());
        questionDto.setTags(question.getTags());
        return questionDto; // Return the DTO
    }

    /**
     * Checks if a specified Apprenant has voted on a question and sets the vote status in the QuestionDTO.
     *
     * @param question   The question to check votes for.
     * @param apprenantId The ID of the Apprenant.
     * @return The updated QuestionDTO with vote status.
     */
    private QuestionDTO handleVoteCheck(Question question, Long apprenantId) {
        QuestionDTO questionDto = question.getQuestionDto();
        questionDto.setVoted(0); // Default voted status
        question.getQuestionVoteList().stream()
                .filter(vote -> vote.getApprenant().getId().equals(apprenantId))
                .findFirst()
                .ifPresent(vote -> questionDto.setVoted(vote.getVoteType() == VoteType.UPVOTE ? 1 : -1));
        return questionDto; // Return the updated DTO
    }

    /**
     * Retrieves a list of approved answers for a given question.
     *
     * @param questionId The ID of the question.
     * @return A list of AnswerDto containing only approved answers.
     */
    private List<AnswerDto> getApprovedAnswersForQuestion(Long questionId) {
        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);
        return answers.stream()
                .filter(Answer::isApproved)
                .map(Answer::getAnswerDto)
                .collect(Collectors.toList()); // Return approved answers as DTOs
    }
}
