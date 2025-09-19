package com.innobyte.quizapp.controller;

import com.innobyte.quizapp.entity.QuizAttempt;
import com.innobyte.quizapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}/attempts")
    public ResponseEntity<List<QuizAttempt>> getUserAttempts(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserAttempts(id));
    }
}
