package com.nimscreation.prepmind.ai.repository;

import com.nimscreation.prepmind.ai.entity.AiUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AiUsageRepository extends JpaRepository<AiUsage, Long> {

    Optional<AiUsage> findByUserEmailAndUsageDate(String userEmail, LocalDate usageDate);

}
