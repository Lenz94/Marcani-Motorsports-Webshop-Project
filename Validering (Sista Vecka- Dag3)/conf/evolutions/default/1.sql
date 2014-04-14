# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table student (
  id                        integer not null,
  name                      varchar(255),
  lastname                  varchar(255),
  email                     varchar(255),
  phone_number              varchar(255),
  constraint pk_student primary key (id))
;

create sequence student_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists student;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists student_seq;

