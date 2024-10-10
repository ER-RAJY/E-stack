package com.example.E_stack.controller;

import com.example.E_stack.dtos.AnswerDto;
import com.example.E_stack.services.answer.AnswerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/api/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    // Add an answer
    @PostMapping
    public ResponseEntity<AnswerDto> addAnswer(@RequestBody AnswerDto answerDto) {
        try {
            AnswerDto createdAnswerDto = answerService.postAnswer(answerDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswerDto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Respond with a 400 Bad Request
        }
    }
    @GetMapping("/{answerId}")
    public  ResponseEntity<AnswerDto> approuvAnswer(@PathVariable Long answerId){
        AnswerDto approverAnswerDto = answerService.aprouveAnswer(answerId);
        if (approverAnswerDto == null){
              ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(approverAnswerDto);
    }


    // Edit an existing answer
    @PutMapping("/{id}")
    public ResponseEntity<AnswerDto> editAnswer(@PathVariable Long id, @RequestBody AnswerDto answerDto) {
        try {
            AnswerDto updatedAnswerDto = answerService.editAnswer(id, answerDto);
            if (updatedAnswerDto != null) {
                return ResponseEntity.ok(updatedAnswerDto); // 200 OK with updated answer
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Respond with a 400 Bad Request
        }
    }

    // Delete an answer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        try {
            answerService.deleteAnswer(id);
            return ResponseEntity.ok().build(); // Return 200 OK without a body
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // Handle errors appropriately
        }
    }

}
