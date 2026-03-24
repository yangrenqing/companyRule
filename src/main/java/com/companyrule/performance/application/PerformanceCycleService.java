package com.companyrule.performance.application;

import com.companyrule.performance.api.CreatePerformanceCycleRequest;
import com.companyrule.performance.domain.PerformanceCycle;
import com.companyrule.performance.domain.PerformanceCycleStatus;
import com.companyrule.performance.domain.PerformancePlan;
import com.companyrule.performance.domain.PerformancePlanStatus;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PerformanceCycleService {

    private final PerformanceCycleRepository cycleRepository;
    private final PerformancePlanRepository planRepository;
    private final OrganizationGateway organizationGateway;

    public PerformanceCycleService(
            PerformanceCycleRepository cycleRepository,
            PerformancePlanRepository planRepository,
            OrganizationGateway organizationGateway
    ) {
        this.cycleRepository = cycleRepository;
        this.planRepository = planRepository;
        this.organizationGateway = organizationGateway;
    }

    public PerformanceCycle createCycle(CreatePerformanceCycleRequest request) {
        if (!organizationGateway.organizationExists(request.organizationId())) {
            throw new OrganizationNotFoundException(request.organizationId());
        }

        Instant now = Instant.now();
        PerformanceCycle cycle = new PerformanceCycle(
                UUID.randomUUID().toString(),
                request.name(),
                request.organizationId(),
                PerformanceCycleStatus.DRAFT,
                now,
                now
        );
        return cycleRepository.save(cycle);
    }

    public PerformanceCycle getCycle(String cycleId) {
        return cycleRepository.findById(cycleId)
                .orElseThrow(() -> new CycleNotFoundException(cycleId));
    }

    public PerformancePlan generatePlan(String cycleId) {
        PerformanceCycle cycle = getCycle(cycleId);
        PerformancePlan plan = new PerformancePlan(
                UUID.randomUUID().toString(),
                cycle.id(),
                PerformancePlanStatus.GENERATED,
                Instant.now(),
                "SYNC_STUB",
                organizationGateway.estimateTargetEmployeeCount(cycle.organizationId())
        );
        return planRepository.save(plan);
    }
}
