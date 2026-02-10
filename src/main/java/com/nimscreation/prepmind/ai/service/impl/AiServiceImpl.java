package com.nimscreation.prepmind.ai.service.impl;

import com.nimscreation.prepmind.ai.enums.AiUseCase;
import com.nimscreation.prepmind.ai.provider.AiProvider;
import com.nimscreation.prepmind.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AiServiceImpl implements AiService {

    private final AiProvider aiProvider;
    private final AiRateLimiter rateLimiter;

    @Override
    public String generate(AiUseCase useCase, String prompt) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String userId = auth.getName();
        boolean isAdmin = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!rateLimiter.allowRequest(userId, isAdmin)) {
            throw new TooManyRequestsException("AI rate limit exceeded");
        }

        return aiProvider.generate(useCase, prompt);
    }
}


