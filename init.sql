create table sys_permission
(
    id         int auto_increment
        primary key,
    pid        int           null,
    per_name   varchar(30)   not null,
    per_type   varchar(10)   null,
    permission varchar(50)   null,
    url        varchar(50)   null,
    sort       int default 1 null
);

create table sys_role
(
    id        int auto_increment
        primary key,
    role_name varchar(30) not null
);

create table sys_role_permission
(
    role_id       int not null,
    permission_id int not null
);

create table sys_user
(
    id             int auto_increment
        primary key,
    username       varchar(30)  not null,
    password       varchar(100) not null,
    phone          varchar(20)  null,
    email          varchar(50)  null,
    avatar         varchar(100) null,
    login_ip       varchar(30)  null,
    login_time     datetime     null,
    create_user_id int          null,
    update_user_id int          null,
    create_time    datetime     null,
    update_time    datetime     null
);

create table sys_user_role
(
    user_id int not null,
    role_id int not null
);