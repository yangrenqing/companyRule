package com.companyrule.performance.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.companyrule.performance.api.CreatePerformanceCycleRequest;
import com.companyrule.performance.domain.PerformanceCycle;
import com.companyrule.performance.domain.PerformanceCycleStatus;
import com.companyrule.performance.domain.PerformancePlan;
import com.companyrule.performance.domain.PerformancePlanStatus;
import com.companyrule.performance.infrastructure.InMemoryPerformanceCycleRepository;
import com.companyrule.performance.infrastructure.InMemoryPerformancePlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PerformanceCycleServiceTest {

    private OrganizationGateway organizationGateway;
    private InMemoryPerformancePlanRepository planRepository;
    private PerformanceCycleService service;

    @BeforeEach
    void setUp() {
        organizationGateway = mock(OrganizationGateway.class);
        when(organizationGateway.organizationExists("org-001")).thenReturn(true);
        when(organizationGateway.estimateTargetEmployeeCount("org-001")).thenReturn(7);
        planRepository = new InMemoryPerformancePlanRepository();
        service = new PerformanceCycleService(
                new InMemoryPerformanceCycleRepository(),
                planRepository,
                organizationGateway
        );
    }

    @Test
    void createCycleCanBeReadBackThroughRepositoryBoundary() {
        PerformanceCycle cycle = service.createCycle(new CreatePerformanceCycleRequest(
                "2026 Mid-Year Review",
                "org-001"
        ));

        PerformanceCycle storedCycle = service.getCycle(cycle.id());

        assertThat(storedCycle).isEqualTo(cycle);
        assertThat(storedCycle.id()).isNotBlank();
        assertThat(storedCycle.name()).isEqualTo("2026 Mid-Year Review");
        assertThat(storedCycle.organizationId()).isEqualTo("org-001");
        assertThat(storedCycle.status()).isEqualTo(PerformanceCycleStatus.DRAFT);
        assertThat(storedCycle.createdAt()).isNotNull();
        assertThat(storedCycle.updatedAt()).isEqualTo(storedCycle.createdAt());
    }

    @Test
    void getCycleFailsClearlyWhenRepositoryMisses() {
        assertThatThrownBy(() -> service.getCycle("missing-cycle"))
                .isInstanceOf(CycleNotFoundException.class)
                .hasMessage("Performance cycle not found: missing-cycle");
    }

    @Test
    void createCycleFailsClearlyWhenOrganizationMissing() {
        when(organizationGateway.organizationExists("missing-org")).thenReturn(false);

        assertThatThrownBy(() -> service.createCycle(new CreatePerformanceCycleRequest(
                "2026 Mid-Year Review",
                "missing-org"
        )))
                .isInstanceOf(OrganizationNotFoundException.class)
                .hasMessage("Organization not found: missing-org");
    }

    @Test
    void generatePlanUsesStoredCycleThroughRepositoryBoundary() {
        PerformanceCycle cycle = service.createCycle(new CreatePerformanceCycleRequest(
                "2026 Mid-Year Review",
                "org-001"
        ));

        PerformancePlan plan = service.generatePlan(cycle.id());

        assertThat(plan.id()).isNotBlank();
        assertThat(plan.cycleId()).isEqualTo(cycle.id());
        assertThat(plan.status()).isEqualTo(PerformancePlanStatus.GENERATED);
        assertThat(plan.generatedAt()).isNotNull();
        assertThat(plan.generationMode()).isEqualTo("SYNC_STUB");
        assertThat(plan.targetEmployeeCount()).isEqualTo(7);
    }

    @Test
    void generatedPlanCanBeReadBackThroughPlanRepositoryBoundary() {
        PerformanceCycle cycle = service.createCycle(new CreatePerformanceCycleRequest(
                "2026 Mid-Year Review",
                "org-001"
        ));

        PerformancePlan plan = service.generatePlan(cycle.id());

        assertThat(planRepository.findById(plan.id()))
                .contains(plan);
    }

    @Test
    void generatePlanFailsClearlyWhenCycleMissing() {
        assertThatThrownBy(() -> service.generatePlan("missing-cycle"))
                .isInstanceOf(CycleNotFoundException.class)
                .hasMessage("Performance cycle not found: missing-cycle");
    }
}
