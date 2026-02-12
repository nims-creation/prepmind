package com.nimscreation.prepmind.ai.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiInternalResponse {

    private String content;
    private Integer promptTokens;
    private Integer completionTokens;
    private Integer totalTokens;
    private Double estimatedCost;
}

