package SmartHome.service;

import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.device.Device;
import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.repository.*;
import SmartHome.domain.room.Room;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.sensors.implementation.TemperatureSensor;
import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PeakPowerServiceTest {

    @Test
    void findPeakPower_nonExistingHouseId() {
        //Arrange
        HouseId houseId = mock(HouseId.class);
        ZonedDateTime start = mock(ZonedDateTime.class);
        ZonedDateTime end = mock(ZonedDateTime.class);
        HouseRepository houseRepository = mock(HouseRepository.class);
        when(houseRepository.existsById(houseId)).thenReturn(false);
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        SensorActivityLogRepository sensorActivityLogRepository = mock(SensorActivityLogRepository.class);
        PeakPowerService peakPowerService = new PeakPowerService(houseRepository, roomRepository, deviceRepository,
                sensorRepository, sensorActivityLogRepository);

        //Act and assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> peakPowerService.findPeakPower(houseId, start, end, 0));

        assertEquals("House id doesn't exists", e.getMessage());
    }

    @Test
    void findPeakPower_deltaLessThanZero() {
        //Arrange
        HouseId houseId = mock(HouseId.class);
        ZonedDateTime start = mock(ZonedDateTime.class);
        ZonedDateTime end = mock(ZonedDateTime.class);
        HouseRepository houseRepository = mock(HouseRepository.class);
        when(houseRepository.existsById(houseId)).thenReturn(true);
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        SensorActivityLogRepository sensorActivityLogRepository = mock(SensorActivityLogRepository.class);
        PeakPowerService peakPowerService = new PeakPowerService(houseRepository, roomRepository, deviceRepository, sensorRepository, sensorActivityLogRepository);

        long invalidDelta = -1;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> peakPowerService.findPeakPower(houseId, start, end, invalidDelta));
    }

    @Test
    void findPeakPower_deltaWithValue901()
    {
        //Arrange
        HouseId houseId = mock(HouseId.class);
        ZonedDateTime start = mock(ZonedDateTime.class);
        ZonedDateTime end = mock(ZonedDateTime.class);
        HouseRepository houseRepository = mock(HouseRepository.class);
        when(houseRepository.existsById(houseId)).thenReturn(true);
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        SensorActivityLogRepository sensorActivityLogRepository = mock(SensorActivityLogRepository.class);
        PeakPowerService peakPowerService = new PeakPowerService(houseRepository, roomRepository, deviceRepository, sensorRepository, sensorActivityLogRepository);

        long invalidDelta = 901;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> peakPowerService.findPeakPower(houseId, start, end, invalidDelta));
    }

    @Test
    void findPeakPowerDoesNotThrowExceptionForValidDelta() {
        // Arrange
        HouseId houseId = mock(HouseId.class);
        ZonedDateTime start = mock(ZonedDateTime.class);
        ZonedDateTime end = mock(ZonedDateTime.class);
        HouseRepository houseRepository = mock(HouseRepository.class);
        when(houseRepository.existsById(houseId)).thenReturn(true);
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        SensorActivityLogRepository sensorActivityLogRepository = mock(SensorActivityLogRepository.class);
        PeakPowerService peakPowerService = new PeakPowerService(houseRepository, roomRepository, deviceRepository, sensorRepository, sensorActivityLogRepository);

        long validDelta = 900;

        // Act & Assert
        assertDoesNotThrow(() -> peakPowerService.findPeakPower(houseId, start, end, validDelta));
    }

    @Test
    void findPeakPowerReturnsDouble() {
        // Arrange
        HouseId houseId = mock(HouseId.class);
        ZonedDateTime start = mock(ZonedDateTime.class);
        ZonedDateTime end = mock(ZonedDateTime.class);
        HouseRepository houseRepository = mock(HouseRepository.class);
        when(houseRepository.existsById(houseId)).thenReturn(true);
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        SensorActivityLogRepository sensorActivityLogRepository = mock(SensorActivityLogRepository.class);
        PeakPowerService peakPowerService = new PeakPowerService(houseRepository, roomRepository, deviceRepository, sensorRepository, sensorActivityLogRepository);

        long validDelta = 900;

        // Act
        double result = peakPowerService.findPeakPower(houseId, start, end, validDelta);

        // Assert
        assertTrue(result >= 0);
    }


    @Test
    void findAllBySensorLongId() {
        // Arrange
        HouseId houseId = new HouseId(1);
        ZonedDateTime start = ZonedDateTime.parse("2024-05-21T20:00:00.00Z");
        ZonedDateTime end = ZonedDateTime.parse("2024-05-21T20:15:00.00Z");
        HouseRepository houseRepository = mock(HouseRepository.class);
        when(houseRepository.existsById(houseId)).thenReturn(true);

        RoomName roomName = new RoomName("r1");
        RoomId roomId = new RoomId(1);
        Floor floor = new Floor("1");
        Dimensions dimensions = new Dimensions(1, 1, 1);
        OutdoorIndoor outdoorIndoor = OutdoorIndoor.INDOOR;
        Room room = new RoomFactory().createRoom(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);

        List<Room> rooms = Collections.singletonList(room);
        RoomRepository roomRepository = mock(RoomRepository.class);
        when(roomRepository.findAllByHouseId(houseId.id)).thenReturn(rooms);

        List<Device> devices = new ArrayList<>();
        DeviceID deviceID = new DeviceID(1L);
        DeviceID deviceID2 = new DeviceID(2L);
        DeviceName deviceName = new DeviceName("device1");
        DeviceModel deviceModel = new DeviceModel("deviceModel");
        Device device = new DeviceFactory()
                .createDevice(roomId, deviceID, deviceName, deviceModel, new DeviceStatus(true));
        Device device2 = new DeviceFactory()
                .createDevice(roomId, deviceID2, deviceName, deviceModel, new DeviceStatus(true));
        devices.add(device);
        devices.add(device2);

        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        when(deviceRepository.findAllByRoomId(any())).thenReturn(devices);

        SensorId sensorId = new SensorId(1);
        SensorId sensorId2 = new SensorId(2);
        SensorName sensorName = new SensorName("s1");
        SensorTypeId sensorTypeId = new SensorTypeId("Power Meter");
        SensorTypeId sensorTypeId2 = new SensorTypeId("Instant Power");

        Sensor sensor = new TemperatureSensor(deviceID, sensorName, sensorId, sensorTypeId, new SensorModel("TemperatureSensor"));
        Sensor sensor2 = new TemperatureSensor(deviceID2, sensorName, sensorId2, sensorTypeId2, new SensorModel("TemperatureSensor"));
        SensorRepository sensorRepository = mock(SensorRepository.class);
        when(sensorRepository.findAllByDeviceIdAndSensorTypeId(1L, "Power Meter"))
                .thenReturn(List.of(sensor));
        when(sensorRepository.findAllByDeviceIdAndSensorTypeId(1L, "Instant Power"))
                .thenReturn(List.of(sensor2));

        ActivityLogId activityLogId = new ActivityLogId(1L);
        Measurement measurement = new Measurement("50.0");
        Measurement measurement2 = new Measurement("-25.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.parse("2024-05-21T20:00:00.00Z"));
        SensorActivityLog sensorActivityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, measurement);
        SensorActivityLog sensorActivityLog2 = new SensorActivityLog(activityLogId, timeStamp, sensorId2, measurement2);
        SensorActivityLogRepository sensorActivityLogRepository = mock(SensorActivityLogRepository.class);
        when(sensorActivityLogRepository.findAllBySensorIdAndTimestampBetween(1, start, end)).thenReturn(List.of(sensorActivityLog));
        when(sensorActivityLogRepository.findAllBySensorIdAndTimestampBetween(2, start, end)).thenReturn(List.of(sensorActivityLog2));
        PeakPowerService peakPowerService = new PeakPowerService(houseRepository, roomRepository, deviceRepository, sensorRepository, sensorActivityLogRepository);

        long validDelta = 900;

        // Act
        double result = peakPowerService.findPeakPower(houseId, start, end, validDelta);

        // Assert
        assertEquals(25.0, result);
    }

    /**
     * ALGORITHM TESTS
     **/
    @Test
    void getInstantPowerConsumption_successWithTwoValidLogs() {
        // Arrange
        HouseRepository houseRepository = mock(HouseRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        SensorActivityLogRepository sensorActivityLogRepository = mock(SensorActivityLogRepository.class);

        // 60 seconds
        long delta = 60;

        SensorActivityLog gridSensorM1 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T20:00:00.00Z")),
                new SensorId(1L),
                new Measurement("50.0")
        );

        SensorActivityLog gridSensorM2 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T20:15:00.00Z")),
                new SensorId(1L),
                new Measurement("40.0")
        );

        SensorActivityLog consSensorM1 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T20:01:00.00Z")),
                new SensorId(2L),
                new Measurement("-30.0")
        );

        SensorActivityLog consSensorM2 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T20:16:00.00Z")),
                new SensorId(2L),
                new Measurement("-30.0")
        );

        List<SensorActivityLog> meterLogs = List.of(gridSensorM1, gridSensorM2);
        List<SensorActivityLog> consumptionLogs = List.of(consSensorM1, consSensorM2);

        // Act
        PeakPowerService peakPowerService = new PeakPowerService(houseRepository, roomRepository, deviceRepository,
                sensorRepository, sensorActivityLogRepository);

        // Assert
        assertEquals(20.0,
                peakPowerService.getInstantPowerConsumption(meterLogs, consumptionLogs, delta),
                0.01);
    }

    @Test
    void getInstantPowerConsumption_successWithExistingByNoneValidDeltaLogs() {
        // Arrange
        HouseRepository houseRepository = mock(HouseRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        SensorActivityLogRepository sensorActivityLogRepository = mock(SensorActivityLogRepository.class);

        // 60 seconds
        long delta = 60;

        SensorActivityLog gridSensorM1 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T20:00:00.00Z")),
                new SensorId(1L),
                new Measurement("50.0")
        );

        SensorActivityLog gridSensorM2 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T20:15:00.00Z")),
                new SensorId(1L),
                new Measurement("40.0")
        );

        SensorActivityLog consSensorM1 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T20:01:01.00Z")),
                new SensorId(2L),
                new Measurement("-30.0")
        );

        SensorActivityLog consSensorM2 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T20:16:01.00Z")),
                new SensorId(2L),
                new Measurement("-30.0")
        );

        List<SensorActivityLog> meterLogs = List.of(gridSensorM1, gridSensorM2);
        List<SensorActivityLog> consumptionLogs = List.of(consSensorM1, consSensorM2);

        // Act
        PeakPowerService peakPowerService = new PeakPowerService(houseRepository, roomRepository, deviceRepository,
                sensorRepository, sensorActivityLogRepository);

        // Assert
        assertEquals(0.0,
                peakPowerService.getInstantPowerConsumption(meterLogs, consumptionLogs, delta),
                0.01);
    }

    @Test
    void getInstantPowerConsumption_successWithExistingByOneValidDeltaLogMeterFirstEntryWithConsLastEntry() {
        // Arrange
        HouseRepository houseRepository = mock(HouseRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        SensorActivityLogRepository sensorActivityLogRepository = mock(SensorActivityLogRepository.class);

        // 60 seconds
        long delta = 60;

        SensorActivityLog gridSensorM1 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T20:00:00.00Z")),
                new SensorId(1L),
                new Measurement("50.0")
        );

        SensorActivityLog consSensorM2 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T19:59:01.00Z")),
                new SensorId(2L),
                new Measurement("-40.0")
        );

        SensorActivityLog gridSensorM2 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T20:15:00.00Z")),
                new SensorId(1L),
                new Measurement("100.0")
        );

        SensorActivityLog consSensorM1 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T19:44:01.00Z")),
                new SensorId(2L),
                new Measurement("-30.0")
        );


        List<SensorActivityLog> meterLogs = List.of(gridSensorM1, gridSensorM2);
        List<SensorActivityLog> consumptionLogs = List.of(consSensorM1, consSensorM2);

        // Act
        PeakPowerService peakPowerService = new PeakPowerService(houseRepository, roomRepository, deviceRepository,
                sensorRepository, sensorActivityLogRepository);

        // Assert
        assertEquals(10.0,
                peakPowerService.getInstantPowerConsumption(meterLogs, consumptionLogs, delta),
                0.01);
    }

    @Test
    void getInstantPowerConsumption_successPowerProductionMeansPowerConsZero() {
        // Arrange
        HouseRepository houseRepository = mock(HouseRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        SensorActivityLogRepository sensorActivityLogRepository = mock(SensorActivityLogRepository.class);

        // 60 seconds
        long delta = 60;

        SensorActivityLog gridSensorM1 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T20:00:00.00Z")),
                new SensorId(1L),
                new Measurement("-50.0")
        );

        SensorActivityLog gridSensorM2 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T20:15:00.00Z")),
                new SensorId(1L),
                new Measurement("-40.0")
        );

        SensorActivityLog consSensorM1 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T20:00:59.00Z")),
                new SensorId(2L),
                new Measurement("30.0")
        );

        SensorActivityLog consSensorM2 = new SensorActivityLog(new ActivityLogId(1L),
                new TimeStamp(ZonedDateTime.parse("2024-05-21T20:14:58.00Z")),
                new SensorId(2L),
                new Measurement("30.0")
        );

        List<SensorActivityLog> meterLogs = List.of(gridSensorM1, gridSensorM2);
        List<SensorActivityLog> consumptionLogs = List.of(consSensorM1, consSensorM2);

        // Act
        PeakPowerService peakPowerService = new PeakPowerService(houseRepository, roomRepository, deviceRepository,
                sensorRepository, sensorActivityLogRepository);

        // Assert
        assertEquals(0.0,
                peakPowerService.getInstantPowerConsumption(meterLogs, consumptionLogs, delta),
                0.01);
    }

}