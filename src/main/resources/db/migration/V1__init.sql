create table scenario_results
(
    id          bigserial primary key,
    user_id     bigint      not null,
    name        text        not null,
    site        text        not null,
    executed_at timestamptz not null default now()
);

create table steps
(
    id     bigserial primary key,
    action text not null,
    value  text not null
);

create table step_results
(
    id        bigserial primary key,
    step_id   bigint references steps (id) on delete cascade,
    is_passed boolean
);

create table scenario_results_step_results
(
    scenario_result_id bigint not null,
    step_result_id     bigint not null,
    primary key (scenario_result_id, step_result_id),
    constraint scenario_step_scenario_fk foreign key (scenario_result_id)
        references scenario_results (id) on delete cascade,
    constraint scenario_step_step_fk foreign key (step_result_id)
        references step_results (id) on delete cascade
);
