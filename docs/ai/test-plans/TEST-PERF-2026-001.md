# Test Plan

## Source
Adapted from `/Users/yangrenqing/ai-rd-team/examples/performance-module-test-plan.md`.

## First pass checks
- [ ] create cycle returns draft status with core response fields (`id`, `name`, `organizationId`, `status`, `createdAt`, `updatedAt`) and starts with aligned initial timestamps
- [ ] get cycle returns the created entity with core response fields (`id`, `name`, `organizationId`, `status`, `createdAt`, `updatedAt`) and preserves the initial timestamps from create
- [ ] get cycle fails clearly for a missing cycle with the standard 404 error payload (`timestamp`, `status`, `error`, `message`)
- [ ] create cycle rejects invalid request payloads (blank name, blank organizationId, null required fields, missing required fields, malformed JSON, empty body, empty JSON object, numeric values for required string fields) with the standard 400 error payload (`timestamp`, `status`, `error`, `message`)
- [ ] create cycle rejects unsupported content types such as `text/plain` with the standard 415 error payload (`timestamp`, `status`, `error`, `message`)
- [ ] create cycle rejects unsupported HTTP methods such as `PUT` with the standard 405 error payload (`timestamp`, `status`, `error`, `message`)
- [ ] create cycle fails clearly for a missing organization with the standard 400 error payload (`timestamp`, `status`, `error`, `message`)
- [ ] plan generation creates a minimal result for an existing cycle with `generatedAt`, minimal response fields, non-regressing chronology from cycle creation, and no unintended mutation of the stored cycle timestamps or draft status
- [ ] plan generation fails clearly for a missing cycle with the standard 404 error payload (`timestamp`, `status`, `error`, `message`)
- [ ] service-layer repository seam preserves cycle readback after create with initial draft fields (`id`, `name`, `organizationId`, `status`, `createdAt`, `updatedAt`)
- [ ] service-layer repository seam preserves plan generation from stored cycle with minimal generated fields (`id`, `cycleId`, `status`, `generatedAt`, `generationMode`, `targetEmployeeCount`) and non-regressing generation chronology
- [ ] service-layer plan repository seam preserves plan readback after generation with key generated fields (`id`, `cycleId`, `status`, `generatedAt`, `generationMode`, `targetEmployeeCount`)
- [ ] service-layer plan generation fails clearly for a missing cycle

## Validation command when toolchain is ready
- `mvn test`
- Optional startup-path check: `mvn spring-boot:run`

## Pending validation blocker
Local validation has not yet run because the machine currently has Java 1.8 and no Maven.

## Next Handoff
- To: local build/test execution once toolchain is ready
