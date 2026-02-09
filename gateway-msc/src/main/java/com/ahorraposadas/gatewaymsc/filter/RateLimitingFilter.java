package com.ahorraposadas.gatewaymsc.filter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ahorraposadas.gatewaymsc.config.GatewayProperties;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    private final GatewayProperties.RateLimit rateLimit;

    public RateLimitingFilter(GatewayProperties gatewayProperties) {
        this.rateLimit = gatewayProperties.getRateLimit();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String key = resolveClientKey(request);
        Bucket bucket = buckets.computeIfAbsent(key, ignored -> newBucket(rateLimit));

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded");
        }
    }

    private Bucket newBucket(GatewayProperties.RateLimit rateLimit) {
        Duration refillPeriod = rateLimit.getRefillPeriod();
        Refill refill = Refill.intervally(rateLimit.getRefillTokens(), refillPeriod);
        Bandwidth limit = Bandwidth.classic(rateLimit.getCapacity(), refill);
        return Bucket.builder().addLimit(limit).build();
    }

    private String resolveClientKey(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
