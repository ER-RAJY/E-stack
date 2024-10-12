package com.example.E_stack.controller;



import com.example.E_stack.dtos.QuestionVoteDto;
import com.example.E_stack.services.vote.VoteService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VoteController {


    private final VoteService voteService;


    @PostMapping("/vote")
    public ResponseEntity<?> addVoteToQuestion(@RequestBody QuestionVoteDto questionVoteDto){
        QuestionVoteDto questionVotedDto = voteService.addVoteToQuestion(questionVoteDto);
        if(questionVotedDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong!");
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(questionVotedDto);
        }

    }
//    @PostMapping("/answer/vote")
//    public ResponseEntity<?> addVoteToAnswer(@PathVariable AnswerVoteDto answerVoteDto){
//      AnswerVoteDto createdAnswerVoteDto =   voteService.addVoteToAnswer(answerVoteDto);
//        if (createdAnswerVoteDto == null)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something went wrong");
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswerVoteDto);
//    }


}
