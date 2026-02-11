package com.nimscreation.prepmind.ai.ratelimiter;

import com.bucket4j.Bandwidth;
import com.bucket4j.Bucket;
import com.bucket4j.Refill;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AiRateLimiter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean allowRequest(String userId, boolean isAdmin) {

        if (isAdmin) return true; // 🚀 ADMIN unlimited

        Bucket bucket = buckets.computeIfAbsent(userId, this::createBucket);
        return bucket.tryConsume(1);
    }

    private Bucket createBucket(String userId) {

        Bandwidth limit = Bandwidth.classic(
                5, // 5 requests
                Refill.intervally(5, Duration.ofMinutes(1))
        );

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
