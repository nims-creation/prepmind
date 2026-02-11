package com.nimscreation.prepmind.ai.repository;

import com.nimscreation.prepmind.ai.entity.AiUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AiUsageRepository extends JpaRepository<AiUsage, Long> {

    @Query("SELECT COUNT(u) FROM AiUsage u WHERE u.userEmail = :userEmail AND DATE(u.requestedAt) = :date")
    long countByUserEmailAndDate(@Param("userEmail") String userEmail, @Param("date") LocalDate date);

    Optional<AiUsage> findByUserEmailAndRequestedAt(String userEmail, LocalDateTime requestedAt);
}
