# companyRule

这是绩效项目的 adopted repo 试做仓库，用来把 `/Users/yangrenqing/ai-rd-team` 中已有的 `performance-module-*` 文档链路落成一个最小可运行的实现骨架。

## 当前定位
- 业务域：Moka 风格绩效管理模块
- 当前目标：最小 vertical slice
- 范围：绩效周期创建、查询，以及计划生成入口骨架
- 风险策略：仅 internal pilot 思路；保留人工 review、人工 release/rollback 决策，不加入任何 deploy 自动化

## 目录
```txt
.claude/
  agents/
  settings.json

docs/
  project-status.md
  session-handoff.md
  ai/
    intake/
    requirements/
    prd/
    design/
    test-plans/
    release/
    pilot-runs/

src/
  main/java/com/companyrule/performance/
  main/resources/
  test/java/com/companyrule/performance/
```

## 当前最小实现边界
- `POST /api/performance/cycles`
- `GET /api/performance/cycles/{id}`
- `POST /api/performance/cycles/{id}/plans`
- 首批 schema：`perf_cycle`、`perf_template`、`perf_plan`、`perf_review_record`
- `organization-service` 先视为上游只读依赖，当前仓库仅保留 stub 级接口边界

## 已落地代码
- Maven + Spring Boot 项目骨架已创建：`pom.xml`
- 启动类：`src/main/java/com/companyrule/performance/PerformanceServiceApplication.java`
- 最小领域模型、应用服务与内存仓储边界已创建：
  - `src/main/java/com/companyrule/performance/domain/PerformanceCycle.java`
  - `src/main/java/com/companyrule/performance/domain/PerformancePlan.java`
  - `src/main/java/com/companyrule/performance/application/PerformanceCycleService.java`
  - `src/main/java/com/companyrule/performance/application/PerformanceCycleRepository.java`
  - `src/main/java/com/companyrule/performance/application/PerformancePlanRepository.java`
  - `src/main/java/com/companyrule/performance/infrastructure/InMemoryPerformanceCycleRepository.java`
  - `src/main/java/com/companyrule/performance/infrastructure/InMemoryPerformancePlanRepository.java`
- API 与异常处理已创建：
  - `src/main/java/com/companyrule/performance/api/PerformanceCycleController.java`
  - `src/main/java/com/companyrule/performance/api/PerformanceExceptionHandler.java`
- 上游组织能力先通过 stub adapter 表达：`src/main/java/com/companyrule/performance/infrastructure/StubOrganizationGateway.java`
- 首个 migration 文件已创建：`src/main/resources/db/migration/V1__create_performance_tables.sql`
- 本地 runtime 已补齐最小 H2 + Flyway 配置：`src/main/resources/application.properties`
- 首批接口测试已创建：`src/test/java/com/companyrule/performance/api/PerformanceCycleControllerTest.java`，当前已覆盖 create/get/plan-generation 主路径、create response 初始时间戳对齐、create/readback 初始时间戳一致性、plan generation chronology、plan generation 后 cycle readback 不被意外改写、缺失 cycle 的标准 404 error payload、缺失 organization 与请求校验失败的标准 400 error payload，以及 cycle create 的必填/空白/null 字段、malformed JSON 与 empty body 在各类 invalid-payload 场景下的标准 400 contract
- 服务层仓储边界测试已创建：`src/test/java/com/companyrule/performance/application/PerformanceCycleServiceTest.java`，当前已覆盖 cycle readback 的初始 draft 字段、plan readback 的关键生成字段保留、plan generation timestamp chronology、plan generation 后 cycle 聚合保持不变，以及缺失 cycle 时的 plan-generation failure path
- 当前实现保持 in-memory first，cycle 创建后返回 `DRAFT`，plan 生成返回最小 `GENERATED` 结果
- `PerformancePlanRepository` 已具备最小 readback seam，便于后续替换为真实持久化实现前先验证 plan 存储边界
- 当前 migration 已具备在本地内存库启动时执行的最小依赖前提

## 当前验证状态
- 代码与测试文件已落位
- 由于本机仍缺 JDK 17 与 Maven，`mvn test` 还未执行
- 当前状态属于“已实现首轮骨架，待本地工具链验证”

## 工具链现状
当前本机环境已确认存在两个 blocker：
- 本地 Java 为 1.8，不是目标 JDK 17
- `mvn` 未安装

因此当前提交已完成项目骨架、接口、测试和 migration 落位，但未完成本地 build/test 验证。

## 文档来源
本仓库中的 repo-local AI 文档基于以下 starter 规格同步而来：
- `examples/performance-module-requirement-intake.md`
- `examples/performance-module-prd.md`
- `examples/performance-module-design.md`
- `examples/performance-module-test-plan.md`
- `examples/performance-module-release-checklist.md`

## 本地验证命令
1. 确认 `java -version` 为 17.x
2. 确认 `mvn -version` 可用
3. 在仓库根目录运行 `mvn test`
4. 如需单独验证 migration 启动路径，运行 `mvn spring-boot:run`

## 下一步
1. 安装 JDK 17 与 Maven
2. 跑通 `mvn test`
3. 如需验证 migration 启动链路，执行 `mvn spring-boot:run`
4. 将计划生成入口从内存 stub 替换为真实 organization-service 读取与持久化逻辑
5. 按 test plan 补齐权限、状态流转与失败路径测试
