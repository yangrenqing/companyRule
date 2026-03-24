package com.companyrule.performance.application;

import com.companyrule.performance.domain.PerformancePlan;
import java.util.Optional;

public interface PerformancePlanRepository {

    PerformancePlan save(PerformancePlan plan);

    Optional<PerformancePlan> findById(String planId);
}
