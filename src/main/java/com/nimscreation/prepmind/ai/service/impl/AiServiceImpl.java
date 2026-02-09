package com.nimscreation.prepmind.ai.service.impl;

import com.nimscreation.prepmind.ai.enums.AiUseCase;
import com.nimscreation.prepmind.ai.provider.AiProvider;
import com.nimscreation.prepmind.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final AiProvider aiProvider;

    @Override
    public String generate(AiUseCase useCase, String prompt) {
        return aiProvider.generate(useCase, prompt);
    }
}


