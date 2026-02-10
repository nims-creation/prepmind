package com.nimscreation.prepmind.ai.ratelimiter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
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
        return Bucket4j.builder()
                .addLimit(Bandwidth.simple(5, Duration.ofMinutes(1)))
                .build();
    }
}
