package com.nimscreation.prepmind.ai.service.impl;

import com.nimscreation.prepmind.ai.dto.AiAnalyticsResponse;
import com.nimscreation.prepmind.ai.dto.AiGlobalAnalyticsResponse;
import com.nimscreation.prepmind.ai.dto.AiHistoryResponse;
import com.nimscreation.prepmind.ai.dto.AiInternalResponse;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final AiProvider aiProvider;
    private final AiRateLimiter rateLimiter;
    private final AiUsageRepository aiUsageRepository;
    private final AiRequestLogRepository aiRequestLogRepository;

    private static final int DAILY_LIMIT = 10;

    // =====================================================
    // GENERATE AI RESPONSE
    // =====================================================
    @Override
    @Transactional
    public String generate(AiUseCase useCase, String prompt) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new RuntimeException("Unauthenticated request");
        }

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

            AiInternalResponse response = aiProvider.generate(useCase, prompt);
            long duration = System.currentTimeMillis() - start;

            log.setResponse(response.getContent());
            log.setPromptTokens(
                    response.getPromptTokens() == null ? null :
                            response.getPromptTokens().longValue()
            );

            log.setCompletionTokens(
                    response.getCompletionTokens() == null ? null :
                            response.getCompletionTokens().longValue()
            );

            log.setTotalTokens(
                    response.getTotalTokens() == null ? null :
                            response.getTotalTokens().longValue()
            );

            log.setEstimatedCost(response.getEstimatedCost());
            log.setResponseTimeMs(duration);
            log.setSuccess(true);

            aiRequestLogRepository.save(log);

            if (!isAdmin) {
                incrementUsage(userEmail);
            }

            return response.getContent();

        } catch (Exception ex) {

            log.setSuccess(false);
            log.setErrorMessage(ex.getMessage());
            log.setResponseTimeMs(System.currentTimeMillis() - start);

            aiRequestLogRepository.save(log);
            throw ex;
        }
    }

    // =====================================================
    // USER HISTORY
    // =====================================================
    @Override
    public Page<AiHistoryResponse> getUserHistory(Pageable pageable) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

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

    // =====================================================
    // GLOBAL ANALYTICS (ADMIN)
    // =====================================================
    @Override
    public AiGlobalAnalyticsResponse getGlobalAnalytics() {

        Object[] result = aiRequestLogRepository.getGlobalAnalytics();

        return new AiGlobalAnalyticsResponse(
                getLong(result[0]),
                getLong(result[1]),
                getLong(result[2]),
                getLong(result[3]),
                getDouble(result[4]),
                getLong(result[5])
        );
    }

    // Optional backward compatibility method
    @Override
    public AiAnalyticsResponse getAnalytics() {

        AiGlobalAnalyticsResponse global = getGlobalAnalytics();

        return AiAnalyticsResponse.builder()
                .totalRequests(global.getTotalRequests())
                .successfulRequests(global.getSuccessfulRequests())
                .failedRequests(global.getFailedRequests())
                .totalTokens(global.getTotalTokens())
                .totalCost(global.getTotalCost())
                .uniqueUsers(global.getUniqueUsers())
                .build();
    }

    // =====================================================
    // DAILY QUOTA MANAGEMENT
    // =====================================================
    private void incrementUsage(String userEmail) {

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

    // =====================================================
    // SAFE TYPE CONVERSION HELPERS
    // =====================================================
    private Long getLong(Object value) {
        return value == null ? 0L : ((Number) value).longValue();
    }

    private Double getDouble(Object value) {
        return value == null ? 0.0 : ((Number) value).doubleValue();
    }
}
