package com.companyrule.performance.application;

import com.companyrule.performance.domain.PerformancePlan;

public interface PerformancePlanRepository {

    PerformancePlan save(PerformancePlan plan);
}
