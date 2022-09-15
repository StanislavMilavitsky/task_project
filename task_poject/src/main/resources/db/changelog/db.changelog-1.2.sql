--liquibase formatted sql

--changeset milavitsky:1
create table project_has_task
(
    id_task bigint not null
        constraint task_pk
            references tasks,
    id_project bigint not null
        constraint project_pk
            references projects,
     constraint project_has_task_pk
            primary key (id_task, id_project)
);
