package com.nimscreation.prepmind.ai.provider;

import com.nimscreation.prepmind.ai.dto.AiInternalResponse;
import com.nimscreation.prepmind.ai.enums.AiUseCase;
import com.nimscreation.prepmind.ai.prompt.PromptFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("openai")
@RequiredArgsConstructor
public class OpenAiProvider implements AiProvider {

    private final ChatClient chatClient;

    @Override
    public AiInternalResponse generate(AiUseCase useCase, String prompt) {

        String systemPrompt = PromptFactory.forUseCase(useCase);

        String content = chatClient.prompt()
                .system(systemPrompt)
                .user(prompt)
                .call()
                .content();

        return AiInternalResponse.builder()
                .content(content)
                .promptTokens(0)
                .completionTokens(0)
                .totalTokens(0)
                .estimatedCost(0.0)
                .build();
    }
}
