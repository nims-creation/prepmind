package com.nimscreation.prepmind.ai.provider;

import com.nimscreation.prepmind.ai.enums.AiUseCase;

public interface AiProvider {

    String generate(AiUseCase useCase, String prompt);
}
