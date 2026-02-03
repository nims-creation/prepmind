package com.nimscreation.prepmind.controller;

import com.nimscreation.prepmind.dto.request.UpdateUserRequestDto;
import com.nimscreation.prepmind.dto.request.UserRequestDto;
import com.nimscreation.prepmind.dto.response.UserResponseDto;
import com.nimscreation.prepmind.entity.base.User;
import com.nimscreation.prepmind.mapper.UserMapper;
import com.nimscreation.prepmind.service.UserService;
import com.nimscreation.prepmind.util.ApiResponse;
import com.nimscreation.prepmind.dto.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@Validated
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User APIs", description = "User management operations")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(
            @Valid @RequestBody UserRequestDto requestDto) {

        User user = UserMapper.toEntity(requestDto);
        User savedUser = userService.createUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "User created successfully",
                        UserMapper.toResponse(savedUser)
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable Long id) {

        User user = userService.getUserById(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "User fetched successfully",
                        UserMapper.toResponse(user)
                )
        );
    }

    @GetMapping("/by-email")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserByEmail(
            @RequestParam String email) {

        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "User fetched successfully",
                        UserMapper.toResponse(user)
                )
        );

    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserResponseDto>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {

        String[] sortParams = sort.split(",");

        Sort.Direction direction =
                sortParams.length > 1
                        ? Sort.Direction.fromString(sortParams[1])
                        : Sort.Direction.DESC;

        Sort sortObj = Sort.by(direction, sortParams[0]);

        Page<User> usersPage = userService.getAllUsers(
                PageRequest.of(page, size, sortObj)
        );

        PageResponse<UserResponseDto> response =
                new PageResponse<>(
                        usersPage.getContent()
                                .stream()
                                .map(UserMapper::toResponse)
                                .toList(),
                        usersPage.getNumber(),
                        usersPage.getSize(),
                        usersPage.getTotalElements(),
                        usersPage.getTotalPages(),
                        usersPage.isLast()
                );

        return ResponseEntity.ok(
                ApiResponse.success("Users fetched successfully", response)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequestDto dto) {

        User updatedUser = userService.updateUser(id, dto);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "User updated successfully",
                        UserMapper.toResponse(updatedUser)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                ApiResponse.success("User deleted successfully", null)
        );
    }


    @PutMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<UserResponseDto>> restoreUser(@PathVariable Long id) {

        User user = userService.restoreUser(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "User restored successfully",
                        UserMapper.toResponse(user)
                )
        );
    }



}
