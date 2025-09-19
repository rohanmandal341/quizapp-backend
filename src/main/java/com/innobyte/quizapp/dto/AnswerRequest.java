package com.innobyte.quizapp.dto;

import lombok.Data;
import java.util.Map;

@Data
public class AnswerRequest {

    private Map<Long, String> answers; // questionId -> chosen option
}
