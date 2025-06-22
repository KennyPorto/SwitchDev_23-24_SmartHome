package SmartHome.service;

import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.device.Device;
import SmartHome.domain.repository.*;
import SmartHome.domain.room.Room;
import SmartHome.domain.valueObjects.*;
import SmartHome.persistence.mem.DeviceRepositoryImplMem;
import SmartHome.persistence.mem.RoomRepositoryImplMem;
import SmartHome.persistence.mem.SensorTypeRepositoryImplMem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnvironmentalTemperatureServiceTest
{
    private RoomRepository roomRepository;
    private SensorRepository sensorRepository;
    private SensorTypeRepository sensorTypeRep;
    private SensorActivityLogRepository sensorActivityLogRepository;
    private EnvironmentalTemperatureService environmentalTemperatureService;

    @BeforeEach
    public void setup() {
        roomRepository = new RoomRepositoryImplMem();
        sensorRepository = mock(SensorRepository.class);
        sensorTypeRep = new SensorTypeRepositoryImplMem();
        DeviceRepository deviceRepository = new DeviceRepositoryImplMem();
        sensorActivityLogRepository = mock(SensorActivityLogRepository.class);
        environmentalTemperatureService = new EnvironmentalTemperatureService(roomRepository,sensorRepository, sensorTypeRep, deviceRepository, sensorActivityLogRepository);
    }

    @Test
    public void calculateWithEqualIndoorAndOutdoorTemperatures() {
        // Arrange
        SensorActivityLog indoorLog = new SensorActivityLog(new ActivityLogId(1), new TimeStamp(ZonedDateTime.now()), new SensorId(1), new Measurement("20.0"));
        SensorActivityLog outdoorLog = new SensorActivityLog(new ActivityLogId(1), new TimeStamp(ZonedDateTime.now()), new SensorId(1), new Measurement("20.0"));
        List<SensorActivityLog> indoorSensors = List.of(indoorLog);
        List<SensorActivityLog> outdoorSensors = List.of(outdoorLog);

        // Act
        double result = environmentalTemperatureService.calculateMaximumTemperatureDifference(indoorSensors, outdoorSensors, Long.MAX_VALUE);

        // Assert
        assertEquals(0.0, result, "The temperature difference should be 0.0");
    }

    @Test
    public void calculateWithDifferentIndoorAndOutdoorTemperatures() {
        // Arrange
        SensorActivityLog indoorLog = new SensorActivityLog(new ActivityLogId(1), new TimeStamp(ZonedDateTime.now()), new SensorId(1), new Measurement("22.0"));
        SensorActivityLog outdoorLog = new SensorActivityLog(new ActivityLogId(1), new TimeStamp(ZonedDateTime.now()), new SensorId(1), new Measurement("18.0"));
        List<SensorActivityLog> indoorSensors = List.of(indoorLog);
        List<SensorActivityLog> outdoorSensors = List.of(outdoorLog);

        // Act
        double result = environmentalTemperatureService.calculateMaximumTemperatureDifference(indoorSensors, outdoorSensors, Long.MAX_VALUE);

        // Assert
        assertEquals(4.0, result, "The temperature difference should be 4.0");
    }

    @Test
    public void calculateWithMultipleIndoorAndOutdoorLogs() {
        // Arrange
        ZonedDateTime fixedTime = ZonedDateTime.now();
        SensorActivityLog indoorLog1 = new SensorActivityLog(new ActivityLogId(1), new TimeStamp(fixedTime), new SensorId(1), new Measurement("20.0"));
        SensorActivityLog indoorLog2 = new SensorActivityLog(new ActivityLogId(2), new TimeStamp(fixedTime.plusHours(1)), new SensorId(2), new Measurement("22.0"));
        SensorActivityLog indoorLog3 = new SensorActivityLog(new ActivityLogId(3), new TimeStamp(fixedTime.plusHours(2)), new SensorId(3), new Measurement("23.0"));
        List<SensorActivityLog> indoorSensors = Arrays.asList(indoorLog1, indoorLog2, indoorLog3);

        SensorActivityLog outdoorLog1 = new SensorActivityLog(new ActivityLogId(4), new TimeStamp(fixedTime), new SensorId(4), new Measurement("18.0"));
        SensorActivityLog outdoorLog2 = new SensorActivityLog(new ActivityLogId(5), new TimeStamp(fixedTime.plusHours(1)), new SensorId(5), new Measurement("21.0"));
        SensorActivityLog outdoorLog3 = new SensorActivityLog(new ActivityLogId(6), new TimeStamp(fixedTime.plusHours(2)), new SensorId(6), new Measurement("24.0"));
        List<SensorActivityLog> outdoorSensors = Arrays.asList(outdoorLog1, outdoorLog2, outdoorLog3);

        // Act
        double result = environmentalTemperatureService.calculateMaximumTemperatureDifference(indoorSensors, outdoorSensors, Long.MAX_VALUE);

        // Assert
        assertEquals(2.0, result, "The temperature difference should be 2.0");
    }

    @Test
    public void calculateTheChosenLogsAreNotTheFirstOnesInTheList() {
        // Arrange
        ZonedDateTime fixedTime = ZonedDateTime.now();
        SensorActivityLog indoorLog1 = new SensorActivityLog(new ActivityLogId(1), new TimeStamp(fixedTime.plusHours(5)), new SensorId(1), new Measurement("20.0"));
        SensorActivityLog indoorLog2 = new SensorActivityLog(new ActivityLogId(2), new TimeStamp(fixedTime.plusHours(4)), new SensorId(2), new Measurement("25.0"));
        SensorActivityLog indoorLog3 = new SensorActivityLog(new ActivityLogId(3), new TimeStamp(fixedTime.plusHours(15)), new SensorId(3), new Measurement("23.0"));
        List<SensorActivityLog> indoorSensors = Arrays.asList(indoorLog1, indoorLog2, indoorLog3);

        SensorActivityLog outdoorLog1 = new SensorActivityLog(new ActivityLogId(4), new TimeStamp(fixedTime.plusHours(10)), new SensorId(4), new Measurement("18.0"));
        SensorActivityLog outdoorLog2 = new SensorActivityLog(new ActivityLogId(5), new TimeStamp(fixedTime.plusHours(7)), new SensorId(5), new Measurement("21.0"));
        SensorActivityLog outdoorLog3 = new SensorActivityLog(new ActivityLogId(6), new TimeStamp(fixedTime.plusHours(16)), new SensorId(6), new Measurement("30.0"));
        List<SensorActivityLog> outdoorSensors = Arrays.asList(outdoorLog1, outdoorLog2, outdoorLog3);

        // Act
        double result = environmentalTemperatureService.calculateMaximumTemperatureDifference(indoorSensors, outdoorSensors, Long.MAX_VALUE);

        // Assert
        assertEquals(2.0, result, "The temperature difference should be 2.0");
    }
    @Test
    public void testIsIndoorDeviceReturnsFalseWhenDeviceNotFound() throws Exception {
        // Arrange
        DeviceRepository deviceRepository = Mockito.mock(DeviceRepository.class);
        EnvironmentalTemperatureService environmentalTemperatureService = new EnvironmentalTemperatureService(roomRepository, sensorRepository, sensorTypeRep, deviceRepository, sensorActivityLogRepository);
        when(deviceRepository.findById(new DeviceID(1L))).thenReturn(Optional.empty());

        // Use reflection to access the private method
        Method method = EnvironmentalTemperatureService.class.getDeclaredMethod("isIndoorDevice", long.class);
        method.setAccessible(true);

        // Act
        boolean result = (boolean) method.invoke(environmentalTemperatureService, 1L);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsIndoorDeviceReturnsFalseWhenRoomNotFound() throws Exception {
        // Arrange
        Device device = Mockito.mock(Device.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        DeviceRepository deviceRepository = Mockito.mock(DeviceRepository.class);
        EnvironmentalTemperatureService environmentalTemperatureService = new EnvironmentalTemperatureService(roomRepository, sensorRepository, sensorTypeRep, deviceRepository, sensorActivityLogRepository);

        when(device.getRoomId()).thenReturn(new RoomId(1L));
        when(deviceRepository.findById(new DeviceID(1L))).thenReturn(Optional.of(device));
        when(roomRepository.findById(new RoomId(1L))).thenReturn(Optional.empty());

        Method method = EnvironmentalTemperatureService.class.getDeclaredMethod("isIndoorDevice", long.class);
        method.setAccessible(true);

        // Act
        boolean result = (boolean) method.invoke(environmentalTemperatureService, 1L);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsIndoorDeviceReturnsFalseWhenRoomIsOutdoor() throws Exception {
        // Arrange
        Device device = Mockito.mock(Device.class);
        Room room = Mockito.mock(Room.class);
        DeviceRepository deviceRepository = Mockito.mock(DeviceRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        EnvironmentalTemperatureService environmentalTemperatureService = new EnvironmentalTemperatureService(roomRepository, sensorRepository, sensorTypeRep, deviceRepository, sensorActivityLogRepository);

        when(device.getRoomId()).thenReturn(new RoomId(1L));
        when(deviceRepository.findById(new DeviceID(1L))).thenReturn(Optional.of(device));
        when(roomRepository.findById(new RoomId(1L))).thenReturn(Optional.of(room));
        when(room.getOutdoorIndoor()).thenReturn(OutdoorIndoor.OUTDOOR);

        Method method = EnvironmentalTemperatureService.class.getDeclaredMethod("isIndoorDevice", long.class);
        method.setAccessible(true);

        // Act
        boolean result = (boolean) method.invoke(environmentalTemperatureService, 1L);

        // Assert
        assertFalse(result);
    }
    @Test
    public void calculateWithLogsHavingDifferentTimeDifferences() {
        // Arrange
        ZonedDateTime fixedTime = ZonedDateTime.now();
        SensorActivityLog indoorLog1 = new SensorActivityLog(new ActivityLogId(1), new TimeStamp(fixedTime.plusSeconds(10)), new SensorId(1), new Measurement("20.0"));
        SensorActivityLog indoorLog2 = new SensorActivityLog(new ActivityLogId(2), new TimeStamp(fixedTime.plusSeconds(20)), new SensorId(2), new Measurement("22.0"));
        List<SensorActivityLog> indoorSensors = Arrays.asList(indoorLog1, indoorLog2);

        SensorActivityLog outdoorLog1 = new SensorActivityLog(new ActivityLogId(3), new TimeStamp(fixedTime), new SensorId(3), new Measurement("18.0"));
        SensorActivityLog outdoorLog2 = new SensorActivityLog(new ActivityLogId(4), new TimeStamp(fixedTime.plusSeconds(15)), new SensorId(4), new Measurement("21.0"));
        List<SensorActivityLog> outdoorSensors = Arrays.asList(outdoorLog1, outdoorLog2);

        // Act
        double result = environmentalTemperatureService.calculateMaximumTemperatureDifference(indoorSensors, outdoorSensors, Long.MAX_VALUE);

        // Assert
        assertEquals(2.0, result, "The temperature difference should be 2.0");
    }
    @Test
    public void getDoubleOfLogsHavingDifferentTimeDifferences() {
        // Arrange
        ZonedDateTime fixedTime = ZonedDateTime.now();
        SensorActivityLog indoorLog1 = new SensorActivityLog(new ActivityLogId(1), new TimeStamp(fixedTime.plusSeconds(10)), new SensorId(1), new Measurement("21.0"));
        SensorActivityLog indoorLog2 = new SensorActivityLog(new ActivityLogId(2), new TimeStamp(fixedTime.plusSeconds(30)), new SensorId(2), new Measurement("22.0"));
        List<SensorActivityLog> indoorSensors = Arrays.asList(indoorLog1, indoorLog2);

        SensorActivityLog outdoorLog1 = new SensorActivityLog(new ActivityLogId(3), new TimeStamp(fixedTime), new SensorId(3), new Measurement("20.0"));
        SensorActivityLog outdoorLog2 = new SensorActivityLog(new ActivityLogId(4), new TimeStamp(fixedTime.plusSeconds(20)), new SensorId(4), new Measurement("21.0"));
        List<SensorActivityLog> outdoorSensors = Arrays.asList(outdoorLog1, outdoorLog2);

        // Act
        double result = environmentalTemperatureService.calculateMaximumTemperatureDifference(indoorSensors, outdoorSensors, Long.MAX_VALUE);

        // Assert
        assertEquals(1.0, result, "The temperature difference should be 1.0");
    }
    @Test
    public void getReadingsWithTheLeastTimeDifferenceBetweenThem() {
        // Arrange
        ZonedDateTime fixedTime = ZonedDateTime.now();
        SensorActivityLog indoorLog1 = new SensorActivityLog(new ActivityLogId(1), new TimeStamp(fixedTime.plusSeconds(10)), new SensorId(1), new Measurement("21.0"));
        SensorActivityLog indoorLog2 = new SensorActivityLog(new ActivityLogId(2), new TimeStamp(fixedTime.plusSeconds(30)), new SensorId(2), new Measurement("22.0"));
        List<SensorActivityLog> indoorSensors = Arrays.asList(indoorLog1, indoorLog2);

        SensorActivityLog outdoorLog1 = new SensorActivityLog(new ActivityLogId(3), new TimeStamp(fixedTime.plusSeconds(5)), new SensorId(3), new Measurement("20.0"));
        SensorActivityLog outdoorLog2 = new SensorActivityLog(new ActivityLogId(4), new TimeStamp(fixedTime.plusSeconds(20)), new SensorId(4), new Measurement("21.0"));
        List<SensorActivityLog> outdoorSensors = Arrays.asList(outdoorLog1, outdoorLog2);

        // Act
        int[] result = environmentalTemperatureService.getReadingsWithTheLeastTimeDifferenceBetweenThem(indoorSensors, outdoorSensors, Long.MAX_VALUE);

        // Assert
        assertEquals(0, result[0], "The index of the indoor sensor log with the least time difference should be 0");
        assertEquals(0, result[1], "The index of the outdoor sensor log with the least time difference should be 0");
    }
    @Test
    public void testGetReadingsWithTimeDifferenceNotGreaterThanMax() {
        // Arrange
        ZonedDateTime now = ZonedDateTime.now();
        SensorActivityLog indoorLog = new SensorActivityLog(new ActivityLogId(1), new TimeStamp(now), new SensorId(1), new Measurement("20.0"));
        SensorActivityLog outdoorLog = new SensorActivityLog(new ActivityLogId(2), new TimeStamp(now.plusSeconds(1)), new SensorId(2), new Measurement("22.0"));
        List<SensorActivityLog> indoorSensors = List.of(indoorLog);
        List<SensorActivityLog> outdoorSensors = List.of(outdoorLog);

        // Act
        int[] result = environmentalTemperatureService.getReadingsWithTheLeastTimeDifferenceBetweenThem(indoorSensors, outdoorSensors, 2);

        // Assert
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);
    }

    @Test
    public void testGetReadingsWithOrderOfTimestampsMatters() {
        // Arrange
        ZonedDateTime now = ZonedDateTime.now();
        SensorActivityLog indoorLog = new SensorActivityLog(new ActivityLogId(1), new TimeStamp(now.plusSeconds(1)), new SensorId(1), new Measurement("20.0"));
        SensorActivityLog outdoorLog = new SensorActivityLog(new ActivityLogId(2), new TimeStamp(now), new SensorId(2), new Measurement("22.0"));
        List<SensorActivityLog> indoorSensors = List.of(indoorLog);
        List<SensorActivityLog> outdoorSensors = List.of(outdoorLog);

        // Act
        int[] result = environmentalTemperatureService.getReadingsWithTheLeastTimeDifferenceBetweenThem(indoorSensors, outdoorSensors, 2);

        // Assert
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);
    }
}
