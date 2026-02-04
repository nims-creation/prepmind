package com.nimscreation.prepmind.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDto {

    private final String accessToken;
    private final String refreshToken;
}
