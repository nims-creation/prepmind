package com.nimscreation.prepmind.ai.controller;

import com.nimscreation.prepmind.ai.dto.AiRequest;
import com.nimscreation.prepmind.ai.service.AiService;
import com.nimscreation.prepmind.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/generate")
    public ApiResponse<String> generate(@Valid @RequestBody AiRequest request) {

        return ApiResponse.success(
                aiService.generate(request.useCase(), request.prompt())
        );
    }
}

