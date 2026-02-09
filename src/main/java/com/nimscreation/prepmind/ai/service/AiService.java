package com.nimscreation.prepmind.ai.service;

import com.nimscreation.prepmind.ai.enums.AiUseCase;

public interface AiService {

    String generate(AiUseCase useCase, String prompt);
}
