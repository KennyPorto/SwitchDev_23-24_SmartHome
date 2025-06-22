create table sensor_type
(
    id               varchar(255)  not null
        primary key,
    measurement_unit varchar(255)  null,
    version          int default 0 not null
);

INSERT INTO smarthome.sensor_type (id, measurement_unit, version) VALUES ('AveragePowerConsumptionSensor', 'WattsPerHour', 0);
INSERT INTO smarthome.sensor_type (id, measurement_unit, version) VALUES ('BinarySensor', 'Binary', 0);
INSERT INTO smarthome.sensor_type (id, measurement_unit, version) VALUES ('DewPointSensor', 'Percentage', 0);
INSERT INTO smarthome.sensor_type (id, measurement_unit, version) VALUES ('ElectricEnergyConsumputionSensor', 'WattsPerHour', 0);
INSERT INTO smarthome.sensor_type (id, measurement_unit, version) VALUES ('HumiditySensor', 'Percentage', 0);
INSERT INTO smarthome.sensor_type (id, measurement_unit, version) VALUES ('InstantPowerConsumptionSensor', 'WattsPerHour', 0);
INSERT INTO smarthome.sensor_type (id, measurement_unit, version) VALUES ('ScaleSensor', 'Percentage', 0);
INSERT INTO smarthome.sensor_type (id, measurement_unit, version) VALUES ('SolarIrradianceSensor', 'WattsPerHour', 0);
INSERT INTO smarthome.sensor_type (id, measurement_unit, version) VALUES ('SunriseSensor', 'Time', 0);
INSERT INTO smarthome.sensor_type (id, measurement_unit, version) VALUES ('SunsetSensor', 'Time', 0);
INSERT INTO smarthome.sensor_type (id, measurement_unit, version) VALUES ('TemperatureSensor', 'Celsius', 0);
INSERT INTO smarthome.sensor_type (id, measurement_unit, version) VALUES ('WindSensor', 'Speed', 0);
