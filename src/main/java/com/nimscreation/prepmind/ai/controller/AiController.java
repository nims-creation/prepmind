package com.nimscreation.prepmind.ai.controller;

import com.nimscreation.prepmind.ai.dto.AiRequestDto;
import com.nimscreation.prepmind.ai.dto.AiResponseDto;
import com.nimscreation.prepmind.ai.service.AiService;
import com.nimscreation.prepmind.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<AiResponseDto>> generate(
            @Valid @RequestBody AiRequestDto request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "AI response generated",
                        aiService.generate(request)
                )
        );
    }
}
