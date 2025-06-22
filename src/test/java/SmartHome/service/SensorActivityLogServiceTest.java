package SmartHome.service;

import SmartHome.domain.activitylog.ActivityLogFactory;
import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.repository.SensorActivityLogRepository;
import SmartHome.domain.repository.SensorRepository;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.sensors.SensorFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.SensorActivityLogDTO;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorActivityLogServiceTest {
    private SensorActivityLogService sensorActivityLogService;
    private SensorActivityLogRepository activityLogRepository;
    private ActivityLogFactory activityLogFactory;
    private SensorRepository sensorRepository;
    private SensorFactory sensorFactory;

    @BeforeEach
    void setup()
    {
        activityLogRepository = mock(SensorActivityLogRepository.class);
        activityLogFactory = mock(ActivityLogFactory.class);
        sensorRepository = mock(SensorRepository.class);
        sensorFactory = mock(SensorFactory.class);
        sensorActivityLogService = new SensorActivityLogService(activityLogRepository, activityLogFactory,
                sensorRepository, sensorFactory);
    }

    @Test
    void add_successful()
    {
        // Arrange
        long activityLogId = 1L;
        long sensorId = 1L;
        String measurement = "1.0";
        SensorActivityLogDTO sensorActivityLogDTO = new SensorActivityLogDTO(activityLogId, sensorId,
                ZonedDateTime.now().toString(), measurement);

        SensorId sensorIdObj = new SensorId(sensorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        SensorActivityLog sensorActivityLog = new SensorActivityLog(activityLogIdObj, timeStamp, sensorIdObj,
                measurementObj);
        when(sensorFactory.createSensorId(sensorId)).thenReturn(Optional.of(sensorIdObj));
        when(sensorRepository.existsById(sensorIdObj)).thenReturn(true);
        when(activityLogFactory.createActivityLogId(activityLogId)).thenReturn(Optional.of(activityLogIdObj));
        when(activityLogFactory.createMeasurement(sensorActivityLogDTO.measurement)).thenReturn(Optional.of(measurementObj));
        when(activityLogFactory.createTimeStamp(ZonedDateTime.parse(sensorActivityLogDTO.timeStamp)))
                .thenReturn(Optional.of(timeStamp));
        when(activityLogFactory.createSensorActivityLog(activityLogIdObj, sensorIdObj, timeStamp, measurementObj))
                .thenReturn(Optional.of(sensorActivityLog));
        when(activityLogRepository.save(sensorActivityLog)).thenReturn(sensorActivityLog);

        // Act
        SensorActivityLog result = sensorActivityLogService.add(sensorIdObj, timeStamp, activityLogIdObj, measurementObj);


        // Assert
        assertEquals(sensorIdObj.id, result.getSensorId().id);
    }

    @Test
    void add_sensorIdDontExist()
    {
        // Arrange
        long activityLogId = 1L;
        long sensorId = 1L;
        String measurement = "1.0";
        SensorActivityLogDTO sensorActivityLogDTO = new SensorActivityLogDTO(activityLogId, sensorId,
                ZonedDateTime.now().toString(), measurement);

        SensorId sensorIdObj = new SensorId(sensorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        SensorActivityLog sensorActivityLog = new SensorActivityLog(activityLogIdObj, timeStamp, sensorIdObj,
                measurementObj);
        when(sensorFactory.createSensorId(sensorId)).thenReturn(Optional.of(sensorIdObj));
        when(sensorRepository.existsById(sensorIdObj)).thenReturn(false);
        when(activityLogFactory.createActivityLogId(activityLogId)).thenReturn(Optional.of(activityLogIdObj));
        when(activityLogFactory.createMeasurement(sensorActivityLogDTO.measurement)).thenReturn(Optional.of(measurementObj));
        when(activityLogFactory.createTimeStamp(ZonedDateTime.parse(sensorActivityLogDTO.timeStamp)))
                .thenReturn(Optional.of(timeStamp));
        when(activityLogFactory.createSensorActivityLog(activityLogIdObj, sensorIdObj, timeStamp, measurementObj))
                .thenReturn(Optional.of(sensorActivityLog));
        when(activityLogRepository.save(sensorActivityLog)).thenReturn(sensorActivityLog);

        // Act and Assert
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> sensorActivityLogService.add(sensorIdObj, timeStamp, activityLogIdObj, measurementObj));

        assertEquals("SensorId don't exist", e.getMessage());
    }

    @Test
    void add_badSensorActivityLogCreation()
    {
        // Arrange
        long activityLogId = 1L;
        long sensorId = 1L;
        String measurement = "1.0";
        SensorActivityLogDTO sensorActivityLogDTO = new SensorActivityLogDTO(activityLogId, sensorId,
                ZonedDateTime.now().toString(), measurement);

        SensorId sensorIdObj = new SensorId(sensorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        SensorActivityLog sensorActivityLog = new SensorActivityLog(activityLogIdObj, timeStamp, sensorIdObj,
                measurementObj);
        when(sensorFactory.createSensorId(sensorId)).thenReturn(Optional.of(sensorIdObj));
        when(sensorRepository.existsById(sensorIdObj)).thenReturn(true);
        when(activityLogFactory.createActivityLogId(activityLogId)).thenReturn(Optional.of(activityLogIdObj));
        when(activityLogFactory.createMeasurement(sensorActivityLogDTO.measurement)).thenReturn(Optional.of(measurementObj));
        when(activityLogFactory.createTimeStamp(ZonedDateTime.parse(sensorActivityLogDTO.timeStamp)))
                .thenReturn(Optional.of(timeStamp));
        when(activityLogFactory.createSensorActivityLog(activityLogIdObj, sensorIdObj, timeStamp, measurementObj))
                .thenReturn(Optional.empty());
        when(activityLogRepository.save(sensorActivityLog)).thenReturn(sensorActivityLog);

        // Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> sensorActivityLogService.add(sensorIdObj, timeStamp, activityLogIdObj, measurementObj));
    }

    @Test
    void findAll()
    {
        // Arrange
        long activityLogId = 1L;
        long sensorId = 1L;
        SensorId sensorIdObj = new SensorId(sensorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        SensorActivityLog sensorActivityLog = new SensorActivityLog(activityLogIdObj, timeStamp, sensorIdObj,
                measurementObj);
        Iterable<SensorActivityLog> actuatorActivityLogList = List.of(sensorActivityLog);

        when(activityLogRepository.findAll()).thenReturn(actuatorActivityLogList);

        // Act
        Iterable<SensorActivityLog> result = sensorActivityLogService.findAll();
        Iterable<SensorActivityLog> expected = List.of(sensorActivityLog);

        // Assert
        assertEquals(expected.iterator().next().identity().id,
                result.iterator().next().identity().id);
    }

    @Test
    void findById()
    {
        // Arrange
        long activityLogId = 1L;
        long sensorId = 1L;

        SensorId sensorIdObj = new SensorId(sensorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        SensorActivityLog sensorActivityLog = new SensorActivityLog(activityLogIdObj, timeStamp, sensorIdObj,
                measurementObj);
        when(activityLogFactory.createActivityLogId(activityLogId)).thenReturn(Optional.of(activityLogIdObj));
        when(activityLogRepository.findById(activityLogIdObj)).thenReturn(Optional.of(sensorActivityLog));

        // Act
        SensorActivityLog result = sensorActivityLogService.findById(activityLogId);

        // Assert
        assertEquals(sensorActivityLog.identity().id, result.identity().id);
    }

    @Test
    void findById_badActivityLogId()
    {
        // Arrange
        long activityLogId = 1L;
        long sensorId = 1L;

        SensorId sensorIdObj = new SensorId(sensorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        SensorActivityLog sensorActivityLog = new SensorActivityLog(activityLogIdObj, timeStamp, sensorIdObj,
                measurementObj);
        when(activityLogFactory.createActivityLogId(activityLogId)).thenReturn(Optional.empty());
        when(activityLogRepository.findById(activityLogIdObj)).thenReturn(Optional.of(sensorActivityLog));

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> sensorActivityLogService.findById(activityLogId));

        assertEquals("Bad id input", e.getMessage());
    }

    @Test
    void findById_notFound()
    {
        // Arrange
        long activityLogId = 1L;
        long sensorId = 1L;

        SensorId sensorIdObj = new SensorId(sensorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        SensorActivityLog sensorActivityLog = new SensorActivityLog(activityLogIdObj, timeStamp, sensorIdObj,
                measurementObj);
        when(activityLogFactory.createActivityLogId(activityLogId)).thenReturn(Optional.of(activityLogIdObj));
        when(activityLogRepository.findById(activityLogIdObj)).thenReturn(Optional.empty());

        // Act and Assert
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> sensorActivityLogService.findById(activityLogId));

        assertEquals("Not found by id.", e.getMessage());
    }

    @Test
    void existsById_true()
    {
        // Arrange
        long activityLogId = 1L;

        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        when(activityLogFactory.createActivityLogId(activityLogId)).thenReturn(Optional.of(activityLogIdObj));
        when(activityLogRepository.existsById(activityLogIdObj)).thenReturn(true);

        // Act
        Pair<Boolean, ActivityLogId> result = sensorActivityLogService.existsById(activityLogId);

        // Assert
        assertTrue(result.getLeft());
    }

    @Test
    void existsById_false()
    {
        // Arrange
        long activityLogId = 1L;

        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        when(activityLogFactory.createActivityLogId(activityLogId)).thenReturn(Optional.of(activityLogIdObj));
        when(activityLogRepository.existsById(activityLogIdObj)).thenReturn(false);

        // Act
        Pair<Boolean, ActivityLogId> result = sensorActivityLogService.existsById(activityLogId);

        // Assert
        assertFalse(result.getLeft());
    }

    @Test
    void existsById_badIdInput()
    {
        // Arrange
        long activityLogId = 1L;

        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        when(activityLogFactory.createActivityLogId(activityLogId)).thenReturn(Optional.empty());

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> sensorActivityLogService.existsById(activityLogId));

        assertEquals("Bad id input", e.getMessage());
    }

    @Test
    void findAllBySensorId_badSensorId() {
        when(sensorFactory.createSensorId(-1L)).thenReturn(Optional.empty());

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                        () -> sensorActivityLogService.findAllBySensorId(-1));

        assertEquals("Bad id input", e.getMessage());
    }

    @Test
    void findAllBySensorId_cantFindSensorId() {
        SensorId sensorId = new SensorId(1);
        when(sensorFactory.createSensorId(1L)).thenReturn(Optional.of(sensorId));
        when(sensorRepository.findById(sensorId)).thenReturn(Optional.empty());

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> sensorActivityLogService.findAllBySensorId(1));

        assertEquals("Not found by id.", e.getMessage());
    }

    @Test
    void findAllBySensorId_success() {
        Sensor sensor = new SensorFactory().createSensor(new DeviceID(1), new SensorModel("TemperatureSensor"),
                new SensorName("s1"), new SensorId(1), new SensorTypeId("st1"));
        SensorActivityLog sensorActivityLog = new SensorActivityLog(new ActivityLogId(1),
                new TimeStamp(ZonedDateTime.now()), new SensorId(1), new Measurement("1.0"));

        SensorId sensorId = new SensorId(1);
        when(sensorFactory.createSensorId(1L)).thenReturn(Optional.of(sensorId));
        when(sensorRepository.findById(sensorId)).thenReturn(Optional.of(sensor));
        when(activityLogRepository.findAllBySensorId(1L)).thenReturn(List.of(sensorActivityLog));

        // Act
        Iterable<SensorActivityLog> expected = List.of(sensorActivityLog);
        Iterable<SensorActivityLog> result = sensorActivityLogService.findAllBySensorId(1);

        assertEquals(expected.iterator().next().getSensorId(), result.iterator().next().getSensorId());
    }
}