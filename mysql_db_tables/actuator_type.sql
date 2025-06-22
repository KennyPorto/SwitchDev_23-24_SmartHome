create table actuator_type
(
    id               varchar(255)  not null
        primary key,
    measurement_unit varchar(255)  null,
    version          int default 0 not null
);

INSERT INTO smarthome.actuator_type (id, measurement_unit, version) VALUES ('BlindRollerActuator', 'Percentage', 0);
INSERT INTO smarthome.actuator_type (id, measurement_unit, version) VALUES ('RangeActuatorInt', 'Int', 0);
INSERT INTO smarthome.actuator_type (id, measurement_unit, version) VALUES ('RangueActuatorDecimal', 'Decimal', 0);
INSERT INTO smarthome.actuator_type (id, measurement_unit, version) VALUES ('SwitchOnOffActuator', 'Binary', 0);
