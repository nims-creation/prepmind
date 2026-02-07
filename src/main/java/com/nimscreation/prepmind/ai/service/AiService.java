package com.nimscreation.prepmind.ai.service;

import com.nimscreation.prepmind.ai.dto.AiRequestDto;
import com.nimscreation.prepmind.ai.dto.AiResponseDto;

public interface AiService {

    AiResponseDto generate(AiRequestDto request);
}
