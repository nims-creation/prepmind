package com.nimscreation.prepmind.ai.provider;

import com.nimscreation.prepmind.ai.enums.AiUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "spring.ai.openai.enabled",
        havingValue = "true"
)
public class OpenAiProvider implements AiProvider {

    private final ChatClient chatClient;

    @Override
    public String generate(AiUseCase useCase, String prompt) {

        String systemPrompt = buildSystemPrompt(useCase);

        return chatClient.prompt()
                .system(systemPrompt)
                .user(prompt)
                .call()
                .content();
    }

    private String buildSystemPrompt(AiUseCase useCase) {

        return switch (useCase) {

            case INTERVIEW_QUESTIONS ->
                    "You are an expert interviewer. Generate high-quality interview questions.";

            case MOCK_INTERVIEW ->
                    "You are an interviewer. Conduct a realistic mock interview.";

            case RESUME_REVIEW ->
                    "You are a resume reviewer. Give clear improvement suggestions.";

            case STUDY_PLAN ->
                    "You are a mentor. Create a structured study plan.";

            case QUIZ_GENERATION ->
                    "You are a quiz generator. Create MCQs with answers.";
        };
    }
}
