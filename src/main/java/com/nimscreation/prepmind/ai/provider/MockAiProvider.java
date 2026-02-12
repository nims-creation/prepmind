package com.nimscreation.prepmind.ai.provider;

import com.nimscreation.prepmind.ai.dto.AiInternalResponse;
import com.nimscreation.prepmind.ai.enums.AiUseCase;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!openai")
public class MockAiProvider implements AiProvider {

    @Override
    public AiInternalResponse generate(AiUseCase useCase, String prompt) {

        return AiInternalResponse.builder()
                .content("Mock AI response for: " + prompt)
                .promptTokens(10)
                .completionTokens(20)
                .totalTokens(30)
                .estimatedCost(0.0)
                .build();
    }
}
