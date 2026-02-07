package com.nimscreation.prepmind.ai.service.impl;

import com.nimscreation.prepmind.ai.dto.AiRequestDto;
import com.nimscreation.prepmind.ai.dto.AiResponseDto;
import com.nimscreation.prepmind.ai.provider.AiProvider;
import com.nimscreation.prepmind.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final AiProvider aiProvider;

    @Override
    public AiResponseDto generate(AiRequestDto request) {

        String output =
                aiProvider.generate(
                        request.getUseCase(),
                        request.getPrompt()
                );

        return new AiResponseDto(output);
    }
}

