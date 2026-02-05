package com.nimscreation.prepmind.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RefreshTokenRequestDto {

    @NotBlank
    private String refreshToken;
}