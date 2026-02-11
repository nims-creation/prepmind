package com.nimscreation.prepmind.ai.entity;

import com.nimscreation.prepmind.ai.enums.AiUseCase;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    @Enumerated(EnumType.STRING)
    private AiUseCase useCase;

    private LocalDateTime requestedAt;

    private int requestCount;
}
