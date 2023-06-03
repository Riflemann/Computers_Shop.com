create table if not exists Desktops
(
    id            integer AUTO_INCREMENT PRIMARY KEY,
    type_desktops varchar not null,
    series_num    varchar not null,
    manufacturer  varchar not null,
    cost          double  not null,
    quantity      integer not null
);

create table if not exists Laptops
(
    id           integer AUTO_INCREMENT PRIMARY KEY,
    diagonal     varchar not null,
    series_num   varchar not null,
    manufacturer varchar not null,
    cost         double  not null,
    quantity     integer not null,
);

create table if not exists Monitors
(
    id           integer AUTO_INCREMENT PRIMARY KEY,
    diagonal     varchar not null,
    series_num   varchar not null,
    manufacturer varchar not null,
    cost         double  not null,
    quantity     integer not null,
);

create table if not exists Hard_discs
(
    id                 integer AUTO_INCREMENT PRIMARY KEY,
    hard_drive_volumes varchar not null,
    series_num         varchar not null,
    manufacturer       varchar not null,
    cost               double  not null,
    quantity           integer not null,
);


