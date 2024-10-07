package com.example.E_stack.reposeitories;

import com.example.E_stack.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByApprenantId(Long apprenantId, Pageable pageable); // Updated from userId to apprenantId
}
