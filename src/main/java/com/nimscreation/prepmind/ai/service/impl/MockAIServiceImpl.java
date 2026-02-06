package com.nimscreation.prepmind.ai.service.impl;

import com.nimscreation.prepmind.ai.dto.AIRequestDto;
import com.nimscreation.prepmind.ai.dto.AIResponseDto;
import com.nimscreation.prepmind.ai.service.AIService;
import org.springframework.stereotype.Service;

@Service
public class MockAIServiceImpl implements AIService {

    @Override
    public AIResponseDto generate(AIRequestDto request) {

        String response =
                "Mock AI Response for use case: " +
                        request.getUseCase() +
                        "\nPrompt: " +
                        request.getPrompt();

        return new AIResponseDto(response, "mock-ai");
    }
}

