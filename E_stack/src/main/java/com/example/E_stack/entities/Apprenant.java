package com.example.E_stack.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@DiscriminatorValue("APPRENANT")
public class Apprenant extends Personne {

    // List of achievements for the learner
    @OneToMany(mappedBy = "apprenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Achievement> achievements = new ArrayList<>();

//    // List of answer votes
//    @OneToMany(mappedBy = "apprenant", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<AnswerVote> answerVoteList = new ArrayList<>();

    // No-arg constructor
    public Apprenant() {
    }

    // Add achievement to the list
    public void addAchievement(Achievement achievement) {
        achievements.add(achievement);
        achievement.setApprenant(this);
    }

    // Submit an approved answer and add points
    public void submitAnswer(Answer answer) {
        if (answer.isApproved()) {
            Achievement achievement = new Achievement();
            achievement.addPoints(100); // Award points for approved answer
            addAchievement(achievement);
        }
    }
}
