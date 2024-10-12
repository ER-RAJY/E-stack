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
import java.util.List;

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

    private boolean approved = false;
    private  int voteCount = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "apprenant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Apprenant apprenant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Question question; // Question to which the answer belongs

//    @OneToMany(mappedBy = "annswer",cascade =CascadeType.ALL )
//    @JsonIgnore
//    private List<AnswerVote> answerVoteList;



    // Convert Answer entity to AnswerDto
    public AnswerDto getAnswerDto() {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setId(id);
        answerDto.setBody(body);
        answerDto.setApprenantId(apprenant.getId()); // Changed from user to apprenant
        answerDto.setApproved(approved);
        answerDto.setQuestionId(question.getId());
        answerDto.setUsername(apprenant.getNom()); // Changed from user.getName() to apprenant.getNom()
        answerDto.setCreatedDate(createdDate);
        return answerDto;
    }
}
