package com.nimscreation.prepmind.service.impl;

import com.nimscreation.prepmind.dto.auth.AuthResponseDto;
import com.nimscreation.prepmind.dto.auth.LoginRequestDto;
import com.nimscreation.prepmind.entity.base.User;
import com.nimscreation.prepmind.repository.UserRepository;
import com.nimscreation.prepmind.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;

    @Override
    public AuthResponseDto login(LoginRequestDto dto) {

        User user = userRepository.findByEmailAndDeletedFalse(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtService.generateAccessToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponseDto(accessToken, refreshToken);
    }
}
