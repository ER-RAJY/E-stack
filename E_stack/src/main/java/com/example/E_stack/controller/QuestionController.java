package com.example.E_stack.controller;

import com.example.E_stack.dtos.AllQuestionResponseDto;
import com.example.E_stack.dtos.QuestionDTO;
import com.example.E_stack.dtos.SingleQuestionDto;
import com.example.E_stack.services.question.QuestionService;
import lombok.RequiredArgsConstructor; // Use RequiredArgsConstructor for constructor injection
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor // For constructor injection
public class QuestionController {
    private final QuestionService questionService; // Use final for dependency injection

    @PostMapping("/question")
    public ResponseEntity<?> postQuestion(@RequestBody QuestionDTO questionDto) {
        QuestionDTO createdQuestionDto = questionService.addQuestion(questionDto);
        if (createdQuestionDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create question. Please check your input.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDto);
    }

    @GetMapping("/questions/{pageNumber}")
    public ResponseEntity<AllQuestionResponseDto> getAllQuestions(@PathVariable int pageNumber) {
        AllQuestionResponseDto allQuestionResponseDto = questionService.getAllQuestions(pageNumber);
        return ResponseEntity.ok(allQuestionResponseDto);
    }

    @GetMapping("/question/{apprenantId}/{questionId}") // Changed userId to apprenantId
    public ResponseEntity<?> getQuestionById(@PathVariable Long apprenantId, @PathVariable Long questionId) {
        try {
            SingleQuestionDto singleQuestionDto = questionService.getQuestionById(apprenantId, questionId);
            if (singleQuestionDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found.");
            }
            return ResponseEntity.ok(singleQuestionDto);
        } catch (Exception e) {
            // Log the error using a logging framework
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving the question: " + e.getMessage());
        }
    }

    @GetMapping("/questions/{apprenantId}/{pageNumber}") // Changed userId to apprenantId
    public ResponseEntity<AllQuestionResponseDto> getQuestionsByApprenantId(@PathVariable Long apprenantId, @PathVariable int pageNumber) {
        AllQuestionResponseDto allQuestionResponseDto = questionService.getAllQuestionsByApprenantId(apprenantId, pageNumber);
        return ResponseEntity.ok(allQuestionResponseDto);
    }

    // Add DELETE mapping for deleting a question by its ID
    @DeleteMapping("/question/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        try {
            questionService.deleteQuestionById(questionId); // Call service to delete question
            return ResponseEntity.noContent().build(); // HTTP 204 No Content on successful delete
        } catch (RuntimeException e) {
            // Handle case where question does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
