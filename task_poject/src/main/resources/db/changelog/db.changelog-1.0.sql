--liquibase formatted sql

--changeset milavitsky:1
create table projects
(
    id bigserial not null
   constraint project_pk primary key,
    title varchar(20) not null,
    project_description varchar(100),
    budget numeric (10,2),
    date_of_start date,
    date_of_end date,
    is_deleted boolean
);

--changeset milavitsky:2
create table tasks
(
    id bigserial not null
    constraint task_pk primary key,
    task_description varchar(100),
    is_deleted boolean
);

--changeset milavitsky:3
create table project_has_task
(
    id_task bigserial not null
        constraint task_pk
            references tasks,
    id_project bigint not null
        constraint project_pk
            references projects,
     constraint project_has_task_pk
            primary key (id_task, id_project)
);

