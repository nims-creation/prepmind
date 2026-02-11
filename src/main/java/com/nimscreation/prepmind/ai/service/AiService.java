package com.nimscreation.prepmind.ai.service;

import com.nimscreation.prepmind.ai.dto.AiHistoryResponse;
import com.nimscreation.prepmind.ai.enums.AiUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AiService {

    String generate(AiUseCase useCase, String prompt);
    Page<AiHistoryResponse> getUserHistory(Pageable pageable);

}
