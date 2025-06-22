package SmartHome.controller.cli_controllers;

import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.device.Device;
import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.repository.DeviceRepository;
import SmartHome.domain.repository.RoomRepository;
import SmartHome.domain.repository.SensorActivityLogRepository;
import SmartHome.domain.repository.SensorTypeRepository;
import SmartHome.domain.room.Room;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.sensors.SensorFactory;
import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.sensortype.SensorTypeFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.MaxInstantTempDiffDTO;
import SmartHome.mapper.TimePeriodDTO;
import SmartHome.persistence.mem.DeviceRepositoryImplMem;
import SmartHome.persistence.mem.RoomRepositoryImplMem;
import SmartHome.persistence.mem.SensorRepositoryImplMem;
import SmartHome.persistence.mem.SensorTypeRepositoryImplMem;
import SmartHome.service.EnvironmentalTemperatureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Ctrl34GetMaxInstantaneousTempDifOutsideInsideTest
{
    private RoomRepository roomRepository;
    private DeviceRepository deviceRepository;
    private SensorRepositoryImplMem sensorRepository;
    private SensorTypeRepository sensorTypeRepository;
    private SensorActivityLogRepository sensorActivityLogRepository;
    private EnvironmentalTemperatureService environmentalTemperatureService;

    @BeforeEach
    void setup() {
        roomRepository = new RoomRepositoryImplMem();
        deviceRepository = new DeviceRepositoryImplMem();
        sensorTypeRepository = new SensorTypeRepositoryImplMem();
        sensorRepository = new SensorRepositoryImplMem();
        sensorActivityLogRepository = mock(SensorActivityLogRepository.class);
        environmentalTemperatureService = new EnvironmentalTemperatureService(roomRepository, sensorRepository, sensorTypeRepository, deviceRepository, sensorActivityLogRepository);
    }

    @Test
    void getMaximumTemperatureDifference_successful() {
        String sensorModel = "TemperatureSensor";
        long indoorRoomId = 1L;
        long outdoorRoomId = 2L;
        long indoorDeviceId = 1L;
        long outdoorDeviceId = 2L;
        String start = "2024-04-23T00:00:00Z";
        String end = "2024-04-24T00:00:00Z";
        TimePeriodDTO dto = new TimePeriodDTO(start, end);
        DeviceID indoorDevId = new DeviceID(indoorDeviceId);
        DeviceID outdoorDevId = new DeviceID(outdoorDeviceId);
        RoomId indoorRoomID = new RoomId(indoorRoomId);
        RoomId outdoorRoomID = new RoomId(outdoorRoomId);
        Room indoorRoom = new RoomFactory().createRoom(new RoomName("r1"), indoorRoomID, new HouseId(1L), new Floor("F1"), mock(Dimensions.class), OutdoorIndoor.INDOOR);
        Room outdoorRoom = new RoomFactory().createRoom(new RoomName("r2"), outdoorRoomID, new HouseId(1L), new Floor("F1"), mock(Dimensions.class), OutdoorIndoor.OUTDOOR);
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        Device indoorDevice = new DeviceFactory().createDevice(indoorRoomID, indoorDevId, deviceName, deviceModel, new DeviceStatus(true));
        Device outdoorDevice = new DeviceFactory().createDevice(outdoorRoomID, outdoorDevId, deviceName, deviceModel, new DeviceStatus(true));

        Sensor indoorSensor = new SensorFactory().createSensor(indoorDevId, new SensorModel(sensorModel), new SensorName("s1"),
                new SensorId(1L), new SensorTypeId("Temperature"));
        Sensor outdoorSensor = new SensorFactory().createSensor(outdoorDevId, new SensorModel(sensorModel), new SensorName("s2"),
                new SensorId(2L), new SensorTypeId("Temperature"));
        SensorType sensorType = new SensorTypeFactory().createSensorType(new SensorTypeId("Temperature"),
                MeasurementUnit.Celsius);

        roomRepository.save(indoorRoom);
        roomRepository.save(outdoorRoom);
        sensorTypeRepository.save(sensorType);
        deviceRepository.save(indoorDevice);
        deviceRepository.save(outdoorDevice);
        sensorRepository.save(indoorSensor);
        sensorRepository.save(outdoorSensor);

        ActivityLogId activityLogId1 = new ActivityLogId(1L);
        TimeStamp timeStamp1 = new TimeStamp(ZonedDateTime.parse("2024-04-23T01:00:00Z"));
        SensorId sensorId1 = new SensorId(1L);
        Measurement measurement1 = new Measurement("1.0");
        SensorActivityLog sensorActivityLog1 = new SensorActivityLog(activityLogId1, timeStamp1, sensorId1, measurement1);
        Iterable<SensorActivityLog> sensorActivityLogs1 = List.of(sensorActivityLog1);
        when(sensorActivityLogRepository.findAllBySensorIdAndTimestampBetween(1L,
                ZonedDateTime.parse(dto.startDate), ZonedDateTime.parse(dto.endDate)))
                .thenReturn(sensorActivityLogs1);

        ActivityLogId activityLogId2 = new ActivityLogId(2L);
        TimeStamp timeStamp2 = new TimeStamp(ZonedDateTime.parse("2024-04-23T03:00:00Z"));
        SensorId sensorId2 = new SensorId(2L);
        Measurement measurement2 = new Measurement("5.0");
        SensorActivityLog sensorActivityLog2 = new SensorActivityLog(activityLogId2, timeStamp2, sensorId2, measurement2);
        Iterable<SensorActivityLog> sensorActivityLogs2 = List.of(sensorActivityLog2);
        when(sensorActivityLogRepository.findAllBySensorIdAndTimestampBetween(2L,
                ZonedDateTime.parse(dto.startDate), ZonedDateTime.parse(dto.endDate)))
                .thenReturn(sensorActivityLogs2);

        Ctrl34GetMaxInstantaneousTempDifOutsideInside ctrl34GetMaxInstantaneousTempDifOutsideInside =
                new Ctrl34GetMaxInstantaneousTempDifOutsideInside(environmentalTemperatureService);
        TimePeriodDTO timePeriodDTO = new TimePeriodDTO(start, end);

        // Act
        MaxInstantTempDiffDTO result = ctrl34GetMaxInstantaneousTempDifOutsideInside
                .getMaximumTemperatureDifference(1L, timePeriodDTO, Long.MAX_VALUE);
        MaxInstantTempDiffDTO expected = new MaxInstantTempDiffDTO(4.0);

        // Assert

        assertEquals(result.tempDiff, expected.tempDiff);
    }

    @Test
    void getMaximumTemperatureDifference_inexistentTemperatureDeviceInAnIndoorRoom() {
        String sensorModel = "TemperatureSensor";
        long outdoorRoomId = 2L;
        long outdoorDeviceId = 2L;
        String start = "2024-04-23T00:00:00Z";
        String end = "2024-04-24T00:00:00Z";
        DeviceID outdoorDevId = new DeviceID(outdoorDeviceId);
        RoomId outdoorRoomID = new RoomId(outdoorRoomId);
        Room outdoorRoom = new RoomFactory().createRoom(new RoomName("r2"), outdoorRoomID, new HouseId(1L),
                new Floor("F1"), mock(Dimensions.class), OutdoorIndoor.OUTDOOR);
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        Device outdoorDevice = new DeviceFactory()
                .createDevice(outdoorRoomID, outdoorDevId, deviceName, deviceModel, new DeviceStatus(true));

        Sensor outdoorSensor = new SensorFactory().createSensor(outdoorDevId, new SensorModel(sensorModel), new SensorName("s2"),
                new SensorId(2L), new SensorTypeId("Temperature"));
        SensorType sensorType = new SensorTypeFactory().createSensorType(new SensorTypeId("Temperature"),
                MeasurementUnit.Celsius);

        roomRepository.save(outdoorRoom);
        sensorTypeRepository.save(sensorType);
        deviceRepository.save(outdoorDevice);
        sensorRepository.save(outdoorSensor);

        Ctrl34GetMaxInstantaneousTempDifOutsideInside ctrl34GetMaxInstantaneousTempDifOutsideInside =
                new Ctrl34GetMaxInstantaneousTempDifOutsideInside(environmentalTemperatureService);
        TimePeriodDTO timePeriodDTO = new TimePeriodDTO(start, end);

        // Act

        IllegalArgumentException message = assertThrows(IllegalArgumentException.class,
                () -> ctrl34GetMaxInstantaneousTempDifOutsideInside.getMaximumTemperatureDifference(2L,
                        timePeriodDTO, Long.MAX_VALUE));
        String expected = "The device must be in an indoor room";

        // Assert
        assertEquals(expected, message.getMessage());
    }

    @Test
    void getMaximumTemperatureDifference_endDateBeforeStartDate() {
        String sensorModel = "TemperatureSensor";
        long outdoorRoomId = 2L;
        long outdoorDeviceId = 2L;
        String start = "2024-04-25T00:00:00Z";
        String end = "2024-04-24T00:00:00Z";
        DeviceID outdoorDevId = new DeviceID(outdoorDeviceId);
        RoomId outdoorRoomID = new RoomId(outdoorRoomId);
        Room outdoorRoom = new RoomFactory().createRoom(new RoomName("r2"), outdoorRoomID, new HouseId(1L),
                new Floor("F1"), mock(Dimensions.class), OutdoorIndoor.INDOOR);
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        Device outdoorDevice = new DeviceFactory()
                .createDevice(outdoorRoomID, outdoorDevId, deviceName, deviceModel, new DeviceStatus(true));

        Sensor outdoorSensor = new SensorFactory().createSensor(outdoorDevId, new SensorModel(sensorModel), new SensorName("s2"),
                new SensorId(2L), new SensorTypeId("Temperature"));
        SensorType sensorType = new SensorTypeFactory().createSensorType(new SensorTypeId("Temperature"),
                MeasurementUnit.Celsius);

        roomRepository.save(outdoorRoom);
        sensorTypeRepository.save(sensorType);
        deviceRepository.save(outdoorDevice);
        sensorRepository.save(outdoorSensor);

        Ctrl34GetMaxInstantaneousTempDifOutsideInside ctrl34GetMaxInstantaneousTempDifOutsideInside =
                new Ctrl34GetMaxInstantaneousTempDifOutsideInside(environmentalTemperatureService);
        TimePeriodDTO timePeriodDTO = new TimePeriodDTO(start, end);

        // Act

        IllegalArgumentException message = assertThrows(IllegalArgumentException.class,
                () -> ctrl34GetMaxInstantaneousTempDifOutsideInside.getMaximumTemperatureDifference(2L,
                        timePeriodDTO, Long.MAX_VALUE));
        String expected = "The start date must be before the end date";

        // Assert
        assertEquals(expected, message.getMessage());
    }

    @Test
    void getMaximumTemperatureDifference_noActivityLogInOutdoorSensors() {
        String sensorModel = "TemperatureSensor";
        long indoorRoomId = 1L;
        long outdoorRoomId = 2L;
        long indoorDeviceId = 1L;
        long outdoorDeviceId = 2L;
        String start = "2024-04-23T00:00:00Z";
        String end = "2024-04-24T00:00:00Z";
        TimePeriodDTO dto = new TimePeriodDTO(start, end);
        DeviceID indoorDevId = new DeviceID(indoorDeviceId);
        DeviceID outdoorDevId = new DeviceID(outdoorDeviceId);
        RoomId indoorRoomID = new RoomId(indoorRoomId);
        RoomId outdoorRoomID = new RoomId(outdoorRoomId);
        Room indoorRoom = new RoomFactory().createRoom(new RoomName("r1"), indoorRoomID, new HouseId(1L),
                new Floor("F1"), mock(Dimensions.class), OutdoorIndoor.INDOOR);
        Room outdoorRoom = new RoomFactory().createRoom(new RoomName("r2"), outdoorRoomID, new HouseId(1L),
                new Floor("F1"), mock(Dimensions.class), OutdoorIndoor.OUTDOOR);
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        Device indoorDevice = new DeviceFactory()
                .createDevice(indoorRoomID, indoorDevId, deviceName, deviceModel, new DeviceStatus(true));
        Device outdoorDevice = new DeviceFactory()
                .createDevice(outdoorRoomID, outdoorDevId, deviceName, deviceModel, new DeviceStatus(true));

        Sensor indoorSensor = new SensorFactory().createSensor(indoorDevId, new SensorModel(sensorModel), new SensorName("s1"),
                new SensorId(1L), new SensorTypeId("Temperature"));
        Sensor outdoorSensor = new SensorFactory().createSensor(outdoorDevId, new SensorModel(sensorModel), new SensorName("s2"),
                new SensorId(2L), new SensorTypeId("Temperature"));
        SensorType sensorType = new SensorTypeFactory().createSensorType(new SensorTypeId("Temperature"),
                MeasurementUnit.Celsius);

        roomRepository.save(indoorRoom);
        roomRepository.save(outdoorRoom);
        sensorTypeRepository.save(sensorType);
        deviceRepository.save(indoorDevice);
        deviceRepository.save(outdoorDevice);
        sensorRepository.save(indoorSensor);
        sensorRepository.save(outdoorSensor);

        ActivityLogId activityLogId1 = new ActivityLogId(1L);
        TimeStamp timeStamp1 = new TimeStamp(ZonedDateTime.parse("2024-04-23T01:00:00Z"));
        SensorId sensorId1 = new SensorId(1L);
        Measurement measurement1 = new Measurement("1.0");
        SensorActivityLog sensorActivityLog1 = new SensorActivityLog(activityLogId1, timeStamp1, sensorId1, measurement1);
        Iterable<SensorActivityLog> sensorActivityLogs1 = List.of(sensorActivityLog1);
        when(sensorActivityLogRepository.findAllBySensorIdAndTimestampBetween(1L,
                ZonedDateTime.parse(dto.startDate), ZonedDateTime.parse(dto.endDate)))
                .thenReturn(sensorActivityLogs1);

        Iterable<SensorActivityLog> emptySensorActivityLogs = new ArrayList<>();
        when(sensorActivityLogRepository.findAllBySensorIdAndTimestampBetween(2L,
                ZonedDateTime.parse(dto.startDate), ZonedDateTime.parse(dto.endDate)))
                .thenReturn(emptySensorActivityLogs);

        Ctrl34GetMaxInstantaneousTempDifOutsideInside ctrl34GetMaxInstantaneousTempDifOutsideInside =
                new Ctrl34GetMaxInstantaneousTempDifOutsideInside(environmentalTemperatureService);
        TimePeriodDTO timePeriodDTO = new TimePeriodDTO(start, end);

        // Act

        IllegalArgumentException message = assertThrows(IllegalArgumentException.class,
                () -> ctrl34GetMaxInstantaneousTempDifOutsideInside.getMaximumTemperatureDifference(1L,
                        timePeriodDTO, Long.MAX_VALUE));
        String expected = "There must be at least 1 outdoor log in the given time period";

        // Assert
        assertEquals(expected, message.getMessage());
    }

    @Test
    void getMaximumTemperatureDifference_noOutdoorTemperatureSensors() {
        String sensorModel = "TemperatureSensor";
        long indoorRoomId = 1L;
        long outdoorRoomId = 2L;
        long indoorDeviceId = 1L;
        long outdoorDeviceId = 2L;
        String start = "2024-04-23T00:00:00Z";
        String end = "2024-04-24T00:00:00Z";
        TimePeriodDTO dto = new TimePeriodDTO(start, end);
        DeviceID indoorDevId = new DeviceID(indoorDeviceId);
        DeviceID outdoorDevId = new DeviceID(outdoorDeviceId);
        RoomId indoorRoomID = new RoomId(indoorRoomId);
        RoomId outdoorRoomID = new RoomId(outdoorRoomId);
        Room indoorRoom = new RoomFactory().createRoom(new RoomName("r1"), indoorRoomID, new HouseId(1L),
                new Floor("F1"), mock(Dimensions.class), OutdoorIndoor.INDOOR);
        Room outdoorRoom = new RoomFactory().createRoom(new RoomName("r2"), outdoorRoomID, new HouseId(1L),
                new Floor("F1"), mock(Dimensions.class), OutdoorIndoor.OUTDOOR);
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        Device indoorDevice = new DeviceFactory()
                .createDevice(indoorRoomID, indoorDevId, deviceName, deviceModel, new DeviceStatus(true));
        Device outdoorDevice = new DeviceFactory()
                .createDevice(outdoorRoomID, outdoorDevId, deviceName, deviceModel, new DeviceStatus(true));

        Sensor indoorSensor = new SensorFactory().createSensor(indoorDevId, new SensorModel(sensorModel), new SensorName("s1"),
                new SensorId(1L), new SensorTypeId("Temperature"));
        SensorType sensorType = new SensorTypeFactory().createSensorType(new SensorTypeId("Temperature"),
                MeasurementUnit.Celsius);

        roomRepository.save(indoorRoom);
        roomRepository.save(outdoorRoom);
        sensorTypeRepository.save(sensorType);
        deviceRepository.save(indoorDevice);
        deviceRepository.save(outdoorDevice);
        sensorRepository.save(indoorSensor);

        ActivityLogId activityLogId1 = new ActivityLogId(1L);
        TimeStamp timeStamp1 = new TimeStamp(ZonedDateTime.parse("2024-04-23T01:00:00Z"));
        SensorId sensorId1 = new SensorId(1L);
        Measurement measurement1 = new Measurement("1.0");
        SensorActivityLog sensorActivityLog1 = new SensorActivityLog(activityLogId1, timeStamp1, sensorId1, measurement1);
        Iterable<SensorActivityLog> sensorActivityLogs1 = List.of(sensorActivityLog1);
        when(sensorActivityLogRepository.findAllBySensorIdAndTimestampBetween(1L,
                ZonedDateTime.parse(dto.startDate), ZonedDateTime.parse(dto.endDate)))
                .thenReturn(sensorActivityLogs1);

        Ctrl34GetMaxInstantaneousTempDifOutsideInside ctrl34GetMaxInstantaneousTempDifOutsideInside = new Ctrl34GetMaxInstantaneousTempDifOutsideInside(environmentalTemperatureService);
        TimePeriodDTO timePeriodDTO = new TimePeriodDTO(start, end);

        // Act

        IllegalArgumentException message = assertThrows(IllegalArgumentException.class,
                () -> ctrl34GetMaxInstantaneousTempDifOutsideInside.getMaximumTemperatureDifference(1L, timePeriodDTO, Long.MAX_VALUE));
        String expected = "There must be at least 1 outdoor temperature sensor";

        // Assert
        assertEquals(expected, message.getMessage());
    }

    @Test
    void getMaximumTemperatureDifference_noOutdoorRooms() {
        String sensorModel = "TemperatureSensor";
        long indoorRoomId = 1L;
        long outdoorRoomId = 2L;
        long indoorDeviceId = 1L;
        String start = "2024-04-23T00:00:00Z";
        String end = "2024-04-24T00:00:00Z";
        TimePeriodDTO dto = new TimePeriodDTO(start, end);
        DeviceID indoorDevId = new DeviceID(indoorDeviceId);
        RoomId indoorRoomID = new RoomId(indoorRoomId);
        RoomId outdoorRoomID = new RoomId(outdoorRoomId);
        Room indoorRoom = new RoomFactory().createRoom(new RoomName("r1"), indoorRoomID, new HouseId(1L),
                new Floor("F1"), mock(Dimensions.class), OutdoorIndoor.INDOOR);
        Room outdoorRoom = new RoomFactory().createRoom(new RoomName("r2"), outdoorRoomID, new HouseId(1L),
                new Floor("F1"), mock(Dimensions.class), OutdoorIndoor.OUTDOOR);
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        Device indoorDevice = new DeviceFactory()
                .createDevice(indoorRoomID, indoorDevId, deviceName, deviceModel, new DeviceStatus(true));

        Sensor indoorSensor = new SensorFactory().createSensor(indoorDevId, new SensorModel(sensorModel), new SensorName("s1"),
                new SensorId(1L), new SensorTypeId("Temperature"));
        SensorType sensorType = new SensorTypeFactory().createSensorType(new SensorTypeId("Temperature"),
                MeasurementUnit.Celsius);

        roomRepository.save(indoorRoom);
        roomRepository.save(outdoorRoom);
        sensorTypeRepository.save(sensorType);
        deviceRepository.save(indoorDevice);
        sensorRepository.save(indoorSensor);

        ActivityLogId activityLogId1 = new ActivityLogId(1L);
        TimeStamp timeStamp1 = new TimeStamp(ZonedDateTime.parse("2024-04-23T01:00:00Z"));
        SensorId sensorId1 = new SensorId(1L);
        Measurement measurement1 = new Measurement("1.0");
        SensorActivityLog sensorActivityLog1 = new SensorActivityLog(activityLogId1, timeStamp1, sensorId1, measurement1);
        Iterable<SensorActivityLog> sensorActivityLogs1 = List.of(sensorActivityLog1);
        when(sensorActivityLogRepository.findAllBySensorIdAndTimestampBetween(1L,
                ZonedDateTime.parse(dto.startDate), ZonedDateTime.parse(dto.endDate)))
                .thenReturn(sensorActivityLogs1);

        Ctrl34GetMaxInstantaneousTempDifOutsideInside ctrl34GetMaxInstantaneousTempDifOutsideInside =
                new Ctrl34GetMaxInstantaneousTempDifOutsideInside(environmentalTemperatureService);
        TimePeriodDTO timePeriodDTO = new TimePeriodDTO(start, end);

        // Act

        IllegalArgumentException message = assertThrows(IllegalArgumentException.class,
                () -> ctrl34GetMaxInstantaneousTempDifOutsideInside.getMaximumTemperatureDifference(1L,
                        timePeriodDTO, Long.MAX_VALUE));
        String expected = "There must be at least 1 device in the outdoor room";

        // Assert
        assertEquals(expected, message.getMessage());
    }


    @Test
    void getMaximumTemperatureDifference_noIndoorTemperatureSensors() {
        String sensorModel = "TemperatureSensor";
        long indoorRoomId = 1L;
        long outdoorRoomId = 2L;
        long indoorDeviceId = 1L;
        String start = "2024-04-23T00:00:00Z";
        String end = "2024-04-24T00:00:00Z";
        TimePeriodDTO dto = new TimePeriodDTO(start, end);
        DeviceID indoorDevId = new DeviceID(indoorDeviceId);
        RoomId indoorRoomID = new RoomId(indoorRoomId);
        RoomId outdoorRoomID = new RoomId(outdoorRoomId);
        Room indoorRoom = new RoomFactory().createRoom(new RoomName("r1"), indoorRoomID, new HouseId(1L),
                new Floor("F1"), mock(Dimensions.class), OutdoorIndoor.INDOOR);
        Room outdoorRoom = new RoomFactory().createRoom(new RoomName("r2"), outdoorRoomID, new HouseId(1L),
                new Floor("F1"), mock(Dimensions.class), OutdoorIndoor.OUTDOOR);
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        Device indoorDevice = new DeviceFactory()
                .createDevice(indoorRoomID, indoorDevId, deviceName, deviceModel, new DeviceStatus(true));

        Sensor indoorSensor = new SensorFactory().createSensor(indoorDevId, new SensorModel(sensorModel), new SensorName("s1"),
                new SensorId(1L), new SensorTypeId("Temperature"));
        SensorType sensorType = new SensorTypeFactory().createSensorType(new SensorTypeId("Temperature"),
                MeasurementUnit.Celsius);

        roomRepository.save(indoorRoom);
        roomRepository.save(outdoorRoom);
        sensorTypeRepository.save(sensorType);
        deviceRepository.save(indoorDevice);
        sensorRepository.save(indoorSensor);

        Iterable<SensorActivityLog> emptySensorActivityLogs = new ArrayList<>();
        when(sensorActivityLogRepository.findAllBySensorIdAndTimestampBetween(1L,
                ZonedDateTime.parse(dto.startDate), ZonedDateTime.parse(dto.endDate)))
                .thenReturn(emptySensorActivityLogs);

        Ctrl34GetMaxInstantaneousTempDifOutsideInside ctrl34GetMaxInstantaneousTempDifOutsideInside =
                new Ctrl34GetMaxInstantaneousTempDifOutsideInside(environmentalTemperatureService);
        TimePeriodDTO timePeriodDTO = new TimePeriodDTO(start, end);

        // Act

        IllegalArgumentException message = assertThrows(IllegalArgumentException.class,
                () -> ctrl34GetMaxInstantaneousTempDifOutsideInside.getMaximumTemperatureDifference(1L,
                        timePeriodDTO, Long.MAX_VALUE));
        String expected = "There must be at least 1 indoor log in the given time period";

        // Assert
        assertEquals(expected, message.getMessage());
    }

    @Test
    void getMaximumTemperatureDifference_noActivityLogInIndoorSensors() {
        long indoorRoomId = 1L;
        long outdoorRoomId = 2L;
        long indoorDeviceId = 1L;
        String start = "2024-04-23T00:00:00Z";
        String end = "2024-04-24T00:00:00Z";
        DeviceID indoorDevId = new DeviceID(indoorDeviceId);
        RoomId indoorRoomID = new RoomId(indoorRoomId);
        RoomId outdoorRoomID = new RoomId(outdoorRoomId);
        Room indoorRoom = new RoomFactory().createRoom(new RoomName("r1"), indoorRoomID, new HouseId(1L),
                new Floor("F1"), mock(Dimensions.class), OutdoorIndoor.INDOOR);
        Room outdoorRoom = new RoomFactory().createRoom(new RoomName("r2"), outdoorRoomID, new HouseId(1L),
                new Floor("F1"), mock(Dimensions.class), OutdoorIndoor.OUTDOOR);
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        Device indoorDevice = new DeviceFactory()
                .createDevice(indoorRoomID, indoorDevId, deviceName, deviceModel, new DeviceStatus(true));

        SensorType sensorType = new SensorTypeFactory().createSensorType(new SensorTypeId("Temperature"),
                MeasurementUnit.Celsius);

        roomRepository.save(indoorRoom);
        roomRepository.save(outdoorRoom);
        sensorTypeRepository.save(sensorType);
        deviceRepository.save(indoorDevice);

        Ctrl34GetMaxInstantaneousTempDifOutsideInside ctrl34GetMaxInstantaneousTempDifOutsideInside =
                new Ctrl34GetMaxInstantaneousTempDifOutsideInside(environmentalTemperatureService);
        TimePeriodDTO timePeriodDTO = new TimePeriodDTO(start, end);

        // Act

        IllegalArgumentException message = assertThrows(IllegalArgumentException.class,
                () -> ctrl34GetMaxInstantaneousTempDifOutsideInside.getMaximumTemperatureDifference(1L,
                        timePeriodDTO, Long.MAX_VALUE));
        String expected = "There must be at least 1 indoor temperature sensor";

        // Assert
        assertEquals(expected, message.getMessage());
    }


}