CREATE TABLE scenario_results
(
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT      NOT NULL,
    name            TEXT        NOT NULL,
    site            TEXT        NOT NULL,
    stepResults_id int,
    executed_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE step_results
(
    id          BIGSERIAL PRIMARY KEY,
    action      TEXT   NOT NULL,
    value       TEXT   NOT NULL,
    is_passed   BOOLEAN,
    scenario_id BIGINT NOT NULL REFERENCES scenario_results (id)
);

CREATE TABLE scenarios_steps
(
    scenario_id BIGINT NOT NULL,
    step_id     BIGINT NOT NULL,
    PRIMARY KEY (scenario_id, step_id),
    CONSTRAINT scenario_steps_scenario_fk FOREIGN KEY (scenario_id)
        REFERENCES scenario_results (id) ON DELETE CASCADE,
    CONSTRAINT scenario_steps_step_fk FOREIGN KEY (step_id)
        REFERENCES step_results (id) ON DELETE CASCADE
);
