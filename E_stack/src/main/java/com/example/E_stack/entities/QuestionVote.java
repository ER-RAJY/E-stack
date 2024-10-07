package com.example.E_stack.entities;

import com.example.E_stack.enums.VoteType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class QuestionVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID for the vote

    @Enumerated(EnumType.STRING)
    private VoteType voteType; // Enum representing the vote type (UPVOTE/DOWNVOTE)

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "apprenant_id", nullable = false) // Associated Apprenant who voted
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Apprenant apprenant; // Reference to Apprenant (instead of User)

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false) // Associated Question for the vote
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Question question; // Reference to the Question entity
}
