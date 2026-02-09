package com.nimscreation.prepmind.ai.config;

import com.nimscreation.prepmind.ai.provider.AiProvider;
import com.nimscreation.prepmind.ai.provider.MockAiProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    @ConditionalOnProperty(
            name = "spring.ai.openai.enabled",
            havingValue = "false",
            matchIfMissing = true
    )
    public AiProvider mockAiProvider() {
        return new MockAiProvider();
    }
}

