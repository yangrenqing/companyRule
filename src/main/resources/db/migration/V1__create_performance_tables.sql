CREATE TABLE perf_cycle (
    id VARCHAR(64) PRIMARY KEY,
    cycle_name VARCHAR(255) NOT NULL,
    organization_id VARCHAR(64) NOT NULL,
    cycle_status VARCHAR(32) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE perf_template (
    id VARCHAR(64) PRIMARY KEY,
    cycle_id VARCHAR(64) NOT NULL,
    template_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE perf_plan (
    id VARCHAR(64) PRIMARY KEY,
    cycle_id VARCHAR(64) NOT NULL,
    plan_status VARCHAR(32) NOT NULL,
    generation_mode VARCHAR(32) NOT NULL,
    target_employee_count INT NOT NULL,
    generated_at TIMESTAMP NOT NULL
);

CREATE TABLE perf_review_record (
    id VARCHAR(64) PRIMARY KEY,
    cycle_id VARCHAR(64) NOT NULL,
    review_status VARCHAR(32) NOT NULL,
    reviewer_id VARCHAR(64) NOT NULL,
    created_at TIMESTAMP NOT NULL
);
