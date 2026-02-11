package com.nimscreation.prepmind.ai.controller;

import com.nimscreation.prepmind.ai.entity.AiUsage;
import com.nimscreation.prepmind.ai.repository.AiUsageRepository;
import com.nimscreation.prepmind.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/ai")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AiAdminController {

    private final AiUsageRepository aiUsageRepository;

    @GetMapping("/usage")
    public ApiResponse<List<AiUsage>> getAllUsage() {
        return ApiResponse.success("AI usage fetched",
                aiUsageRepository.findAll());
    }
}

