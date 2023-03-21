create table if not exists authorities
(
    id          bigint auto_increment primary key,
    name        varchar(100)      not null,
    description text              null
);

create table if not exists `groups`
(
    id                 bigint auto_increment primary key,
    created_by         bigint null,
    created_date       datetime(6)  null,
    last_modified_by   bigint null,
    last_modified_date datetime(6)  null,
    description        varchar(500) null,
    name               varchar(100) null,
    status             int(1)       not null
);

ALTER TABLE
    `groups`
    AUTO_INCREMENT = 1000;

create table if not exists users
(
    id                    bigint auto_increment primary key,
    created_by            bigint null,
    created_date          datetime(6)  null,
    last_modified_by      bigint null,
    last_modified_date    datetime(6)  null,
    birthdate             date         null,
    email                 varchar(255) not null,
    email_verified        int(1)       null,
    enabled               int(1)       null,
    locked                int(1)       null,
    family_name           varchar(255) null,
    gender                int          null,
    given_name            varchar(255) null,
    middle_name           varchar(255) null,
    name                  varchar(255) null,
    password              varchar(255) null,
    phone_number          varchar(255) null,
    phone_number_verified int(1)       null,
    preferred_username    varchar(255) not null,
    unsigned_name         varchar(255) null,
    username              varchar(255) not null,
    constraint uk_users_email unique (email),
    constraint uk_users_username unique (username),
    constraint users_preferred_username_uindex unique (preferred_username)
);

ALTER TABLE
    users
    AUTO_INCREMENT = 1000;

create table if not exists access_tokens
(
    id         varchar(255) not null,
    user_id    bigint       not null,
    expired_at datetime(6)  null,
    primary key (id),
    constraint fk_access_tokens__users foreign key (user_id) references users (id)
);

create table if not exists group_members
(
    user_id  bigint not null,
    group_id bigint not null,
    primary key (user_id, group_id),
    constraint fk_group_members__groups foreign key (group_id) references `groups` (id),
    constraint fk_group_members__users foreign key (user_id) references users (id)
);

create table if not exists refresh_tokens
(
    id              varchar(255) not null,
    access_token_id varchar(255) not null,
    user_id         bigint       not null,
    expired_at      datetime(6)  null,
    primary key (id),
    constraint fk_refresh_tokens__access_tokens foreign key (access_token_id) references access_tokens (id)
);

create table if not exists user_authorities
(
    user_id      bigint not null,
    authority_id bigint not null,
    primary key (user_id, authority_id),
    constraint user_authorities_authorities_id_fk foreign key (authority_id) references authorities (id),
    constraint user_authorities_users_id_fk foreign key (user_id) references users (id)
);