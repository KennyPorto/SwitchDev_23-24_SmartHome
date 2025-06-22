create table actuatoractivitylog
(
    activity_log_id bigint       not null
        primary key,
    actuator_id     bigint       null,
    measurement     varchar(255) null,
    timestamp       datetime(6)  null
);

INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (1, 22, '1.0', '2024-05-05 00:44:28.513805');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (2, 22, '0', '2024-06-05 11:15:20.378000');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (3, 22, '44', '2024-06-15 12:01:47.973243');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (4, 26, '1.0', '2024-04-12 12:44:28.513000');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (5, 26, '0', '2024-05-05 22:15:20.378318');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (6, 26, '55', '2024-05-15 00:44:28.513000');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (7, 46, '1.0', '2024-06-05 11:15:20.378318');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (8, 46, '67', '2024-06-15 18:44:28.513805');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (9, 46, '0', '2024-06-15 18:15:20.378318');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (10, 65, '1.0', '2024-05-05 05:44:28.513000');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (11, 65, '0', '2024-05-08 23:15:20.378318');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (12, 65, '77', '2024-05-10 12:44:28.513805');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (13, 69, '0', '2024-06-05 21:15:20.378318');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (14, 69, '1.0', '2024-05-05 00:44:28.513000');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (15, 69, '54', '2024-06-05 11:15:20.378318');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (16, 84, '1.0', '2024-05-05 06:44:28.513805');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (17, 84, '0', '2024-05-05 21:15:20.378318');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (18, 84, '45', '2024-05-06 09:44:28.513805');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (19, 103, '0', '2024-04-21 23:15:20.378318');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (20, 103, '44', '2024-04-22 12:01:47.973243');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (21, 103, '1.0', '2024-05-05 00:44:28.513000');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (22, 106, '0', '2024-06-05 12:15:20.378000');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (23, 106, '67', '2024-06-15 15:01:47.973000');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (24, 106, '1.0', '2024-05-15 12:44:28.513805');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (25, 22, '0', '2024-06-05 12:15:20.378000');
INSERT INTO smarthome.actuatoractivitylog (activity_log_id, actuator_id, measurement, timestamp) VALUES (26, 26, '44', '2024-06-15 12:01:47.973000');
