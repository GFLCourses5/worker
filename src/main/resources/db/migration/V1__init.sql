CREATE TABLE Step
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    "action" VARCHAR(255) NOT NULL,
    "value"  VARCHAR(255) NOT NULL
);

CREATE TABLE Scenario
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    site VARCHAR(255) NOT NULL
);

CREATE TABLE Scenario_Steps
(
    scenario_id BIGINT NOT NULL,
    step_id     BIGINT NOT NULL,
    PRIMARY KEY (scenario_id, step_id),
    FOREIGN KEY (scenario_id) REFERENCES Scenario (id),
    FOREIGN KEY (step_id) REFERENCES Step (id)
);
