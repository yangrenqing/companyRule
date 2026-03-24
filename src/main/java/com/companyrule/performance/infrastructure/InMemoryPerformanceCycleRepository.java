package com.companyrule.performance.infrastructure;

import com.companyrule.performance.application.PerformanceCycleRepository;
import com.companyrule.performance.domain.PerformanceCycle;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryPerformanceCycleRepository implements PerformanceCycleRepository {

    private final Map<String, PerformanceCycle> cycles = new ConcurrentHashMap<>();

    @Override
    public PerformanceCycle save(PerformanceCycle cycle) {
        cycles.put(cycle.id(), cycle);
        return cycle;
    }

    @Override
    public Optional<PerformanceCycle> findById(String cycleId) {
        return Optional.ofNullable(cycles.get(cycleId));
    }
}
