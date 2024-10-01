package com.example.E_stack.entities;

import com.example.E_stack.dtos.AnswerDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID of the answer

    @Lob
    @Column(name = "body", length = 512)
    private String body; // Content of the answer

    private Date createdDate; // Date when the answer was created

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user; // User who posted the answer

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Question question; // Question to which the answer belongs

    // Convert Answer entity to AnswerDto
    public AnswerDto getAnswerDto() {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setId(id);
        answerDto.setBody(body);
        answerDto.setUserId(user.getId());
        answerDto.setQuestionId(question.getId());
        answerDto.setUsername(user.getName());
        answerDto.setCreatedDate(createdDate);
        return answerDto;
    }
}
