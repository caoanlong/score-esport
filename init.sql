create table csgo_team_stats
(
    id              bigint auto_increment
        primary key,
    match_id        varchar(30) not null,
    map_name        varchar(20) null,
    first_score     int         null,
    first_win       int         null,
    five_win        int         null,
    over_time_score int         null,
    second_score    int         null,
    sixteenth_win   int         null,
    ten_win         int         null
);

create table dota_team_stats
(
    id             bigint auto_increment
        primary key,
    match_id       varchar(30) not null,
    assists_count  int         null,
    die_count      int         null,
    economic_count int         null,
    exp_count      int         null,
    fifteen_kill   int         null,
    first_blood    int         null,
    first_rou_shan int         null,
    first_tower    int         null,
    five_kill      int         null,
    kill_count     int         null,
    ten_kill       int         null
);

create table `match`
(
    match_id              varchar(30)  not null
        primary key,
    match_time            varchar(50)  not null,
    match_title           varchar(30)  null,
    game_type             varchar(20)  not null,
    status                int          null,
    tournament_id         varchar(30)  null,
    tournament_logo       varchar(100) null,
    tournament_name       varchar(50)  null,
    tournament_name_en    varchar(50)  null,
    tournament_short_name varchar(30)  null,
    view_actual_num       int          null,
    view_num              int          null,
    length_time           int          null,
    is_focus              tinyint(1)   null,
    box                   int          null,
    box_bum               int          null,
    home_id               varchar(20)  null,
    home_name             varchar(50)  null,
    home_short_name       varchar(30)  null,
    home_logo             varchar(100) null,
    home_live_score       int          null,
    home_score            int          null,
    home_odds             varchar(20)  null,
    home_rang_fen         varchar(20)  null,
    home_trend            varchar(30)  null,
    away_id               varchar(20)  null,
    away_name             varchar(50)  null,
    away_short_name       varchar(30)  null,
    away_logo             varchar(100) null,
    away_live_score       int          null,
    away_score            int          null,
    away_odds             varchar(20)  null,
    away_rang_fen         varchar(20)  null,
    away_trend            varchar(30)  null
);

create table member
(
    id             int auto_increment
        primary key,
    username       varchar(30)   null,
    phone          varchar(20)   not null,
    password       varchar(100)  not null,
    gender         int default 1 null,
    avatar         varchar(100)  null,
    email          varchar(50)   null,
    login_ip       varchar(50)   null,
    login_time     datetime      null,
    update_user_id int           null,
    update_time    datetime      null
);

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

