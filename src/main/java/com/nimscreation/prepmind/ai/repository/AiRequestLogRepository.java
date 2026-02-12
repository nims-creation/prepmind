package com.nimscreation.prepmind.ai.repository;

import com.nimscreation.prepmind.ai.entity.AiRequestLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AiRequestLogRepository extends JpaRepository<AiRequestLog, Long> {

    // USER HISTORY

    Page<AiRequestLog> findByUserEmail(String userEmail, Pageable pageable);



    // GLOBAL ANALYTICS (ADMIN)
    @Query("""
        SELECT
            COUNT(a),
            SUM(CASE WHEN a.success = true THEN 1 ELSE 0 END),
            SUM(CASE WHEN a.success = false THEN 1 ELSE 0 END),
            COALESCE(SUM(a.totalTokens), 0),
            COALESCE(SUM(a.estimatedCost), 0),
            COUNT(DISTINCT a.userEmail)
        FROM AiRequestLog a
    """)
    Object[] getGlobalAnalytics();

    // PER USER ANALYTICS (ADMIN)
    @Query("""
        SELECT
            COUNT(a),
            COALESCE(SUM(a.totalTokens), 0),
            COALESCE(SUM(a.estimatedCost), 0)
        FROM AiRequestLog a
        WHERE a.userEmail = :email
    """)
    Object[] getUserAnalytics(@Param("email") String email);


    // LAST 7 DAYS TREND
    @Query("""
        SELECT
            DATE(a.requestedAt),
            COUNT(a),
            COALESCE(SUM(a.totalTokens), 0)
        FROM AiRequestLog a
        WHERE a.requestedAt >= :startDate
        GROUP BY DATE(a.requestedAt)
        ORDER BY DATE(a.requestedAt)
    """)
    List<Object[]> getLast7DaysStats(@Param("startDate") LocalDateTime startDate);

}
