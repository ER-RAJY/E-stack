package com.example.E_stack.controller;


import com.example.E_stack.dtos.AnswerDto;
import com.example.E_stack.services.answer.AnswerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/api/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;
    @PostMapping
    public ResponseEntity<?> addAnswer(@RequestBody AnswerDto answerDto){
        try {
            AnswerDto createdAnswerDto = answerService.postAnswer(answerDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswerDto);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
