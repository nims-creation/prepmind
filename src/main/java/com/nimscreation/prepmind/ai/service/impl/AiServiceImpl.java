package com.nimscreation.prepmind.ai.service.impl;

import com.nimscreation.prepmind.ai.entity.AiUsage;
import com.nimscreation.prepmind.ai.enums.AiUseCase;
import com.nimscreation.prepmind.ai.provider.AiProvider;
import com.nimscreation.prepmind.ai.ratelimiter.AiRateLimiter;
import com.nimscreation.prepmind.ai.repository.AiUsageRepository;
import com.nimscreation.prepmind.ai.service.AiService;
import com.nimscreation.prepmind.exception.AiQuotaExceededException;
import com.nimscreation.prepmind.exception.TooManyRequestsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final AiProvider aiProvider;
    private final AiRateLimiter rateLimiter;
    private final AiUsageRepository aiUsageRepository;
    private static final int DAILY_LIMIT = 10;


    @Override
    public String generate(AiUseCase useCase, String prompt) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String userEmail = auth.getName();

        boolean isAdmin = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // Rate limiting (per minute)
        if (!rateLimiter.allowRequest(userEmail, isAdmin)) {
            throw new TooManyRequestsException(
                    "Rate limit exceeded. Try again after some time."
            );
        }

        // Daily quota (skip for admin)
        if (!isAdmin) {
            validateDailyQuota(userEmail);
        }

        // Call AI provider
        String response = aiProvider.generate(useCase, prompt);

        // Increment usage AFTER successful AI call
        if (!isAdmin) {
            incrementUsage(userEmail, useCase);
        }

        return response;
    }

    private void incrementUsage(String userEmail, AiUseCase useCase) {

        LocalDate today = LocalDate.now();

        AiUsage usage = aiUsageRepository
                .findByUserEmailAndUsageDate(userEmail, today)
                .orElse(
                        AiUsage.builder()
                                .userEmail(userEmail)
                                .usageDate(today)
                                .requestCount(0)
                                .build()
                );

        usage.setRequestCount(usage.getRequestCount() + 1);
        usage.setLastRequestedAt(LocalDateTime.now());

        aiUsageRepository.save(usage);
    }

    private void validateDailyQuota(String userEmail) {

        LocalDate today = LocalDate.now();

        AiUsage usage = aiUsageRepository
                .findByUserEmailAndUsageDate(userEmail, today)
                .orElse(null);

        if (usage != null && usage.getRequestCount() >= DAILY_LIMIT) {
            throw new AiQuotaExceededException("Daily AI quota exceeded");
        }
    }
}
