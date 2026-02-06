package com.nimscreation.prepmind.ai.service;

import com.nimscreation.prepmind.ai.dto.AIRequestDto;
import com.nimscreation.prepmind.ai.dto.AiResponseDto;

public interface AiService {

    AiResponseDto generate(AIRequestDto request);
}
