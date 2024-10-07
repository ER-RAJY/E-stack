package com.example.E_stack.entities;

import com.example.E_stack.dtos.QuestionDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Lob
    @Column(name = "body", length = 512)
    private String body;

    private Date createdDate;

    @ElementCollection(targetClass = String.class)
    private List<String> tags;

    private Integer voteCount = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="apprenant_id", nullable = false)  // Updated the join column
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Apprenant apprenant;  // Changed from User to Apprenant

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<QuestionVote> questionVoteList;

    public QuestionDTO getQuestionDto(){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(id);
        questionDTO.setTitle(title);
        questionDTO.setBody(body);
        questionDTO.setCreatedDate(createdDate);
        questionDTO.setApprenantId(apprenant.getId());  // Changed from user to apprenant
        questionDTO.setTags(tags);
        questionDTO.setVoteCount(voteCount);
        questionDTO.setUsername(apprenant.getNom());  // Changed from user.getName() to apprenant.getNom()
        return questionDTO;
    }
}
