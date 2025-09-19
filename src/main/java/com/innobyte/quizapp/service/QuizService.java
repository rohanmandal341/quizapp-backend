package com.innobyte.quizapp.service;

import com.innobyte.quizapp.dto.AnswerRequest;
import com.innobyte.quizapp.dto.QuestionRequest;
import com.innobyte.quizapp.dto.QuizRequest;
import com.innobyte.quizapp.dto.QuizResultResponse;
import com.innobyte.quizapp.entity.Question;
import com.innobyte.quizapp.entity.Quiz;
import com.innobyte.quizapp.entity.QuizAttempt;
import com.innobyte.quizapp.exception.ResourceNotFoundException;
import com.innobyte.quizapp.repository.QuestionRepository;
import com.innobyte.quizapp.repository.QuizAttemptRepository;
import com.innobyte.quizapp.repository.QuizRepository;
import com.innobyte.quizapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final UserRepository userRepository;

    // Create quiz
    public Quiz createQuiz(QuizRequest request) {
        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setTopic(request.getTopic());
        return quizRepository.save(quiz);
    }

    // Update quiz (NEW)
    public Quiz updateQuiz(Long id, QuizRequest request) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id " + id));
        quiz.setTitle(request.getTitle());
        quiz.setTopic(request.getTopic());
        return quizRepository.save(quiz);
    }

    // Get all quizzes
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    // Delete quiz
    public void deleteQuiz(Long id) {
        if (!quizRepository.existsById(id)) {
            throw new ResourceNotFoundException("Quiz not found with id " + id);
        }
        quizRepository.deleteById(id);
    }

    // Add question to quiz
    public Question addQuestion(Long quizId, QuestionRequest request) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id " + quizId));

        Question q = new Question();
        q.setQuestionText(request.getQuestionText());
        q.setOptionA(request.getOptionA());
        q.setOptionB(request.getOptionB());
        q.setOptionC(request.getOptionC());
        q.setOptionD(request.getOptionD());
        q.setCorrectOption(request.getCorrectOption());
        q.setQuiz(quiz);

        return questionRepository.save(q);
    }

    // Get all questions for a quiz
    public List<Question> getQuizQuestions(Long quizId) {
        return questionRepository.findAll()
                .stream()
                .filter(q -> q.getQuiz().getId().equals(quizId))
                .toList();
    }

    // Submit quiz (final score)
    public QuizResultResponse submitQuiz(Long quizId, AnswerRequest request) {
        List<Question> questions = getQuizQuestions(quizId);

        int score = 0;
        for (Question q : questions) {
            String userAnswer = request.getAnswers().get(q.getId());
            if (userAnswer != null && userAnswer.equalsIgnoreCase(q.getCorrectOption())) {
                score++;
            }
        }

        QuizResultResponse result = new QuizResultResponse();
        result.setScore(score);
        result.setTotalQuestions(questions.size());

        // Save attempt
        QuizAttempt attempt = new QuizAttempt();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findByUsername(username).get().getId();
        attempt.setUserId(userId);

        attempt.setQuiz(quizRepository.findById(quizId).orElseThrow());
        attempt.setScore(score);
        attempt.setAttemptDate(java.time.LocalDateTime.now());
        quizAttemptRepository.save(attempt);

        return result;
    }

    // NEW: Check single answer and give immediate feedback
    public String checkSingleAnswer(Long quizId, Long questionId, String chosenOption) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        if (!question.getQuiz().getId().equals(quizId)) {
            throw new ResourceNotFoundException("Question does not belong to this quiz");
        }

        if (question.getCorrectOption().equalsIgnoreCase(chosenOption.replace("\"", ""))) {
            return "Correct!";
        } else {
            return "Incorrect!";
        }
    }


}
