package com.example.E_stack.reposeitories;

import com.example.E_stack.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByApprenantId(Long apprenantId, Pageable pageable);
    List<Question> findQuestionsByTitleContaining(String keyword);

    @Query("select  q from Question q order by q.voteCount DESC ")
    Question findTopByOrderByVoteCountDesc();

}


































//@Query("select q from Question inner join g.tag t where t.name = ?1")
//    @Query("SELECT q FROM Question q WHERE LOWER(q.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(q.body) LIKE LOWER(CONCAT('%', :keyword, '%'))")
//    List<Question> searchByTitleOrBody(@Param("keyword") String keyword);
