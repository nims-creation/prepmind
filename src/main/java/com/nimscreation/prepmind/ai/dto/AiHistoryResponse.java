package com.nimscreation.prepmind.ai.dto;

import com.nimscreation.prepmind.ai.enums.AiUseCase;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AiHistoryResponse {

    private Long id;
    private AiUseCase useCase;
    private String prompt;
    private String response;
    private LocalDateTime requestedAt;
    private long responseTimeMs;
    private boolean success;
}
