package SmartHome.service;

import SmartHome.domain.activitylog.ActivityLogFactory;
import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.actuators.ActuatorFactory;
import SmartHome.domain.repository.ActuatorActivityLogRepository;
import SmartHome.domain.repository.ActuatorRepository;
import SmartHome.domain.valueObjects.ActivityLogId;
import SmartHome.domain.valueObjects.ActuatorId;
import SmartHome.domain.valueObjects.Measurement;
import SmartHome.domain.valueObjects.TimeStamp;
import SmartHome.mapper.ActuatorActivityLogDTO;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActuatorActivityLogServiceTest
{
    private ActuatorActivityLogService actuatorActivityLogService;
    private ActuatorActivityLogRepository activityLogRepository;
    private ActivityLogFactory activityLogFactory;
    private ActuatorRepository actuatorRepository;
    private ActuatorFactory actuatorFactory;

    @BeforeEach
    void setup()
    {
        activityLogRepository = mock(ActuatorActivityLogRepository.class);
        activityLogFactory = mock(ActivityLogFactory.class);
        actuatorRepository = mock(ActuatorRepository.class);
        actuatorFactory = mock(ActuatorFactory.class);
        actuatorActivityLogService = new ActuatorActivityLogService(activityLogRepository, activityLogFactory,
                actuatorRepository,
                actuatorFactory);

    }

    @Test
    void add_successful()
    {
        // Arrange
        long activityLogId = 1L;
        long actuatorId = 1L;

        ActuatorId actuatorIdObj = new ActuatorId(actuatorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(activityLogIdObj, actuatorIdObj, timeStamp,
                measurementObj);
        when(actuatorRepository.existsById(actuatorIdObj)).thenReturn(true);
        when(activityLogFactory.createActuatorActivityLog(any(), any(), any(), any()))
                .thenReturn(Optional.of(actuatorActivityLog));
        when(activityLogRepository.save(actuatorActivityLog)).thenReturn(actuatorActivityLog);

        // Act
        ActuatorActivityLog result = actuatorActivityLogService.add(actuatorIdObj, timeStamp, activityLogIdObj, measurementObj);

        // Assert
        assertEquals(actuatorIdObj.id, result.getActuatorId().id);
    }

    @Test
    void add_DuplicatedActuatorId()
    {
        // Arrange
        long activityLogId = 1L;
        long actuatorId = 1L;

        ActuatorId actuatorIdObj = new ActuatorId(actuatorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());

        when(actuatorRepository.existsById(actuatorIdObj)).thenReturn(false);

        // Act and Assert
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> actuatorActivityLogService.add(actuatorIdObj, timeStamp, activityLogIdObj, measurementObj));

        assertEquals("ActuatorId doesn't exist", e.getMessage());
    }

    @Test
    void add_ProblemCreatingActivityLog()
    {
        // Arrange
        long activityLogId = 1L;
        long actuatorId = 1L;

        ActuatorId actuatorIdObj = new ActuatorId(actuatorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());

        when(actuatorRepository.existsById(actuatorIdObj)).thenReturn(true);
        when(activityLogFactory.createActuatorActivityLog(any(), any(), any(), any()))
                .thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> actuatorActivityLogService.add(actuatorIdObj, timeStamp, activityLogIdObj, measurementObj));
    }

    @Test
    void findAll()
    {
        // Arrange
        long activityLogId = 1L;
        long actuatorId = 1L;
        String measurement = "1.0";
        ActuatorActivityLogDTO actuatorActivityLogDTO = new ActuatorActivityLogDTO(activityLogId, actuatorId,
                ZonedDateTime.now().toString(), measurement);
        ActuatorId actuatorIdObj = new ActuatorId(actuatorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(activityLogIdObj, actuatorIdObj, timeStamp,
                measurementObj);
        Iterable<ActuatorActivityLog> actuatorActivityLogList = List.of(actuatorActivityLog);

        when(activityLogRepository.findAll()).thenReturn(actuatorActivityLogList);

        // Act
        Iterable<ActuatorActivityLog> result = actuatorActivityLogService.findAll();
        Iterable<ActuatorActivityLogDTO> expected = List.of(actuatorActivityLogDTO);

        // Assert
        assertEquals(expected.iterator().next().actuatorActivityLogId,
                result.iterator().next().identity().id);
    }

    @Test
    void findById()
    {
        // Arrange
        long activityLogId = 1L;
        long actuatorId = 1L;
        String measurement = "1.0";
        ActuatorActivityLogDTO actuatorActivityLogDTO = new ActuatorActivityLogDTO(activityLogId, actuatorId,
                ZonedDateTime.now().toString(), measurement);

        ActuatorId actuatorIdObj = new ActuatorId(actuatorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(activityLogIdObj, actuatorIdObj, timeStamp,
                measurementObj);
        when(activityLogFactory.createActivityLogId(activityLogId)).thenReturn(Optional.of(activityLogIdObj));
        when(activityLogRepository.findById(activityLogIdObj)).thenReturn(Optional.of(actuatorActivityLog));

        // Act
        ActuatorActivityLog result = actuatorActivityLogService.findById(activityLogId);

        // Assert
        assertEquals(actuatorActivityLogDTO.actuatorActivityLogId, result.identity().id);
    }

    @Test
    void findById_emptyActivityLogId()
    {
        // Arrange
        long activityLogId = 1L;
        long actuatorId = 1L;
        String measurement = "1.0";
        ActuatorActivityLogDTO actuatorActivityLogDTO = new ActuatorActivityLogDTO(activityLogId, actuatorId,
                ZonedDateTime.now().toString(), measurement);

        ActuatorId actuatorIdObj = new ActuatorId(actuatorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(activityLogIdObj, actuatorIdObj, timeStamp,
                measurementObj);
        when(activityLogFactory.createActivityLogId(activityLogId)).thenReturn(Optional.empty());
        when(activityLogRepository.findById(activityLogIdObj)).thenReturn(Optional.of(actuatorActivityLog));

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> actuatorActivityLogService.findById(activityLogId));

        assertEquals("Bad id input", e.getMessage());
    }

    @Test
    void findById_actuatorActivityLogNotFound()
    {
        // Arrange
        long activityLogId = 1L;

        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);

        when(activityLogFactory.createActivityLogId(activityLogId)).thenReturn(Optional.of(activityLogIdObj));
        when(activityLogRepository.findById(activityLogIdObj)).thenReturn(Optional.empty());

        // Act and Assert
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> actuatorActivityLogService.findById(activityLogId));

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
        Pair<Boolean, ActivityLogId> result = actuatorActivityLogService.existsById(activityLogId);

        // Assert
        assertTrue(result.getLeft());
    }

    @Test
    void existsById_badActuatorId()
    {
        // Arrange
        long activityLogId = 1L;

        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        when(activityLogFactory.createActivityLogId(activityLogId)).thenReturn(Optional.empty());
        when(activityLogRepository.existsById(activityLogIdObj)).thenReturn(false);

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> actuatorActivityLogService.existsById(activityLogId));

        assertEquals("Bad id input", e.getMessage());
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
        Pair<Boolean, ActivityLogId> result = actuatorActivityLogService.existsById(activityLogId);

        // Assert
        assertFalse(result.getLeft());
    }

    @Test
    void findAllByActuatorId()
    {
        // Arrange
        long activityLogId = 1L;
        long actuatorId = 2;
        String measurement = "1.0";
        Actuator actuator = mock(Actuator.class);
        ActuatorActivityLogDTO actuatorActivityLogDTO = new ActuatorActivityLogDTO(activityLogId, actuatorId,
                ZonedDateTime.now().toString(), measurement);
        ActuatorId actuatorIdObj = new ActuatorId(actuatorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(activityLogIdObj, actuatorIdObj, timeStamp,
                measurementObj);
        Iterable<ActuatorActivityLog> actuatorActivityLogList = List.of(actuatorActivityLog);

        when(activityLogRepository.findAllByActuatorId(actuatorId)).thenReturn(actuatorActivityLogList);
        when(actuatorFactory.createActuatorId(actuatorId)).thenReturn(Optional.of(actuatorIdObj));
        when(actuatorRepository.findById(actuatorIdObj)).thenReturn(Optional.of(actuator));

        // Act
        Iterable<ActuatorActivityLog> result = actuatorActivityLogService.findAllByActuatorId(actuatorId);
        Iterable<ActuatorActivityLogDTO> expected = List.of(actuatorActivityLogDTO);

        // Assert
        assertEquals(expected.iterator().next().actuatorActivityLogId,
                result.iterator().next().identity().id);
    }

    @Test
    void findAllByActuatorId_badActuatorId()
    {
        // Arrange
        long activityLogId = 1L;
        long actuatorId = 2;

        Actuator actuator = mock(Actuator.class);

        ActuatorId actuatorIdObj = new ActuatorId(actuatorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(activityLogIdObj, actuatorIdObj, timeStamp,
                measurementObj);
        Iterable<ActuatorActivityLog> actuatorActivityLogList = List.of(actuatorActivityLog);

        when(activityLogRepository.findAllByActuatorId(actuatorId)).thenReturn(actuatorActivityLogList);
        when(actuatorFactory.createActuatorId(actuatorId)).thenReturn(Optional.empty());
        when(actuatorRepository.findById(actuatorIdObj)).thenReturn(Optional.of(actuator));

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> actuatorActivityLogService.findAllByActuatorId(activityLogId));

        assertEquals("Bad id input", e.getMessage());
    }

    @Test
    void findAllByActuatorId_actuatorNotFound()
    {
        // Arrange
        long activityLogId = 1L;
        long actuatorId = 2;

        ActuatorId actuatorIdObj = new ActuatorId(actuatorId);
        ActivityLogId activityLogIdObj = new ActivityLogId(activityLogId);
        Measurement measurementObj = new Measurement("1.0");
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(activityLogIdObj, actuatorIdObj, timeStamp,
                measurementObj);
        Iterable<ActuatorActivityLog> actuatorActivityLogList = List.of(actuatorActivityLog);

        when(actuatorFactory.createActuatorId(actuatorId)).thenReturn(Optional.of(actuatorIdObj));
        when(actuatorRepository.findById(actuatorIdObj)).thenReturn(Optional.empty());
        when(activityLogRepository.findAllByActuatorId(actuatorId)).thenReturn(actuatorActivityLogList);

        // Act and Assert
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> actuatorActivityLogService.findAllByActuatorId(actuatorId));

        assertEquals("Not found by id.", e.getMessage());
    }

}