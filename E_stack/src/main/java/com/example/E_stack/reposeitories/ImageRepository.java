package com.example.E_stack.reposeitories;

import com.example.E_stack.entities.Answer;
import com.example.E_stack.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
     Image findByAnswer(Answer answer);
}

