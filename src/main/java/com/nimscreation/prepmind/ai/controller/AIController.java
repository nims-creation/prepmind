package com.nimscreation.prepmind.ai.controller;

import com.nimscreation.prepmind.ai.dto.AIRequestDto;
import com.nimscreation.prepmind.ai.dto.AIResponseDto;
import com.nimscreation.prepmind.ai.service.AIService;
import com.nimscreation.prepmind.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    @PostMapping("/generate")
    public ApiResponse<AIResponseDto> generate(
            @Valid @RequestBody AIRequestDto request) {

        return ApiResponse.success(
                "AI response generated",
                aiService.generate(request)
        );
    }
}

