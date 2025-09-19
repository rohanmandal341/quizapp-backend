package com.innobyte.quizapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;  // for now simple, later link to User entity
    private int score;
    private LocalDateTime attemptDate;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}
