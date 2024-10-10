package com.example.E_stack.entities;

import com.example.E_stack.enums.VoteType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
public class QuestionVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private VoteType voteType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "apprenant_id", nullable = false)  // Changed to 'apprenant_id'
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Apprenant apprenant;  // Changed from 'personne' to 'apprenant'

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Question question;
}

