package com.companyrule.performance.application;

import com.companyrule.performance.domain.PerformanceCycle;
import java.util.Optional;

public interface PerformanceCycleRepository {

    PerformanceCycle save(PerformanceCycle cycle);

    Optional<PerformanceCycle> findById(String cycleId);
}
