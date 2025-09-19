package com.innobyte.quizapp.controller;

import com.innobyte.quizapp.dto.QuestionRequest;
import com.innobyte.quizapp.dto.QuizRequest;
import com.innobyte.quizapp.entity.Question;
import com.innobyte.quizapp.entity.Quiz;
import com.innobyte.quizapp.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final QuizService quizService;

    // Create quiz
    @PostMapping("/quizzes")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizRequest request) {
        return ResponseEntity.ok(quizService.createQuiz(request));
    }

    // Get all quizzes
    @GetMapping("/quizzes")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    // Delete quiz
    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok("Quiz deleted successfully.");
    }

    // Add question to quiz
    @PostMapping("/quizzes/{id}/questions")
    public ResponseEntity<Question> addQuestion(@PathVariable Long id, @RequestBody QuestionRequest request) {
        return ResponseEntity.ok(quizService.addQuestion(id, request));
    }

    // Update quiz
    @PutMapping("/quizzes/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody QuizRequest request) {
        return ResponseEntity.ok(quizService.updateQuiz(id, request));
    }

}
