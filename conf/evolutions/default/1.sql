# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  category_id               integer not null,
  category_name             varchar(255),
  category_list             varchar(255),
  constraint pk_category primary key (category_id))
;

create table product (
  id                        integer not null,
  name                      varchar(255),
  description               varchar(255),
  cost                      double,
  constraint pk_product primary key (id))
;

create table shopping_basket (
  id                        integer not null,
  quantity                  integer,
  constraint pk_shopping_basket primary key (id))
;

create table user (
  user_id                   integer not null,
  password                  varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  address                   varchar(255),
  phone_number              varchar(255),
  email                     varchar(255),
  constraint pk_user primary key (user_id))
;

create sequence category_seq;

create sequence product_seq;

create sequence shopping_basket_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists category;

drop table if exists product;

drop table if exists shopping_basket;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists category_seq;

drop sequence if exists product_seq;

drop sequence if exists shopping_basket_seq;

drop sequence if exists user_seq;

