package com.nimscreation.prepmind.ai.repository;

import com.nimscreation.prepmind.ai.entity.AiUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AiUsageRepository extends JpaRepository<AiUsage, Long> {
}

