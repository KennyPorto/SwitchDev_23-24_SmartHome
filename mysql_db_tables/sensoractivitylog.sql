create table sensoractivitylog
(
    sensoractivitylog_id bigint       not null
        primary key,
    measurement          varchar(255) null,
    sensor_id            bigint       null,
    timestamp            datetime(6)  null
);

INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (1, '-25.0', 1, '2024-05-04 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (2, '25.0', 4, '2024-05-05 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (3, '21.0', 3, '2024-05-06 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (4, '19.0', 2, '2024-06-15 15:42:53.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (5, '20.0', 5, '2024-05-07 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (6, 'ON', 6, '2024-05-08 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (7, '50', 7, '2024-05-09 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (8, '45', 8, '2024-05-10 22:01:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (9, '190', 9, '2024-05-11 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (10, '2500', 10, '2024-05-01 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (11, '3454', 11, '2024-06-07 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (12, '6.5', 12, '2024-02-04 22:01:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (13, '190.0', 13, '2024-03-04 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (14, '200.0', 14, '2024-03-05 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (15, '7.0', 15, '2024-03-04 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (16, '250.0', 16, '2024-02-04 22:01:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (17, '190.0', 17, '2024-06-04 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (18, 'ON', 18, '2024-01-04 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (19, '29.0', 19, '2024-02-04 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (20, 'ON', 20, '2024-04-04 22:01:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (21, 'ON', 21, '2024-06-04 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (22, 'ON', 22, '2024-03-04 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (23, 'ON', 23, '2024-04-04 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (24, 'ON', 24, '2024-01-04 22:01:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (25, '-25.0', 25, '2024-02-04 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (26, '25.0', 26, '2024-04-04 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (27, '21.0', 27, '2024-03-04 22:00:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (28, '50.0', 28, '2024-04-04 22:01:00.000000');
INSERT INTO smarthome.sensoractivitylog (sensoractivitylog_id, measurement, sensor_id, timestamp) VALUES (29, null, 29, '2024-04-04 22:01:00.000000');
