package com.innobyte.quizapp.controller;

import com.innobyte.quizapp.dto.LoginRequest;
import com.innobyte.quizapp.dto.RegisterRequest;
import com.innobyte.quizapp.dto.UserResponse;
import com.innobyte.quizapp.entity.User;
import com.innobyte.quizapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
