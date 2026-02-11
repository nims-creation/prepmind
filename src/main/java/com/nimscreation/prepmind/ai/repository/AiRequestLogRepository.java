package com.nimscreation.prepmind.ai.repository;

import com.nimscreation.prepmind.ai.entity.AiRequestLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRequestLogRepository extends JpaRepository<AiRequestLog, Long> {

    Page<AiRequestLog> findByUserEmail(String userEmail, Pageable pageable);
}
