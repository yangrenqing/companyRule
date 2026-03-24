# Test Plan

## Source
Adapted from `/Users/yangrenqing/ai-rd-team/examples/performance-module-test-plan.md`.

## First pass checks
- [ ] create cycle returns draft status
- [ ] get cycle returns the created entity
- [ ] get cycle fails clearly for a missing cycle
- [ ] create cycle rejects invalid request payloads
- [ ] create cycle fails clearly for a missing organization
- [ ] plan generation creates a minimal result for an existing cycle
- [ ] plan generation fails clearly for a missing cycle
- [ ] service-layer repository seam preserves cycle readback after create
- [ ] service-layer repository seam preserves plan generation from stored cycle
- [ ] service-layer plan repository seam preserves plan readback after generation

## Validation command when toolchain is ready
- `mvn test`
- Optional startup-path check: `mvn spring-boot:run`

## Pending validation blocker
Local validation has not yet run because the machine currently has Java 1.8 and no Maven.

## Next Handoff
- To: local build/test execution once toolchain is ready
