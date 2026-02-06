package com.nimscreation.prepmind.ai.dto;

import com.nimscreation.prepmind.ai.enums.AIUseCase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AIRequestDto {

    @NotNull
    private AIUseCase useCase;

    @NotBlank
    private String prompt;
}
