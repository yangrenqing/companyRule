# Test Plan

## Source
Adapted from `/Users/yangrenqing/ai-rd-team/examples/performance-module-test-plan.md`.

## First pass checks
- [ ] create cycle returns draft status with core response fields (`id`, `name`, `organizationId`, `status`, `createdAt`, `updatedAt`)
- [ ] get cycle returns the created entity with core response fields (`id`, `name`, `organizationId`, `status`, `createdAt`, `updatedAt`)
- [ ] get cycle fails clearly for a missing cycle
- [ ] create cycle rejects invalid request payloads (blank name, blank organizationId, missing required fields)
- [ ] create cycle fails clearly for a missing organization
- [ ] plan generation creates a minimal result for an existing cycle with `generatedAt` and minimal response fields
- [ ] plan generation fails clearly for a missing cycle
- [ ] service-layer repository seam preserves cycle readback after create with initial draft fields (`id`, `name`, `organizationId`, `status`, `createdAt`, `updatedAt`)
- [ ] service-layer repository seam preserves plan generation from stored cycle with minimal generated fields (`id`, `cycleId`, `status`, `generatedAt`, `generationMode`, `targetEmployeeCount`)
- [ ] service-layer plan repository seam preserves plan readback after generation
- [ ] service-layer plan generation fails clearly for a missing cycle

## Validation command when toolchain is ready
- `mvn test`
- Optional startup-path check: `mvn spring-boot:run`

## Pending validation blocker
Local validation has not yet run because the machine currently has Java 1.8 and no Maven.

## Next Handoff
- To: local build/test execution once toolchain is ready
