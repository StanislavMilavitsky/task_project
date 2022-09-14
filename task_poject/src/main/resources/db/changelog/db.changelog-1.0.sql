--liquibase formatted sql

--changeset milavitsky:1
create table projects
(
    project_id   bigserial   not null
   constraint project_pk primary key,
    title    varchar(20) not null,
    project_description    varchar(100),
    budget        bigserial,
    date_of_start date
);