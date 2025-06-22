create table room
(
    room_id        bigint        not null
        primary key,
    house_floor    varchar(255)  null,
    height         double        null,
    house_id       bigint        null,
    length         double        null,
    outdoor_indoor varchar(255)  null,
    room_name      varchar(255)  null,
    version        int default 0 not null,
    width          double        null
);

INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (1, 'ground floor', 1.6, 1, 2, 'INDOOR', 'Dining Room', 0, 3.5);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (2, 'ground floor', 1.6, 1, 2, 'OUTDOOR', 'Flower Garden', 0, 3.5);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (3, 'ground floor', 2.2, 1, 3.5, 'INDOOR', 'Kitchen', 0, 6);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (4, 'ground floor', 2.2, 1, 3.5, 'INDOOR', 'Kitchen', 0, 6);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (5, 'ground floor', 2.2, 1, 5, 'INDOOR', 'Dining Room', 0, 7.5);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (6, 'ground floor', 2.2, 1, 5, 'INDOOR', 'Living Room', 0, 6);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (7, 'ground floor', 3, 1, 15, 'OUTDOOR', 'Garden', 0, 10.5);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (8, 'first floor', 2.2, 1, 5, 'INDOOR', 'Master Bedroom', 0, 4);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (9, 'first floor', 2.2, 1, 3, 'INDOOR', 'Kids Bedroom', 0, 4);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (10, 'first floor', 2.2, 1, 3, 'INDOOR', 'Office', 0, 2);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (11, 'first floor', 2.2, 1, 3, 'INDOOR', 'Bathroom', 0, 3);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (12, 'second floor', 3, 1, 7, 'OUTDOOR', 'Tree Garden', 0, 7.5);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (13, 'underground floor', 2.5, 1, 7, 'INDOOR', 'Garage', 0, 7.5);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (14, 'first floor', 2.2, 1, 3, 'INDOOR', 'Bathroom', 0, 3);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (15, 'second floor', 3, 1, 7, 'OUTDOOR', 'Pool', 0, 7.5);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (16, 'second floor', 3, 1, 7, 'OUTDOOR', 'Rooftop', 0, 7.5);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (17, 'first floor', 2.2, 1, 3, 'INDOOR', 'Kids Bedroom', 0, 4);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (18, 'first floor', 2.2, 1, 3, 'INDOOR', 'Office', 0, 2);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (19, 'ground floor', 2.2, 2, 5, 'INDOOR', 'Dining Room', 0, 3);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (20, 'ground floor', 5, 2, 15, 'OUTDOOR', 'Garden', 0, 4);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (21, 'ground floor', 2.2, 2, 6, 'INDOOR', 'Kitchen', 0, 3);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (22, 'first floor', 6, 2, 5, 'INDOOR', 'Master Bedroom', 0, 3);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (23, 'first floor', 2.2, 2, 4, 'INDOOR', 'Kids Bedroom', 0, 5);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (24, 'ground floor', 4, 2, 6, 'INDOOR', 'Living Room', 0, 4);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (25, 'first floor', 3, 2, 4, 'INDOOR', 'Office', 0, 6);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (26, 'underground floor', 5, 2, 15, 'OUTDOOR', 'Garage', 0, 3);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (27, 'ground floor', 2.2, 2, 4, 'INDOOR', 'Bathroom', 0, 4);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (28, 'ground floor', 7, 2, 15, 'OUTDOOR', 'Flower Garden', 0, 5);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (29, 'ground floor', 3, 3, 3, 'INDOOR', 'Dining Room', 0, 3);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (30, 'first floor', 4, 3, 2, 'INDOOR', 'Master Bedroom', 0, 4);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (31, 'first floor', 5, 3, 2, 'INDOOR', 'Bedroom', 0, 2);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (32, 'ground floor', 6, 3, 3, 'INDOOR', 'Office', 0, 3);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (33, 'underground floor', 3, 3, 4, 'OUTDOOR', 'Garage', 0, 4);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (34, 'ground floor', 2, 3, 5, 'OUTDOOR', 'Pool', 0, 5);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (35, 'ground floor', 3, 3, 4, 'INDOOR', 'Kitchen', 0, 3);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (36, 'ground floor', 4, 3, 2, 'INDOOR', 'Bathroom', 0, 4);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (37, 'ground floor', 2, 3, 3, 'INDOOR', 'Living Room', 0, 5);
INSERT INTO smarthome.room (room_id, house_floor, height, house_id, length, outdoor_indoor, room_name, version, width) VALUES (38, 'ground floor', 1, 3, 4, 'OUTDOOR', 'Flower Garden', 0, 2);
