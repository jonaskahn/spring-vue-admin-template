CREATE TABLE IF NOT EXISTS `groups` (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        created_by VARCHAR(255) NULL,
    created_date DATETIME(6) NULL,
    last_modified_by VARCHAR(255) NULL,
    last_modified_date DATETIME(6) NULL,
    description VARCHAR(500) NULL,
    NAME VARCHAR(100) NULL,
    STATUS INT(1) NOT NULL
    );

CREATE TABLE IF NOT EXISTS features (
                                        id bigint auto_increment,
                                        name varchar(255) not null,
    type int(1) default 2 not null comment '1 - Equals SYSTEM initial - Cannot modify after initial
2 - Equals APP initial',
    description text null,
    created_by VARCHAR(255) NULL,
    created_date DATETIME(6) NULL,
    last_modified_by VARCHAR(255) NULL,
    last_modified_date DATETIME(6) NULL,
    constraint features_pk primary key (id)
    );

create unique index features_id_uindex on features (id);

create unique index features_name_uindex on features (name);

CREATE TABLE IF NOT EXISTS authorities (
                                           id bigint auto_increment primary key,
                                           created_by varchar(255) null,
    created_date datetime(6) null,
    last_modified_by varchar(255) null,
    last_modified_date datetime(6) null,
    description varchar(500) null,
    name varchar(100) not null,
    status int(1) not null,
    feature_id bigint not null,
    constraint authorities_name_uindex unique (name),
    constraint authorities_features_id_fk foreign key (feature_id) references features (id)
    );

CREATE TABLE IF NOT EXISTS roles (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     created_by VARCHAR(255) NULL,
    created_date DATETIME(6) NULL,
    last_modified_by VARCHAR(255) NULL,
    last_modified_date DATETIME(6) NULL,
    description VARCHAR(500) NULL,
    NAME VARCHAR(100) NULL,
    STATUS INT(1) NOT NULL,
    CONSTRAINT uk_roles_name UNIQUE (NAME)
    );

CREATE TABLE IF NOT EXISTS role_authorities (
                                                role_id bigint not null,
                                                authority_id bigint not null,
                                                constraint role_authorities_pk unique (role_id, authority_id),
    constraint role_authorities_authorities_id_fk foreign key (authority_id) references authorities (id),
    constraint role_authorities_roles_id_fk foreign key (role_id) references roles (id)
    );

create index role_authorities_role_id_authority_id_index on role_authorities (role_id, authority_id);

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     created_by VARCHAR(255) NULL,
    created_date DATETIME(6) NULL,
    last_modified_by VARCHAR(255) NULL,
    last_modified_date DATETIME(6) NULL,
    birthdate DATE NULL,
    email VARCHAR(255) NOT NULL,
    email_verified INT(1) NULL,
    enabled INT(1) NULL,
    locked INT(1) NULL,
    family_name VARCHAR(255) NULL,
    gender INT NULL,
    given_name VARCHAR(255) NULL,
    middle_name VARCHAR(255) NULL,
    NAME VARCHAR(255) NULL,
    PASSWORD VARCHAR(255) NULL,
    phone_number VARCHAR(255) NULL,
    phone_number_verified INT(1) NULL,
    preferred_username VARCHAR(255) NOT NULL,
    unsigned_name VARCHAR(255) NULL,
    username VARCHAR(255) NOT NULL,
    CONSTRAINT uk_users_username UNIQUE (username),
    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT users_preferred_username_uindex UNIQUE (preferred_username)
    );

CREATE TABLE IF NOT EXISTS group_members (
                                             user_id BIGINT NOT NULL,
                                             group_id BIGINT NOT NULL,
                                             PRIMARY KEY (user_id, group_id),
    CONSTRAINT fk_group_members__groups FOREIGN KEY (group_id) REFERENCES `groups` (id),
    CONSTRAINT fk_group_members__users FOREIGN KEY (user_id) REFERENCES users (id)
    );

CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id BIGINT NOT NULL,
                                          role_id BIGINT NOT NULL,
                                          PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles__roles FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT fk_user_roles__users FOREIGN KEY (user_id) REFERENCES users (id)
    );

CREATE TABLE IF NOT EXISTS access_tokens (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    expired_at DATETIME(6) NULL,
    STATUS INT(1) NOT NULL,
    CONSTRAINT fk_access_tokens__users FOREIGN KEY (user_id) REFERENCES users (id)
    );

CREATE TABLE IF NOT EXISTS refresh_tokens (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    access_token_id VARCHAR (255) NOT NULL,
    user_id BIGINT NOT NULL,
    expired_at DATETIME (6) NULL,
    STATUS INT(1) NOT NULL,
    CONSTRAINT fk_refresh_tokens__access_tokens FOREIGN KEY (access_token_id) REFERENCES access_tokens (id)
    );