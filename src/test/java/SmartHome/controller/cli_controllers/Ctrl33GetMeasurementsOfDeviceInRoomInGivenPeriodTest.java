package SmartHome.controller.cli_controllers;

import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.actuators.ActuatorFactory;
import SmartHome.domain.actuatortype.ActuatorType;
import SmartHome.domain.actuatortype.ActuatorTypeFactory;
import SmartHome.domain.device.Device;
import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.repository.*;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.sensors.SensorFactory;
import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.sensortype.SensorTypeFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.*;
import SmartHome.persistence.mem.*;
import SmartHome.service.DeviceLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Ctrl33GetMeasurementsOfDeviceInRoomInGivenPeriodTest
{
    private DeviceRepository deviceRepository;
    private SensorRepository sensorRepository;
    private ActuatorRepository actuatorRepository;
    private SensorActivityLogRepository sensorActivityLogRepository;
    private ActuatorActivityLogRepository actuatorActivityLogRepository;
    private DeviceLogService deviceLogService;

    @BeforeEach
    void setup() {
        deviceRepository = new DeviceRepositoryImplMem();
        sensorRepository = new SensorRepositoryImplMem();
        actuatorRepository = new ActuatorRepositoryImplMem();
        sensorActivityLogRepository = mock(SensorActivityLogRepository.class);
        actuatorActivityLogRepository = mock(ActuatorActivityLogRepository.class);
        deviceLogService = new DeviceLogService(deviceRepository, sensorRepository, actuatorRepository,
                sensorActivityLogRepository, actuatorActivityLogRepository);
    }

    @Test
    void getAllLogsFromDeviceSensorAndActuatorLog_successful() {
        String sensorModel = "TemperatureSensor";
        String actuatorModel = "BlindRollerActuator";
        long roomId = 1L;
        long deviceId = 1L;
        String start = "2024-04-23T00:00:00Z";
        String end = "2024-04-24T00:00:00Z";
        TimePeriodDTO dto = new TimePeriodDTO(start, end);
        DeviceID devId = new DeviceID(deviceId);
        RoomId roomId1 = new RoomId(roomId);
        ActuatorId actuatorId = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("BlindRoller");
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        Device device = new DeviceFactory().createDevice(roomId1, devId, deviceName, deviceModel, new DeviceStatus(true));
        Sensor sensor = new SensorFactory().createSensor(devId, new SensorModel(sensorModel), new SensorName("s1"),
                new SensorId(1L), new SensorTypeId("Temperature"));
        Actuator actuator = new ActuatorFactory().createActuator(devId, new ActuatorModel(actuatorModel),
                new ActuatorName("a1"), actuatorId, actuatorTypeId);
        SensorType sensorType = new SensorTypeFactory().createSensorType(new SensorTypeId("Temperature"),
                MeasurementUnit.Celsius);
        ActuatorType actuatorType = new ActuatorTypeFactory().createActuatorType(actuatorTypeId,
                MeasurementUnit.Percentage);

        SensorTypeRepository sensorTypeRepository = new SensorTypeRepositoryImplMem();
        ActuatorTypeRepository actuatorTypeRepository = new ActuatorTypeRepositoryImplMem();
        sensorTypeRepository.save(sensorType);
        actuatorTypeRepository.save(actuatorType);
        deviceRepository.save(device);
        sensorRepository.save(sensor);
        actuatorRepository.save(actuator);

        ActivityLogId activityLogId = new ActivityLogId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.parse("2024-04-23T01:00:00Z"));
        SensorId sensorId = new SensorId(1L);
        Measurement measurement = new Measurement("1.0");
        SensorActivityLog sensorActivityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, measurement);
        Iterable<SensorActivityLog> sensorActivityLogs = List.of(sensorActivityLog);
        when(sensorActivityLogRepository.findAllBySensorIdAndTimestampBetween(1L,
                ZonedDateTime.parse(dto.startDate), ZonedDateTime.parse(dto.endDate)))
                .thenReturn(sensorActivityLogs);

        ActivityLogId activityLogId1 = new ActivityLogId(2L);
        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(activityLogId1, actuatorId, timeStamp, measurement);
        Iterable<ActuatorActivityLog> actuatorActivityLogs = List.of(actuatorActivityLog);
        when(actuatorActivityLogRepository.findAllByActuatorIdAndTimestampBetween(actuatorId.id,
                ZonedDateTime.parse(dto.startDate), ZonedDateTime.parse(dto.endDate)))
                .thenReturn(actuatorActivityLogs);

        Ctrl33GetMeasurementsOfDeviceInRoomInGivenPeriod ctrl33GetMeasurementsOfDeviceInRoomInGivenPeriod = new Ctrl33GetMeasurementsOfDeviceInRoomInGivenPeriod(deviceLogService);
        List<SensorActivityLogDTO> sensorLogsDto = ActivityLogMapper
                .sensorLogAndMeasurementToDto(List.of(sensorActivityLogs.iterator().next()));
        List<ActuatorActivityLogDTO> actuatorLogsDto = ActivityLogMapper
                .actuatorLogAndMeasurementToDto(List.of(actuatorActivityLogs.iterator().next()));

        // Act
        DeviceRecordsDTO result = ctrl33GetMeasurementsOfDeviceInRoomInGivenPeriod.getAllLogs(deviceId, dto);
        DeviceRecordsDTO expected = new DeviceRecordsDTO(actuatorLogsDto, sensorLogsDto);

        // Assert
        assertEquals(expected.sensorLogs.iterator().next().sensorActivityLogId,
                result.sensorLogs.iterator().next().sensorActivityLogId);
        assertEquals(expected.actuatorLogs.iterator().next().actuatorActivityLogId,
                result.actuatorLogs.iterator().next().actuatorActivityLogId);
    }

    @Test
    void getAllLogsFromDevice_nonExistingRoomId() {
        String sensorModel = "TemperatureSensor";
        long deviceId = 1L;
        String start = "2024-04-23T00:00:00Z";
        String end = "2024-04-24T00:00:00Z";
        TimePeriodDTO dto = new TimePeriodDTO(start, end);
        DeviceID devId = new DeviceID(deviceId);
        Sensor sensor = new SensorFactory().createSensor(devId, new SensorModel(sensorModel), new SensorName("s1"),
                new SensorId(1L), new SensorTypeId("Temperature"));
        SensorType sensorType = new SensorTypeFactory().createSensorType(new SensorTypeId("Temperature"),
                MeasurementUnit.Celsius);

        SensorTypeRepository sensorTypeRepository = new SensorTypeRepositoryImplMem();
        sensorTypeRepository.save(sensorType);
        sensorRepository.save(sensor);

        ActivityLogId activityLogId = new ActivityLogId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.parse("2024-04-23T01:00:00Z"));
        SensorId sensorId = new SensorId(1L);
        Measurement measurement = new Measurement("1.0");
        SensorActivityLog sensorActivityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, measurement);
        Iterable<SensorActivityLog> sensorActivityLogs = List.of(sensorActivityLog);
        when(sensorActivityLogRepository.findAllBySensorIdAndTimestampBetween(1L,
                ZonedDateTime.parse(dto.startDate), ZonedDateTime.parse(dto.endDate)))
                .thenReturn(sensorActivityLogs);

        when(actuatorActivityLogRepository.findAllByActuatorIdAndTimestampBetween(2L,
                ZonedDateTime.parse(dto.startDate), ZonedDateTime.parse(dto.endDate)))
                .thenReturn(List.of());

        Ctrl33GetMeasurementsOfDeviceInRoomInGivenPeriod ctrl33GetMeasurementsOfDeviceInRoomInGivenPeriod =
              new Ctrl33GetMeasurementsOfDeviceInRoomInGivenPeriod(deviceLogService);

        // Act
        IllegalArgumentException message = assertThrows(IllegalArgumentException.class,
                () -> ctrl33GetMeasurementsOfDeviceInRoomInGivenPeriod.getAllLogs(deviceId, dto));
        String expected = "Device not found or Device don't exist in room";

        // Assert
        assertEquals(expected, message.getMessage());
    }

    @Test
    void getAllLogsFromDevice_nonExistingDeviceId() {
        String sensorModel = "TemperatureSensor";
        long roomId = 1L;
        long deviceId = 1L;
        long deviceId2 = 2L;
        String start = "2024-04-23T00:00:00Z";
        String end = "2024-04-24T00:00:00Z";
        TimePeriodDTO dto = new TimePeriodDTO(start, end);
        DeviceID devId = new DeviceID(deviceId);
        RoomId roomId1 = new RoomId(roomId);
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        Device device = new DeviceFactory().createDevice(roomId1, devId, deviceName, deviceModel, new DeviceStatus(true));
        Sensor sensor = new SensorFactory().createSensor(devId, new SensorModel(sensorModel), new SensorName("s1"),
                new SensorId(1L), new SensorTypeId("Temperature"));
        SensorType sensorType = new SensorTypeFactory().createSensorType(new SensorTypeId("Temperature"),
                MeasurementUnit.Celsius);

        SensorTypeRepository sensorTypeRepository = new SensorTypeRepositoryImplMem();
        sensorTypeRepository.save(sensorType);
        deviceRepository.save(device);
        sensorRepository.save(sensor);

        ActivityLogId activityLogId = new ActivityLogId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.parse("2024-04-23T01:00:00Z"));
        SensorId sensorId = new SensorId(1L);
        Measurement measurement = new Measurement("1.0");
        SensorActivityLog sensorActivityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, measurement);
        Iterable<SensorActivityLog> sensorActivityLogs = List.of(sensorActivityLog);
        when(sensorActivityLogRepository.findAllBySensorIdAndTimestampBetween(1L,
                ZonedDateTime.parse(dto.startDate), ZonedDateTime.parse(dto.endDate)))
                .thenReturn(sensorActivityLogs);

        when(actuatorActivityLogRepository.findAllByActuatorIdAndTimestampBetween(2L,
                ZonedDateTime.parse(dto.startDate), ZonedDateTime.parse(dto.endDate)))
                .thenReturn(List.of());

        Ctrl33GetMeasurementsOfDeviceInRoomInGivenPeriod ctrl33GetMeasurementsOfDeviceInRoomInGivenPeriod = new Ctrl33GetMeasurementsOfDeviceInRoomInGivenPeriod(deviceLogService);

        // Assert
        IllegalArgumentException message = assertThrows(IllegalArgumentException.class,
                () -> ctrl33GetMeasurementsOfDeviceInRoomInGivenPeriod.getAllLogs(deviceId2, dto));
        String expected = "Device not found or Device don't exist in room";

        assertEquals(expected, message.getMessage());
    }

}