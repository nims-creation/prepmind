package com.nimscreation.prepmind.ai.dto;

import com.nimscreation.prepmind.ai.enums.AiUseCase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AiRequest(

        @NotNull
        AiUseCase useCase,

        @NotBlank
        String prompt
) {}
