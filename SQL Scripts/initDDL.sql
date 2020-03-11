create table abteilungen
(
    id   int auto_increment
        primary key,
    name varchar(30) not null
);

create table angestellte
(
    id         int auto_increment
        primary key,
    vname      varchar(30) not null,
    name       varchar(30) not null,
    abt_id     int         not null,
    birth_date date        not null
);

create table bestellpositionen
(
    id         int auto_increment
        primary key,
    best_id    int not null,
    produkt_id int not null,
    menge      int not null
);

create table bestellungen
(
    id        int auto_increment
        primary key,
    kunde_id  int not null,
    worker_id int not null
);

create table kunden
(
    id      int auto_increment
        primary key,
    name    varchar(30) not null,
    vname   varchar(30) not null,
    telefon varchar(30) null
);

create table produkte
(
    id    int auto_increment
        primary key,
    preis double      not null,
    name  varchar(30) not null
);

