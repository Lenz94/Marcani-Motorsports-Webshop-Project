# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                        integer not null,
  name                      varchar(255),
  constraint pk_category primary key (id))
;

create table user (
  userid                    integer not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  address                   varchar(255),
  phone_number              varchar(255),
  constraint pk_user primary key (userid))
;

create sequence category_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists category;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists category_seq;

drop sequence if exists user_seq;

