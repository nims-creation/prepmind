package com.nimscreation.prepmind.ai.service.impl;

import com.nimscreation.prepmind.ai.dto.AiHistoryResponse;
import com.nimscreation.prepmind.ai.entity.AiRequestLog;
import com.nimscreation.prepmind.ai.entity.AiUsage;
import com.nimscreation.prepmind.ai.enums.AiUseCase;
import com.nimscreation.prepmind.ai.provider.AiProvider;
import com.nimscreation.prepmind.ai.ratelimiter.AiRateLimiter;
import com.nimscreation.prepmind.ai.repository.AiRequestLogRepository;
import com.nimscreation.prepmind.ai.repository.AiUsageRepository;
import com.nimscreation.prepmind.ai.service.AiService;
import com.nimscreation.prepmind.exception.AiQuotaExceededException;
import com.nimscreation.prepmind.exception.TooManyRequestsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final AiRequestLogRepository aiRequestLogRepository;



    @Override
    public String generate(AiUseCase useCase, String prompt) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        boolean isAdmin = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!rateLimiter.allowRequest(userEmail, isAdmin)) {
            throw new TooManyRequestsException("Rate limit exceeded.");
        }

        if (!isAdmin) {
            validateDailyQuota(userEmail);
        }

        long start = System.currentTimeMillis();

        AiRequestLog log = AiRequestLog.builder()
                .userEmail(userEmail)
                .useCase(useCase)
                .prompt(prompt)
                .requestedAt(LocalDateTime.now())
                .build();

        try {
            String response = aiProvider.generate(useCase, prompt);

            long duration = System.currentTimeMillis() - start;

            log.setResponse(response);
            log.setResponseTimeMs(duration);
            log.setSuccess(true);

            aiRequestLogRepository.save(log);

            if (!isAdmin) {
                incrementUsage(userEmail, useCase);
            }

            return response;

        } catch (Exception ex) {

            log.setSuccess(false);
            log.setErrorMessage(ex.getMessage());
            log.setResponseTimeMs(System.currentTimeMillis() - start);

            aiRequestLogRepository.save(log);

            throw ex;
        }
    }

    @Override
    public Page<AiHistoryResponse> getUserHistory(Pageable pageable) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        return aiRequestLogRepository
                .findByUserEmail(userEmail, pageable)
                .map(log -> AiHistoryResponse.builder()
                        .id(log.getId())
                        .useCase(log.getUseCase())
                        .prompt(log.getPrompt())
                        .response(log.getResponse())
                        .requestedAt(log.getRequestedAt())
                        .responseTimeMs(log.getResponseTimeMs())
                        .success(log.isSuccess())
                        .build());
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
