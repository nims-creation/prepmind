package com.nimscreation.prepmind.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AiDailyUsageResponse {

    private LocalDate date;
    private Long totalRequests;
    private Long totalTokens;
}
