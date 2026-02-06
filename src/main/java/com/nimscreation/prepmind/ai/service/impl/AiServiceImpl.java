package com.nimscreation.prepmind.ai.service.impl;

import com.nimscreation.prepmind.ai.dto.AiRequestDto;
import com.nimscreation.prepmind.ai.dto.AiResponseDto;
import com.nimscreation.prepmind.ai.service.AiService;
import org.springframework.stereotype.Service;

@Service
public class AiServiceImpl implements AiService {

    @Override
    public AiResponseDto generate(AiRequestDto request) {

        String response =
                "Mock AI Response for use case: " +
                        request.getUseCase() +
                        "\nPrompt: " +
                        request.getPrompt();

        return new AiResponseDto(response, "mock-ai");
    }
}

