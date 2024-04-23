/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     14.04.2024 21:06:06                          */
/*==============================================================*/

drop table if exists messages;

drop table if exists user_relations;

drop table if exists users;

/*==============================================================*/
/* Table: users                                                 */
/*==============================================================*/
create table users
(
   id                   varchar(100) not null  comment '',
   user_name            varchar(30) not null  comment '',
   first_name           varchar(50) not null  comment '',
   last_name            varchar(50) not null  comment '',
   password             varchar(100) not null  comment ''
);

/*==============================================================*/
/* Table: messages                                              */
/*==============================================================*/
create table messages
(
   message_id           varchar(100) not null  comment '',
   sender_id            varchar(100) not null  comment '',
   receiver_id          varchar(100) not null  comment '',
   text                 varchar(2000) not null  comment '',
   is_read              smallint not null  comment '',
   constraint FK_MESSAGES_SENT_MESS_USERS foreign key (sender_id)
      references users (id) on delete restrict on update restrict
);

/*==============================================================*/
/* Table: user_relations                                        */
/*==============================================================*/
create table user_relations
(
   relation_id          varchar(100) not null  comment '',
   user_1               varchar(100) not null  comment '',
   user_2               varchar(100) not null  comment '',
   is_pinned            smallint not null default false  comment '',
   is_blocked           smallint not null default false  comment '',
   constraint FK_USER_REL_INITIATED_USERS foreign key (user_1)
      references users (id) on delete restrict on update restrict
);

alter table messages 
   drop foreign key FK_MESSAGES_SENT_MESS_USERS;

alter table user_relations 
   drop foreign key FK_USER_REL_INITIATED_USERS;


alter table messages 
   drop foreign key FK_MESSAGES_SENT_MESS_USERS;
   
alter table user_relations 
   drop foreign key FK_USER_REL_INITIATED_USERS;

