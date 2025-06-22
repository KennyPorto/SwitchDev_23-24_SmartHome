create table house
(
    _house_id   bigint        not null
        primary key,
    city        varchar(255)  null,
    country     varchar(255)  null,
    door_number varchar(255)  null,
    latitude    double        null,
    longitude   double        null,
    street      varchar(255)  null,
    version     int default 0 not null,
    zip_code    varchar(255)  null
);

INSERT INTO smarthome.house (_house_id, city, country, door_number, latitude, longitude, street, version, zip_code) VALUES (1, 'Porto', 'Portugal', '4892', 41.166679, -8.680758, 'Avenida da Boavista', 1, '4100-125');
INSERT INTO smarthome.house (_house_id, city, country, door_number, latitude, longitude, street, version, zip_code) VALUES (2, 'Loulé', 'Portugal', '40', 37.027854, -8.018719, 'Rua Douro', 1, '8135-162');
INSERT INTO smarthome.house (_house_id, city, country, door_number, latitude, longitude, street, version, zip_code) VALUES (3, 'Mountain View', 'USA', '1600', 37.422, -122.084, 'Amphitheatre Parkway', 1, '94043');
