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
/* Table: actual_users                                          */
/*==============================================================*/
CREATE TABLE actual_users (
   id                VARCHAR(100) NOT NULL COMMENT '',
   user_name         VARCHAR(30) NOT NULL COMMENT '',
   first_name        VARCHAR(50) NOT NULL COMMENT '',
   last_name         VARCHAR(50) NOT NULL COMMENT '',
   password          VARCHAR(100) NOT NULL COMMENT '',
   color             INT COMMENT '',
   PRIMARY KEY (id),
   CONSTRAINT FK_USERS_HAVE_A_CO_COLORS FOREIGN KEY (color)
      REFERENCES colors (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

/*==============================================================*/
/* Table: text_messages                                              */
/*==============================================================*/
create table text_messages
(
   id                   varchar(100) not null  comment '',
   sender_id            varchar(100) not null  comment '',
   receiver_id          varchar(100) not null  comment '',
   text                 varchar(2000) not null  comment '',
   is_read              smallint not null  comment '',
   created_at           TIMESTAMP not null default CURRENT_TIMESTAMP comment '',
   primary key (id),
   constraint FK_MESSAGES_SENT_MESS_USERS foreign key (sender_id)
      references actual_users (id) on delete restrict on update restrict
);

/*==============================================================*/
/* Table: user_relation                                         */
/*==============================================================*/
create table user_relation
(
   id                   varchar(100) not null  comment '',
   user_1               varchar(100) not null  comment '',
   user_2               varchar(100) not null  comment '',
   is_pinned            smallint not null default false  comment '',
   is_blocked           smallint not null default false  comment '',
   primary key (id),
   constraint FK_USER_REL_INITIATED_USERS foreign key (user_1)
      references actual_users (id) on delete restrict on update restrict
);



INSERT INTO colors(`name`, `hexcode`) VALUES ("Cotton candy", "F2C4DE");
INSERT INTO colors(`name`, `hexcode`) VALUES ("Robin egg blue", "71B1D9");
INSERT INTO colors(`name`, `hexcode`) VALUES ("Baby blue", "AED8F2");
INSERT INTO colors(`name`, `hexcode`) VALUES ("Cornflower yellow", "F2DEA2");
INSERT INTO colors(`name`, `hexcode`) VALUES ("Dusty pink", "F2CDC4");