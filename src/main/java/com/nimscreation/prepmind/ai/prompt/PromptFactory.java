package com.nimscreation.prepmind.ai.prompt;

import com.nimscreation.prepmind.ai.enums.AiUseCase;

public final class PromptFactory {

    private PromptFactory() {}

    public static String forUseCase(AiUseCase useCase) {
        return switch (useCase) {

            case INTERVIEW_QUESTIONS ->
                    "You are a senior interviewer. Generate technical and behavioral interview questions based on the given topic.";

            case MOCK_INTERVIEW ->
                    "You are an interviewer conducting a mock interview. Ask one question at a time and wait for the candidate's response.";

            case RESUME_REVIEW ->
                    "You are an ATS resume reviewer. Analyze the resume, give a detailed review with strengths and weaknesses, " +
                            "and then clearly explain why specific improvements are needed with reasoning.";

            case STUDY_PLAN ->
                    "Create a structured and practical study plan with timeline and milestones.";

            case QUIZ_GENERATION ->
                    "Generate multiple quiz questions with correct answers and brief explanations.";

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported AI use case: " + useCase
                    );
        };
    }
}
