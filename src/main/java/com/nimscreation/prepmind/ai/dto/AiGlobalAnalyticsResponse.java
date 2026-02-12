package com.nimscreation.prepmind.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AiGlobalAnalyticsResponse {

    private Long totalRequests;
    private Long successfulRequests;
    private Long failedRequests;
    private Long totalTokens;
    private Double totalCost;
    private Long uniqueUsers;
}
