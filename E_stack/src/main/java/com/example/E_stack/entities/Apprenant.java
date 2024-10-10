package com.example.E_stack.entities;

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

    @OneToMany(mappedBy = "apprenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Achievement> achievements = new ArrayList<>(); // List of achievements for the learner


    public Apprenant() {

    }

    public void addAchievement(Achievement achievement) {
        achievements.add(achievement);
        achievement.setApprenant(this);
    }

//    public void completeSession() {
//        Achievement achievement = new Achievement();
//        achievement.addPoints(50); // Award points for session completion
//        addAchievement(achievement);
//    }

    public void submitAnswer(Answer answer) {
        if (answer.isApproved()) {
            Achievement achievement = new Achievement();
            achievement.addPoints(100); // Award points for approved answer
            addAchievement(achievement);
        }
    }
}
