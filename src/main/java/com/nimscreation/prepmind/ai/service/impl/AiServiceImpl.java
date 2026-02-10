package com.nimscreation.prepmind.ai.service.impl;

import com.nimscreation.prepmind.ai.enums.AiUseCase;
import com.nimscreation.prepmind.ai.provider.AiProvider;
import com.nimscreation.prepmind.ai.ratelimiter.AiRateLimiter;
import com.nimscreation.prepmind.ai.service.AiService;
import com.nimscreation.prepmind.exception.TooManyRequestsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final AiProvider aiProvider;
    private final AiRateLimiter rateLimiter;

    @Override
    public String generate(AiUseCase useCase, String prompt) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String userId = auth.getName(); // usually email
        boolean isAdmin = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // 🚦 STEP 4 + STEP 6 HERE
        if (!rateLimiter.allowRequest(userId, isAdmin)) {
            throw new TooManyRequestsException(
                    "Rate limit exceeded. Try again after some time."
            );
        }

        return aiProvider.generate(useCase, prompt);
    }
}
