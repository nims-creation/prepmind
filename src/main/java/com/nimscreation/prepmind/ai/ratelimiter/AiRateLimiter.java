package com.nimscreation.prepmind.ai.ratelimiter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class AiRateLimiter {

    private final Map<String, Bucket> userBuckets = new ConcurrentHashMap<>();

    public boolean allowRequest(String userEmail, boolean isAdmin) {
        Bucket bucket = userBuckets.computeIfAbsent(userEmail, this::createBucket);

        if (isAdmin) {
            return bucket.tryConsume(1);
        } else {
            return bucket.tryConsume(1);
        }
    }

    private Bucket createBucket(String userEmail) {
        Bandwidth limit = Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }
}