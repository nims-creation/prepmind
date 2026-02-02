package com.nimscreation.prepmind.mapper;

import com.nimscreation.prepmind.dto.request.UserRequestDto;
import com.nimscreation.prepmind.dto.response.UserResponseDto;
import com.nimscreation.prepmind.entity.base.User;

public class UserMapper {

    private UserMapper() {
        // utility class
    }

    public static User toEntity(UserRequestDto dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .role(dto.getRole())
                .build();
    }

    public static UserResponseDto toResponse(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
