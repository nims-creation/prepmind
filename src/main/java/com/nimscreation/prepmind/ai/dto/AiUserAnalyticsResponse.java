package com.nimscreation.prepmind.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AiUserAnalyticsResponse {

    private Long totalRequests;
    private Long totalTokens;
    private Double totalCost;
}
