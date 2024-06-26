/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     23.04.2024 18:28:50                          */
/*==============================================================*/


drop table if exists user_relations;
drop table if exists messages;
drop table if exists users;
drop table if exists colors;


/*==============================================================*/
/* Table: colors                                                */
/*==============================================================*/
create table colors
(
    id                   int AUTO_INCREMENT not null comment '',
    hexcode              varchar(10) not null  comment '',
    name                 varchar(20) not null  comment '',
    primary key (id)
);

/*==============================================================*/
/* Table: users                                                 */
/*==============================================================*/
create table if not exists actual_users
(
    id                   varchar(100) not null  comment '',
    user_name            varchar(30) not null  comment '',
    first_name           varchar(50) not null  comment '',
    last_name            varchar(50) not null  comment '',
    password             varchar(100) not null  comment '',
    color                varchar(100)  comment '',
    primary key (id)
);

/*==============================================================*/
/* Table: messages                                              */
/*==============================================================*/
create table if not exists text_messages
(
    id                   varchar(100) not null  comment '',
    sender_id            varchar(100) not null  comment '',
    receiver_id          varchar(100) not null  comment '',
    text                 varchar(2000) not null  comment '',
    is_read              smallint not null  comment '',
    created_at           TIMESTAMP not null default CURRENT_TIMESTAMP comment '',
    primary key (id)
);

/*==============================================================*/
/* Table: user_relations                                        */
/*==============================================================*/
create table if not exists user_relation
(
    id                   varchar(100) not null  comment '',
    user_1               varchar(100) not null  comment '',
    user_2               varchar(100) not null  comment '',
    is_pinned            smallint not null default false  comment '',
    is_blocked           smallint not null default false  comment '',
    primary key (id)
);

INSERT INTO COLORS(`name`, `hexcode`) VALUES ('Cotton candy', 'F2C4DE');
INSERT INTO COLORS(`name`, `hexcode`) VALUES ('Robin egg blue', '71B1D9');
INSERT INTO COLORS(`name`, `hexcode`) VALUES ('Baby blue', 'AED8F2');
INSERT INTO COLORS(`name`, `hexcode`) VALUES ('Cornflower yellow', 'F2DEA2');
INSERT INTO COLORS(`name`, `hexcode`) VALUES ('Dusty pink', 'F2CDC4');