package com.nimscreation.prepmind.ai.config;

import com.nimscreation.prepmind.ai.provider.AiProvider;
import com.nimscreation.prepmind.ai.provider.MockAiProvider;
import com.nimscreation.prepmind.ai.provider.OpenAiProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AiProviderConfig {

    private final ObjectProvider<MockAiProvider> mockProvider;
    private final ObjectProvider<OpenAiProvider> openProvider;

    @Bean
    public AiProvider aiProvider(
            @Value("${spring.ai.openai.enabled:false}") boolean openAiEnabled
    ) {

        if (openAiEnabled) {
            return openProvider.getIfAvailable();
        }

        return mockProvider.getIfAvailable();
    }
}
