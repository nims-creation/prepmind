package com.nimscreation.prepmind.ai.provider;

import com.nimscreation.prepmind.ai.enums.AiUseCase;
import org.springframework.stereotype.Component;

@Component
public class MockAiProvider implements AiProvider {

    @Override
    public String generate(AiUseCase useCase, String prompt) {

        return switch (useCase) {
            case INTERVIEW_QUESTIONS ->
                    "Mock Interview Questions for: " + prompt;
            case MOCK_INTERVIEW ->
                    "Mock Interview Simulation for: " + prompt;
            case RESUME_REVIEW ->
                    "Mock Resume Review for: " + prompt;
            case STUDY_PLAN ->
                    "Mock Study Plan for: " + prompt;
            case QUIZ_GENERATION ->
                    "Mock Quiz generated for: " + prompt;
        };
    }
}

