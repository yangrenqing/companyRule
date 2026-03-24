# Project Status

## Repository Role
- This repository is the adopted repo for the real implementation trial of the performance-module sample chain.
- It is no longer a generic starter; it is the concrete place for the Moka-style performance project spike.
- The starter remains in `/Users/yangrenqing/ai-rd-team` and acts as the reusable spec/source pack.

## Current Completion

### Already in place
- `.claude/agents/` copied from the starter as the baseline collaboration pack.
- `.claude/settings.json` copied from the starter as the safe-by-default Claude Code baseline.
- Repo-local AI document directories created under `docs/ai/`.
- Continuity docs created:
  - `README.md`
  - `docs/project-status.md`
  - `docs/session-handoff.md`
- Maven + Spring Boot project skeleton created in `pom.xml`.
- Minimal application entry added at `src/main/java/com/companyrule/performance/PerformanceServiceApplication.java`.
- First vertical-slice API implemented:
  - `POST /api/performance/cycles`
  - `GET /api/performance/cycles/{id}`
  - `POST /api/performance/cycles/{id}/plans`
- In-memory service logic and repository boundaries added in `src/main/java/com/companyrule/performance/application/PerformanceCycleService.java`, `src/main/java/com/companyrule/performance/application/PerformanceCycleRepository.java`, and `src/main/java/com/companyrule/performance/application/PerformancePlanRepository.java`.
- Stub upstream boundary added in `src/main/java/com/companyrule/performance/infrastructure/StubOrganizationGateway.java`.
- First SQL migration file added at `src/main/resources/db/migration/V1__create_performance_tables.sql`.
- Minimal local H2 + Flyway runtime wiring added in `pom.xml` and `src/main/resources/application.properties` so migration execution is ready once the toolchain is available.
- First controller test suite added at `src/test/java/com/companyrule/performance/api/PerformanceCycleControllerTest.java`, including create/get/plan-generation paths, initial timestamp alignment in the create response, initial timestamp consistency across cycle create/readback, non-regressing plan-generation chronology, cycle readback stability after plan generation, the standard 404 error payload for missing-cycle failures, the standard 400 error payload for missing-organization failures, and request-validation plus unsupported-content-type, unsupported-method, and unsupported-accept-header coverage for blank, null, missing, malformed, empty-body, empty-object, numeric-instead-of-string, text/plain, PUT, or `Accept: application/xml` create-cycle requests with the same standard error-payload shape.
- Service-layer repository seam tests added at `src/test/java/com/companyrule/performance/application/PerformanceCycleServiceTest.java`, including cycle readback assertions for the initial draft fields, key generated-field preservation on plan readback through `PerformancePlanRepository`, a plan-generation timestamp chronology assertion, cycle aggregate stability after plan generation, and a missing-cycle failure check for plan generation.

### Current vertical slice boundary
- Create performance cycle
- Get performance cycle
- Trigger minimal plan generation
- Use in-memory service logic first, with organization dependency treated as a future integration point

## Active blockers
- Local Java is 1.8 instead of JDK 17.
- Maven is not installed locally.
- Because of the above, build/test validation has not been executed yet.

## Recommended next actions
1. Install JDK 17 and Maven in the local environment.
2. Run the repo-local build/test command and fix compile issues if any.
3. Replace the current in-memory plan-generation stub with persistent behavior plus organization-service dependency handling.
4. Add role/permission checks and more explicit state-machine tests.
5. Keep release/rollback decisions manual and do not add deploy automation.

## Constraints and guardrails
- Do not add production deployment automation.
- Preserve human confirmation for release, rollback, and high-risk changes.
- Keep starter-level improvements in `/Users/yangrenqing/ai-rd-team`, not mixed into this repo unless they are real adoption findings.
- Treat performance records, comments, and scores as sensitive HR data.

## Validation expectations
- `mvn test` should pass once JDK 17 + Maven are available.
- Migration SQL should remain reversible enough for internal-pilot trial setup.
- The first real validation target is the minimal cycle create/read/plan-generation path.
