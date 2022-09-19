--liquibase formatted sql

--changeset milavitsky:1
create table users
  (
  id bigint not null
  constraint user_pk primary key,
  username varchar (50),
  password varchar (100),
  date_of_registration date,
  role varchar(15),
  is_deleted boolean
  );

