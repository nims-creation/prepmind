package com.nimscreation.prepmind.controller;

import com.nimscreation.prepmind.dto.auth.AuthResponseDto;
import com.nimscreation.prepmind.dto.auth.LoginRequestDto;
import com.nimscreation.prepmind.dto.auth.RefreshTokenRequestDto;
import com.nimscreation.prepmind.dto.auth.RegisterRequestDto;
import com.nimscreation.prepmind.entity.Enum.Role;
import com.nimscreation.prepmind.entity.base.User;
import com.nimscreation.prepmind.repository.UserRepository;
import com.nimscreation.prepmind.service.AuthService;
import com.nimscreation.prepmind.service.JwtService;
import com.nimscreation.prepmind.service.UserService;
import com.nimscreation.prepmind.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDto>> register(
            @Valid @RequestBody RegisterRequestDto dto) {

        if (userRepository.existsByEmailAndDeletedFalse(dto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(dto.getName().trim())
                .email(dto.getEmail().trim())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.STUDENT)
                .build();

        userRepository.save(user);

        // JWT later → for now dummy tokens
        AuthResponseDto response =
                new AuthResponseDto("ACCESS_TOKEN_LATER", "REFRESH_TOKEN_LATER");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", response));
    }



    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(
            @Valid @RequestBody LoginRequestDto dto) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Login successful",
                        authService.login(dto)
                )
        );
    }


    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponseDto>> refreshToken(
            @RequestParam String refreshToken) {

        if (!jwtService.isTokenValid(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = jwtService.extractEmail(refreshToken);

        User user = userRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtService.generateAccessToken(user);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Token refreshed",
                        new AuthResponseDto(newAccessToken, refreshToken)
                )
        );
    }





}

