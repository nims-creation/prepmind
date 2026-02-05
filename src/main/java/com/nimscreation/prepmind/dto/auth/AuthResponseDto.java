package com.nimscreation.prepmind.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
@AllArgsConstructor
public class AuthResponseDto {

    private String accessToken;
    private String refreshToken;
}
