package com.example.E_stack.reposeitories;

import com.example.E_stack.entities.QuestionVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {
}