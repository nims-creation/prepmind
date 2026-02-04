package com.nimscreation.prepmind.service;

import com.nimscreation.prepmind.dto.auth.AuthResponseDto;
import com.nimscreation.prepmind.dto.auth.LoginRequestDto;

public interface AuthService {
    AuthResponseDto login(LoginRequestDto dto);
}
