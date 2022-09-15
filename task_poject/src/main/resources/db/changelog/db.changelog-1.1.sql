--liquibase formatted sql

--changeset milavitsky:1
create table tasks
(
    id bigint not null
    constraint task_pk primary key,
    task_description varchar(100),
    is_deleted boolean
);

