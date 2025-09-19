package com.innobyte.quizapp.controller;

import com.innobyte.quizapp.dto.AnswerRequest;
import com.innobyte.quizapp.dto.QuestionResponse;
import com.innobyte.quizapp.dto.QuizResultResponse;
import com.innobyte.quizapp.entity.Question;
import com.innobyte.quizapp.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    // Get quiz questions (without correctOption)
    @GetMapping("/{id}/questions")
    public ResponseEntity<List<QuestionResponse>> getQuizQuestions(@PathVariable Long id) {
        List<Question> questions = quizService.getQuizQuestions(id);
        List<QuestionResponse> response = questions.stream().map(q -> {
            QuestionResponse dto = new QuestionResponse();
            dto.setId(q.getId());
            dto.setQuestionText(q.getQuestionText());
            dto.setOptionA(q.getOptionA());
            dto.setOptionB(q.getOptionB());
            dto.setOptionC(q.getOptionC());
            dto.setOptionD(q.getOptionD());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // Submit all answers & get final score
    @PostMapping("/{id}/submit")
    public ResponseEntity<QuizResultResponse> submitQuiz(
            @PathVariable Long id,
            @RequestBody AnswerRequest request) {
        return ResponseEntity.ok(quizService.submitQuiz(id, request));
    }

    // To Submit one answer at a time and get immediate feedback
    @PostMapping("/{quizId}/questions/{questionId}/answer")
    public ResponseEntity<String> submitSingleAnswer(
            @PathVariable Long quizId,
            @PathVariable Long questionId,
            @RequestBody String chosenOption) {
        return ResponseEntity.ok(quizService.checkSingleAnswer(quizId, questionId, chosenOption));
    }
}
