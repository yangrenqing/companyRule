package com.companyrule.performance.api;

import com.companyrule.performance.domain.PerformanceCycle;
import java.time.Instant;

public record PerformanceCycleResponse(
        String id,
        String name,
        String organizationId,
        String status,
        Instant createdAt,
        Instant updatedAt
) {
    public static PerformanceCycleResponse from(PerformanceCycle cycle) {
        return new PerformanceCycleResponse(
                cycle.id(),
                cycle.name(),
                cycle.organizationId(),
                cycle.status().name(),
                cycle.createdAt(),
                cycle.updatedAt()
        );
    }
}
