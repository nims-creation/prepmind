package com.nimscreation.prepmind.ai.entity;

import com.nimscreation.prepmind.ai.enums.AiUseCase;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ai_request_logs")
public class AiRequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    @Enumerated(EnumType.STRING)
    private AiUseCase useCase;

    @Column(columnDefinition = "TEXT")
    private String prompt;

    @Column(columnDefinition = "TEXT")
    private String response;

    private LocalDateTime requestedAt;

    private long responseTimeMs;

    private boolean success;

    private String errorMessage;

    // REQUIRED FOR ANALYTICS

    private Long promptTokens;
    private Long completionTokens;
    private Long totalTokens;
    private Double estimatedCost;
}
