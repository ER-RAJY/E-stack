package com.example.E_stack.controller;

import com.example.E_stack.dtos.AllQuestionResponseDto;
import com.example.E_stack.dtos.QuestionDTO;
import com.example.E_stack.dtos.SingleQuestionDto;
import com.example.E_stack.services.question.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<?> postQuestion(@RequestBody QuestionDTO questionDto) {
        QuestionDTO createdQuestionDto = questionService.addQuestion(questionDto);
        if (createdQuestionDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDto);
    }

    @GetMapping("/{pageNumber}")
    public ResponseEntity<AllQuestionResponseDto> getAllQuestions(@PathVariable int pageNumber) {
        AllQuestionResponseDto allQuestionResponseDto = questionService.getAllQuestions(pageNumber);
        return ResponseEntity.ok(allQuestionResponseDto);
    }

    @GetMapping("/{userId}/{questionId}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long userId, @PathVariable Long questionId) {
        SingleQuestionDto singleQuestionDto = questionService.getQuestionById(userId, questionId);
        if (singleQuestionDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }
        return ResponseEntity.ok(singleQuestionDto);
    }

    @GetMapping("/user/{userId}/{pageNumber}")
    public ResponseEntity<AllQuestionResponseDto> getQuestionsByUserId(@PathVariable Long userId, @PathVariable int pageNumber) {
        AllQuestionResponseDto allQuestionResponseDto = questionService.getAllQuestionsByUserId(userId, pageNumber);
        return ResponseEntity.ok(allQuestionResponseDto);
    }
}
