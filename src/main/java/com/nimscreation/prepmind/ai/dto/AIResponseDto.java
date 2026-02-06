package com.nimscreation.prepmind.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AIResponseDto {

    private String answer;
    private String model;   // mock / gpt-4 / etc
}
