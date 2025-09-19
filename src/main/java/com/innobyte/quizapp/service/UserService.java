package com.innobyte.quizapp.service;

import com.innobyte.quizapp.entity.QuizAttempt;
import com.innobyte.quizapp.repository.QuizAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final QuizAttemptRepository attemptRepository;

    public List<QuizAttempt> getUserAttempts(Long userId) {
        return attemptRepository.findByUserId(userId);
    }
}
