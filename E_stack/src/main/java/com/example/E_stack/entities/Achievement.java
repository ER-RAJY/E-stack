package com.example.E_stack.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID of the achievement

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "apprenant_id", nullable = false)
    private Apprenant apprenant; // The learner associated with the achievement

    private Integer points; // Total points earned

    public Achievement() {
        this.points = 0; // Initialize points to 0
    }

    // Method to add points based on activity
    public void addPoints(int pointsToAdd) {
        this.points += pointsToAdd;
    }
}
