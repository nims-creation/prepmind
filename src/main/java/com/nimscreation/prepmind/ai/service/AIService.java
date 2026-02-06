package com.nimscreation.prepmind.ai.service;

import com.nimscreation.prepmind.ai.dto.AIRequestDto;
import com.nimscreation.prepmind.ai.dto.AIResponseDto;

public interface AIService {

    AIResponseDto generate(AIRequestDto request);
}
