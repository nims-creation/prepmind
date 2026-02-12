package com.nimscreation.prepmind.ai.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiAnalyticsResponse {

    private Long totalRequests;
    private Long successfulRequests;
    private Long failedRequests;
    private Long totalTokens;
    private Double totalCost;
    private Long uniqueUsers;
}
