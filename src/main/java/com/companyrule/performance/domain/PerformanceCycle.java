package com.companyrule.performance.domain;

import java.time.Instant;

public record PerformanceCycle(
        String id,
        String name,
        String organizationId,
        PerformanceCycleStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}
