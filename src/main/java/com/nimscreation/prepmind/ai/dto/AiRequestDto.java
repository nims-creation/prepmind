package com.nimscreation.prepmind.ai.dto;

import com.nimscreation.prepmind.ai.enums.AiUseCase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AiRequestDto {

    @NotNull
    private AiUseCase useCase;

    @NotBlank
    private String prompt;
}