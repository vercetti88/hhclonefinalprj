create schema IF NOT EXISTS crud;

create table crud.roles
(
    role varchar(255) not null
        constraint roles_pkey
            primary key
);


create table crud.user_
(
    id          bigserial not null
        constraint user__pkey
            primary key,
    birth_day   date,
    create_date date,
    email       varchar(255),
    is_active   boolean,
    name        varchar(255),
    password    varchar(255),
    patronymic  varchar(255),
    surname     varchar(255),
    role        varchar(255)
        constraint fke4sni1rd2e4xra5j9sqxytaug
            references crud.roles
);


create table crud.resume
(
    id          bigserial not null
        constraint resume_pkey
            primary key,
    about_me    varchar(255),
    create_date timestamp(6),
    education   varchar(255),
    experience  varchar(255),
    languages   varchar(255),
    location    varchar(255),
    salary      integer,
    skills      varchar(255),
    status      varchar(255),
    title       varchar(255),
    work_places varchar(255),
    user_id     bigint
        constraint fk7kr7528kehiwik8tpulnr4qra
            references crud.user_
);

create table crud.vacancy
(
    id            bigserial not null
        constraint vacancy_pkey
            primary key,
    about_company varchar(255),
    about_vacancy varchar(255),
    create_date   timestamp(6),
    experience    varchar(255),
    location      varchar(255),
    requirements  varchar(255),
    salary        varchar(255),
    status        varchar(255),
    title         varchar(255),
    work_type     varchar(255),
    user_id       bigint
        constraint fkmp6we1s90ooty53jcfa43cqoj
            references crud.user_
);


create table crud.reaction
(
    id         bigserial not null
        constraint reaction_pkey
            primary key,
    resume_id  bigint
        constraint fkjkyhcuf5sxna3p4ch65bre7cv
            references crud.resume,
    vacancy_id bigint
        constraint fkjl2duvx523na89mdq2jtfjme9
            references crud.vacancy,
    viewed     boolean,
    user_id    bigint
        constraint fkiwbc6qnc3skd6exy5ap1wtcwy
            references crud.user_
);


create table crud.files
(
    id             bigserial not null
        constraint files_pkey
            primary key,
    file_full_name varchar(255),
    file_name      varchar(255),
    resume_id      bigint
        constraint fk6hfnl9o1om0brjkp9r8rck27y
            references crud.resume
);


