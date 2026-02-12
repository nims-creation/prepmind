package com.nimscreation.prepmind.ai.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MonthlyUsageResponse {
    private Long totalTokens;
    private Double totalCost;
}
