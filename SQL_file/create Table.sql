create table user
(
    user_name        varchar(15)  not null primary key,
    biography        varchar(255) null,
    perfil_file_name varchar(40)  null,
    pass             varchar(100) not null
);

create table publication
(
    id               int auto_increment primary key,
    publication_date datetime     null,
    text             varchar(255) not null,
    user_name        varchar(15)  not null
);

create table user_options
(
    user_name varchar(50) primary key references nexadatabase.user (user_name) ON DELETE CASCADE ON UPDATE CASCADE,
    Language  varchar(5) DEFAULT 'EN'
);

CREATE TABLE Collection
(
    user_name VARCHAR(50),
    name      VARCHAR(20),
    PRIMARY KEY (user_name, name)
);

-- Crear la tabla intermedia Publicaciones_Collection
CREATE TABLE Publications_Collection
(
    id_publication INTEGER,
    user_name      VARCHAR(50),
    name           VARCHAR(20),
    PRIMARY KEY (id_publication, user_name, name)
);

create table likes
(
    id             int auto_increment primary key,
    id_publication int         null,
    user_name      varchar(15) not null,
    date_like      datetime    null
);

create table share
(
    id             int auto_increment primary key,
    id_publication int         null,
    user_name      varchar(15) not null,
    date_shared    datetime    null
)