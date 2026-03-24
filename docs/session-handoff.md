# Session Handoff

## What this repo is
This is the adopted repo for the real implementation trial of the performance-module sample from the AI R&D starter.

## What was completed before this handoff
- Created the minimal adopted-repo directory layout.
- Copied `.claude/agents/` and `.claude/settings.json` from the starter baseline.
- Added continuity docs and repo-local AI doc directories.
- Added `pom.xml` and the Spring Boot application entry for the first `performance-service` vertical slice.
- Implemented the first API slice for cycle create/read and minimal plan generation.
- Added in-memory service logic, explicit repository boundaries, and a stub `organization-service` adapter boundary.
- Added the first SQL migration file for performance-domain tables.
- Added minimal H2 + Flyway local runtime wiring so migration validation is ready once JDK 17 and Maven are installed.
- Added the first controller test suite for create/read/plan-generation behavior, including missing-entity failures and request-validation coverage for blank or missing required fields.
- Extended the in-memory repository seam coverage with minimal readback support, initial draft-field assertions for cycle create/readback, key generated-field preservation on plan readback, a plan-generation timestamp chronology check, and the missing-cycle failure path for plan generation.

## Best next step
1. Install JDK 17 and Maven locally.
2. Run `mvn test`.
3. If startup-path validation is needed, run `mvn spring-boot:run` to exercise local H2 + Flyway wiring.
4. Fix any compile/test issues.
5. Continue replacing stubs with real persistence and upstream organization dependency handling.

## Important constraints
- Keep this repo focused on the performance project spike.
- Keep `/Users/yangrenqing/ai-rd-team` generic as the reusable starter/spec source.
- Do not add deploy/release automation.
- Preserve human approval points for release, rollback, and high-risk changes.
- Keep sensitive HR data boundaries explicit.

## Important files to read first next time
1. `README.md`
2. `docs/project-status.md`
3. `docs/session-handoff.md`
4. `docs/ai/requirements/REQ-PERF-2026-001.md`
5. `docs/ai/prd/PRD-PERF-2026-001.md`
6. `docs/ai/design/DES-PERF-2026-001.md`
7. `docs/ai/test-plans/TEST-PERF-2026-001.md`
8. `docs/ai/release/REL-PERF-2026-001.md`
9. `pom.xml`
10. `src/main/java/com/companyrule/performance/`
11. `src/test/java/com/companyrule/performance/`

## Open work items
- Toolchain setup: JDK 17 + Maven.
- Run local build/tests for the first time.
- Add persistence-backed repositories instead of pure in-memory storage.
- Add role/permission checks and state-machine hardening.
- Add organization-service integration or a cleaner adapter/stub boundary.

## Notes for the next session
- Local environment was checked and is currently blocked by Java 1.8 and missing Maven.
- The repo has been bootstrapped, but verification is still pending.
- Prioritize buildability and the smallest passing vertical slice before expanding feature scope.
