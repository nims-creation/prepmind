package com.nimscreation.prepmind.ai.provider;

import com.nimscreation.prepmind.ai.dto.AiInternalResponse;
import com.nimscreation.prepmind.ai.enums.AiUseCase;

public interface AiProvider {

    AiInternalResponse generate(AiUseCase useCase, String prompt);
}
