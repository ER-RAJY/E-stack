package com.example.E_stack.reposeitories;

import com.example.E_stack.entities.QuestionVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {

}