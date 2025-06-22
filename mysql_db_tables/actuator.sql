create table actuator
(
    discriminator    varchar(31)   not null,
    actuator_id      bigint        not null
        primary key,
    actuator_model   varchar(255)  null,
    actuator_name    varchar(255)  null,
    actuator_type_id varchar(255)  null,
    device_id        bigint        not null,
    version          int default 0 not null,
    lower_limit_dec  double        null,
    upper_limit_dec  double        null,
    lower_limit_int  int           null,
    upper_limit_int  int           null,
    current_value    int           null
);

INSERT INTO smarthome.actuator (discriminator, actuator_id, actuator_model, actuator_name, actuator_type_id, device_id, version, lower_limit_dec, upper_limit_dec, lower_limit_int, upper_limit_int, current_value) VALUES ('blind_roller', 1, 'BlindRollerActuator', 'Blind Roller', 'BlindRollerActuator', 22, 1, null, null, null, null, 44);
INSERT INTO smarthome.actuator (discriminator, actuator_id, actuator_model, actuator_name, actuator_type_id, device_id, version, lower_limit_dec, upper_limit_dec, lower_limit_int, upper_limit_int, current_value) VALUES ('blind_roller', 2, 'BlindRollerActuator', 'Blind Roller', 'BlindRollerActuator', 26, 1, null, null, null, null, null);
INSERT INTO smarthome.actuator (discriminator, actuator_id, actuator_model, actuator_name, actuator_type_id, device_id, version, lower_limit_dec, upper_limit_dec, lower_limit_int, upper_limit_int, current_value) VALUES ('blind_roller', 3, 'BlindRollerActuator', 'Blind Roller', 'BlindRollerActuator', 46, 1, null, null, null, null, null);
INSERT INTO smarthome.actuator (discriminator, actuator_id, actuator_model, actuator_name, actuator_type_id, device_id, version, lower_limit_dec, upper_limit_dec, lower_limit_int, upper_limit_int, current_value) VALUES ('blind_roller', 4, 'BlindRollerActuator', 'Blind Roller', 'BlindRollerActuator', 65, 1, null, null, null, null, null);
INSERT INTO smarthome.actuator (discriminator, actuator_id, actuator_model, actuator_name, actuator_type_id, device_id, version, lower_limit_dec, upper_limit_dec, lower_limit_int, upper_limit_int, current_value) VALUES ('blind_roller', 5, 'BlindRollerActuator', 'Blind Roller', 'BlindRollerActuator', 69, 1, null, null, null, null, null);
INSERT INTO smarthome.actuator (discriminator, actuator_id, actuator_model, actuator_name, actuator_type_id, device_id, version, lower_limit_dec, upper_limit_dec, lower_limit_int, upper_limit_int, current_value) VALUES ('blind_roller', 6, 'BlindRollerActuator', 'Blind Roller', 'BlindRollerActuator', 84, 1, null, null, null, null, null);
INSERT INTO smarthome.actuator (discriminator, actuator_id, actuator_model, actuator_name, actuator_type_id, device_id, version, lower_limit_dec, upper_limit_dec, lower_limit_int, upper_limit_int, current_value) VALUES ('blind_roller', 7, 'BlindRollerActuator', 'Blind Roller', 'BlindRollerActuator', 103, 1, null, null, null, null, null);
INSERT INTO smarthome.actuator (discriminator, actuator_id, actuator_model, actuator_name, actuator_type_id, device_id, version, lower_limit_dec, upper_limit_dec, lower_limit_int, upper_limit_int, current_value) VALUES ('blind_roller', 8, 'BlindRollerActuator', 'Blind Roller', 'BlindRollerActuator', 106, 1, null, null, null, null, null);
INSERT INTO smarthome.actuator (discriminator, actuator_id, actuator_model, actuator_name, actuator_type_id, device_id, version, lower_limit_dec, upper_limit_dec, lower_limit_int, upper_limit_int, current_value) VALUES ('blind_roller', 9, 'BlindRollerActuator', 'Blind Roller', 'BlindRollerActuator', 121, 1, null, null, null, null, null);
INSERT INTO smarthome.actuator (discriminator, actuator_id, actuator_model, actuator_name, actuator_type_id, device_id, version, lower_limit_dec, upper_limit_dec, lower_limit_int, upper_limit_int, current_value) VALUES ('ActuatorDataModel', 52, 'SwitchOnOffActuator', 'Turn On', 'Turn On', 30, 0, null, null, null, null, null);
INSERT INTO smarthome.actuator (discriminator, actuator_id, actuator_model, actuator_name, actuator_type_id, device_id, version, lower_limit_dec, upper_limit_dec, lower_limit_int, upper_limit_int, current_value) VALUES ('range_int', 102, 'RangeActuatorInt', 'Temperature', 'Remp', 252, 0, null, null, 0, 200, null);
INSERT INTO smarthome.actuator (discriminator, actuator_id, actuator_model, actuator_name, actuator_type_id, device_id, version, lower_limit_dec, upper_limit_dec, lower_limit_int, upper_limit_int, current_value) VALUES ('blind_roller', 103, 'BlindRollerActuator', 'Blind roller actuator', 'Blinds', 39, 0, null, null, null, null, 0);
INSERT INTO smarthome.actuator (discriminator, actuator_id, actuator_model, actuator_name, actuator_type_id, device_id, version, lower_limit_dec, upper_limit_dec, lower_limit_int, upper_limit_int, current_value) VALUES ('blind_roller', 104, 'BlindRollerActuator', 'BlindRoller', 'Shutter', 39, 0, null, null, null, null, 0);
INSERT INTO smarthome.actuator (discriminator, actuator_id, actuator_model, actuator_name, actuator_type_id, device_id, version, lower_limit_dec, upper_limit_dec, lower_limit_int, upper_limit_int, current_value) VALUES ('blind_roller', 105, 'BlindRollerActuator', 'Blind Roller', 'Shutter', 22, 0, null, null, null, null, 0);
