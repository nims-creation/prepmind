package com.nimscreation.prepmind.service;

import com.nimscreation.prepmind.entity.base.User;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    String extractEmail(String token);

    boolean isTokenValid(String token);
}

