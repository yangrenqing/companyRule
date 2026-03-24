package com.companyrule.performance.domain;

import java.time.Instant;

public record PerformancePlan(
        String id,
        String cycleId,
        PerformancePlanStatus status,
        Instant generatedAt,
        String generationMode,
        int targetEmployeeCount
) {
}
