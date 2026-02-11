package com.nimscreation.prepmind.ai.prompt;

import com.nimscreation.prepmind.ai.enums.AiUseCase;

public final class PromptFactory {

    private PromptFactory() {}

    public static String forUseCase(AiUseCase useCase) {
        return switch (useCase) {
            case INTERVIEW_QUESTIONS ->
                    "You are a senior interviewer. Generate interview questions.";
            case MOCK_INTERVIEW ->
                    "You are an interviewer conducting a mock interview.";
            case RESUME_REVIEW ->
                    "You are an ATS resume reviewer.";
            case STUDY_PLAN ->
                    "Create a structured study plan.";
            case QUIZ_GENERATION ->
                    "Generate quiz questions with answers.";
        };
    }
}

