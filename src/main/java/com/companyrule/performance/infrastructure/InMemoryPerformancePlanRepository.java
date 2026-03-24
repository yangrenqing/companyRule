package com.companyrule.performance.infrastructure;

import com.companyrule.performance.application.PerformancePlanRepository;
import com.companyrule.performance.domain.PerformancePlan;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryPerformancePlanRepository implements PerformancePlanRepository {

    private final Map<String, PerformancePlan> plans = new ConcurrentHashMap<>();

    @Override
    public PerformancePlan save(PerformancePlan plan) {
        plans.put(plan.id(), plan);
        return plan;
    }
}
