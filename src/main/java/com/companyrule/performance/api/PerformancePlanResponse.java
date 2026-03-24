package com.companyrule.performance.api;

import com.companyrule.performance.domain.PerformancePlan;
import java.time.Instant;

public record PerformancePlanResponse(
        String id,
        String cycleId,
        String status,
        Instant generatedAt,
        String generationMode,
        int targetEmployeeCount
) {
    public static PerformancePlanResponse from(PerformancePlan plan) {
        return new PerformancePlanResponse(
                plan.id(),
                plan.cycleId(),
                plan.status().name(),
                plan.generatedAt(),
                plan.generationMode(),
                plan.targetEmployeeCount()
        );
    }
}
