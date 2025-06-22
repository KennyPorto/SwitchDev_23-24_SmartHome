package SmartHome.service;

import SmartHome.domain.activitylog.ActivityLogFactory;
import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.actuators.ActuatorFactory;
import SmartHome.domain.actuators.implementation.BlindRollerActuator;
import SmartHome.domain.repository.ActuatorActivityLogRepository;
import SmartHome.domain.repository.ActuatorRepository;
import SmartHome.domain.repository.DeviceRepository;
import SmartHome.domain.repository.SensorRepository;
import SmartHome.domain.sensors.implementation.ScaleSensor;
import SmartHome.domain.valueObjects.*;
import SmartHome.domain.valueObjects.values.implementation.PercentageValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static SmartHome.utils.Constants.BLIND_ROLLER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActuatorActServiceTest
{
    private ActuatorActivityLogRepository mockActuatorActivityLogRepository;
    private DeviceRepository mockDeviceRepository;
    private ActivityLogFactory mockActivityLogFactory;
    private ActuatorRepository mockActuatorRepository;
    private SensorRepository mockSensorRepository;

    @BeforeEach
    void setup()
    {
        mockActuatorActivityLogRepository = mock(ActuatorActivityLogRepository.class);
        mockDeviceRepository = mock(DeviceRepository.class);
        mockActivityLogFactory = mock(ActivityLogFactory.class);
        mockActuatorRepository = mock(ActuatorRepository.class);
        mockSensorRepository = mock(SensorRepository.class);
    }

    @Test
    void closeValidPercentageOfABlindRoller()
    {
        // Arrange
        DeviceID deviceId = mock(DeviceID.class);
        Measurement measurement = mock(Measurement.class);
        Measurement mostRecentLog = mock(Measurement.class);
        TimeStamp timeStamp = mock(TimeStamp.class);
        ActuatorModel model = mock(ActuatorModel.class);
        Actuator actuator = mock(BlindRollerActuator.class);
        ActuatorId actuatorId = mock(ActuatorId.class);
        ActuatorActivityLog blindRollerActuatorMostRecentLog = mock(ActuatorActivityLog.class);
        ActuatorActivityLog blindRollerActuatorActivityLog = mock(ActuatorActivityLog.class);
        ScaleSensor scaleSensor = mock(ScaleSensor.class);
        SensorModel sensorModel = mock(SensorModel.class);

        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockActuatorRepository.findById(actuatorId)).thenReturn(Optional.of(actuator));
        when(mockActuatorRepository.save(any())).thenReturn(actuator);

        when(actuator.getActuatorModel()).thenReturn(model);
        when(model.getModel()).thenReturn(BLIND_ROLLER);
        when(actuator.getDeviceID()).thenReturn(deviceId);
        when(actuator.identity()).thenReturn(actuatorId);
        when(actuatorId.getId()).thenReturn(1L);
        when(mockActuatorActivityLogRepository.findTopByActuatorIdOrderByTimeStampDesc(1L)).thenReturn(blindRollerActuatorMostRecentLog);
        when(mockSensorRepository.findAllByDeviceId(anyLong())).thenReturn(List.of(scaleSensor));
        when(scaleSensor.getSensorModel()).thenReturn(sensorModel);
        when(sensorModel.getModel()).thenReturn("ScaleSensor");

        when(measurement.getMeasurement()).thenReturn("45");
        when(blindRollerActuatorMostRecentLog.getMeasurement()).thenReturn(mostRecentLog);
        when(mostRecentLog.getMeasurement()).thenReturn("80");
        when(mockActivityLogFactory.createActuatorActivityLog(new ActivityLogId(1), actuatorId,
              timeStamp, measurement)).thenReturn(Optional.of(blindRollerActuatorActivityLog));
        when(mockActuatorActivityLogRepository.save(blindRollerActuatorActivityLog))
              .thenReturn(blindRollerActuatorActivityLog);

        ActuatorActService actuatorActService = new ActuatorActService(mockActuatorActivityLogRepository,
              mockActivityLogFactory, mockActuatorRepository, mockDeviceRepository, mockSensorRepository);

        // Act
        ActuatorActivityLog actual = actuatorActService.closeBlindRoller(deviceId, measurement, timeStamp, actuatorId);

        // Assert
        assertEquals(blindRollerActuatorActivityLog, actual);
    }

    @Test
    void fullyCloseABlindRoller()
    {
        // Arrange
        DeviceID deviceId = mock(DeviceID.class);
        Measurement measurement = mock(Measurement.class);
        TimeStamp timeStamp = mock(TimeStamp.class);
        ActuatorModel model = mock(ActuatorModel.class);
        BlindRollerActuator actuator = mock(BlindRollerActuator.class);
        PercentageValue percentageValue = mock(PercentageValue.class);
        ActuatorId actuatorId = mock(ActuatorId.class);
        ActuatorActivityLog blindRollerActuatorActivityLog = mock(ActuatorActivityLog.class);
        ScaleSensor scaleSensor = mock(ScaleSensor.class);
        SensorModel sensorModel = mock(SensorModel.class);

        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockActuatorRepository.findById(actuatorId)).thenReturn(Optional.of(actuator));
        when(mockActuatorRepository.save(any())).thenReturn(actuator);

        when(actuator.getActuatorModel()).thenReturn(model);
        when(model.getModel()).thenReturn(BLIND_ROLLER);
        when(actuator.getDeviceID()).thenReturn(deviceId);
        when(actuator.identity()).thenReturn(actuatorId);
        when(actuatorId.getId()).thenReturn(1L);

        when(mockSensorRepository.save(scaleSensor)).thenReturn(scaleSensor);
        when(mockSensorRepository.findAllByDeviceId(anyLong())).thenReturn(List.of(scaleSensor));
        when(scaleSensor.getSensorModel()).thenReturn(sensorModel);
        when(sensorModel.getModel()).thenReturn("ScaleSensor");

        when(actuator.getCurrentPercentage()).thenReturn(percentageValue);
        when(percentageValue.toString()).thenReturn("100");
        when(measurement.getMeasurement()).thenReturn("0");
        when(mockActivityLogFactory.createActuatorActivityLog(new ActivityLogId(1), actuatorId, timeStamp, measurement))
              .thenReturn(Optional.of(blindRollerActuatorActivityLog));
        when(mockActuatorActivityLogRepository.save(blindRollerActuatorActivityLog)).thenReturn(blindRollerActuatorActivityLog);

        ActuatorActService actuatorActService = new ActuatorActService(mockActuatorActivityLogRepository,
              mockActivityLogFactory, mockActuatorRepository, mockDeviceRepository, mockSensorRepository);

        // Act
        ActuatorActivityLog actual = actuatorActService.closeBlindRoller(deviceId, measurement, timeStamp, actuatorId);

        // Assert
        assertEquals(blindRollerActuatorActivityLog, actual);
        assertEquals(actuator.getCurrentPercentage().value, 0);
    }

    @Test
    void closeBlindRollerWithNoLogs()
    {
        // Arrange
        DeviceID deviceId = mock(DeviceID.class);
        Measurement measurement = mock(Measurement.class);
        TimeStamp timeStamp = mock(TimeStamp.class);
        ActuatorModel model = mock(ActuatorModel.class);
        Actuator actuator = mock(BlindRollerActuator.class);
        ActuatorId actuatorId = mock(ActuatorId.class);
        ActuatorActivityLog blindRollerActuatorActivityLog = mock(ActuatorActivityLog.class);
        ScaleSensor scaleSensor = mock(ScaleSensor.class);
        SensorModel sensorModel = mock(SensorModel.class);

        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockActuatorRepository.findById(actuatorId)).thenReturn(Optional.of(actuator));
        when(mockActuatorRepository.save(any())).thenReturn(actuator);

        when(actuator.getActuatorModel()).thenReturn(model);
        when(model.getModel()).thenReturn(BLIND_ROLLER);
        when(actuator.getDeviceID()).thenReturn(deviceId);
        when(actuator.identity()).thenReturn(actuatorId);
        when(actuatorId.getId()).thenReturn(1L);
        when(mockActuatorActivityLogRepository.findTopByActuatorIdOrderByTimeStampDesc(1L)).thenReturn(null);
        when(mockSensorRepository.findAllByDeviceId(anyLong())).thenReturn(List.of(scaleSensor));
        when(scaleSensor.getSensorModel()).thenReturn(sensorModel);
        when(sensorModel.getModel()).thenReturn("ScaleSensor");

        when(measurement.getMeasurement()).thenReturn("45");
        when(mockActivityLogFactory.createActuatorActivityLog(new ActivityLogId(1), actuatorId, timeStamp, measurement))
              .thenReturn(Optional.of(blindRollerActuatorActivityLog));
        when(mockActuatorActivityLogRepository.save(blindRollerActuatorActivityLog)).thenReturn(blindRollerActuatorActivityLog);

        ActuatorActService actuatorActService = new ActuatorActService(mockActuatorActivityLogRepository,
              mockActivityLogFactory, mockActuatorRepository, mockDeviceRepository,mockSensorRepository);

        // Act
        ActuatorActivityLog actual = actuatorActService.closeBlindRoller(deviceId, measurement, timeStamp, actuatorId);

        // Assert
        assertEquals(blindRollerActuatorActivityLog, actual);
    }

    @Test
    void invalidMeasurementIsNotNumeric()
    {
        // Arrange
        DeviceID deviceId = mock(DeviceID.class);
        Measurement measurement = mock(Measurement.class);
        TimeStamp timeStamp = mock(TimeStamp.class);
        ActuatorModel model = mock(ActuatorModel.class);
        Actuator actuator = mock(BlindRollerActuator.class);
        ActuatorId actuatorId = mock(ActuatorId.class);
        ActuatorActivityLog blindRollerActuatorActivityLog = mock(ActuatorActivityLog.class);
        ScaleSensor scaleSensor = mock(ScaleSensor.class);
        SensorModel sensorModel = mock(SensorModel.class);

        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockActuatorRepository.findById(actuatorId)).thenReturn(Optional.of(actuator));

        when(actuator.getActuatorModel()).thenReturn(model);
        when(model.getModel()).thenReturn(BLIND_ROLLER);
        when(actuator.getDeviceID()).thenReturn(deviceId);
        when(actuator.identity()).thenReturn(actuatorId);
        when(actuatorId.getId()).thenReturn(1L);
        when(mockActuatorActivityLogRepository.findTopByActuatorIdOrderByTimeStampDesc(1L)).thenReturn(null);
        when(mockSensorRepository.findAllByDeviceId(anyLong())).thenReturn(List.of(scaleSensor));
        when(scaleSensor.getSensorModel()).thenReturn(sensorModel);
        when(sensorModel.getModel()).thenReturn("ScaleSensor");

        when(measurement.getMeasurement()).thenReturn("Measurement");
        when(mockActivityLogFactory.createActuatorActivityLog(new ActivityLogId(1), actuatorId, timeStamp, measurement))
              .thenReturn(Optional.of(blindRollerActuatorActivityLog));
        when(mockActuatorActivityLogRepository.save(blindRollerActuatorActivityLog)).thenReturn(blindRollerActuatorActivityLog);

        ActuatorActService actuatorActService = new ActuatorActService(mockActuatorActivityLogRepository,
              mockActivityLogFactory, mockActuatorRepository, mockDeviceRepository,mockSensorRepository);

        // Act
        Exception exception = assertThrows(NumberFormatException.class,
              () -> actuatorActService.closeBlindRoller(deviceId, measurement, timeStamp, actuatorId)
        );
        String expectedMessage = "Measurement should be numeric";

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void logNotSavedWithNullTimestampTest()
    {
        // Arrange
        DeviceID deviceId = mock(DeviceID.class);
        Measurement measurement = mock(Measurement.class);
        Measurement blindRollerActuatorMostRecentLogMeasurement = mock(Measurement.class);
        ActuatorModel model = mock(ActuatorModel.class);
        Actuator actuator = mock(BlindRollerActuator.class);
        ActuatorId actuatorId = mock(ActuatorId.class);
        ActuatorActivityLog blindRollerActuatorMostRecentLog = mock(ActuatorActivityLog.class);
        ScaleSensor scaleSensor = mock(ScaleSensor.class);
        SensorModel sensorModel = mock(SensorModel.class);

        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockActuatorRepository.findById(actuatorId)).thenReturn(Optional.of(actuator));
        when(mockActuatorRepository.save(any())).thenReturn(actuator);

        when(actuator.getDeviceID()).thenReturn(deviceId);
        when(actuator.identity()).thenReturn(actuatorId);
        when(actuatorId.getId()).thenReturn(1L);
        when(mockActuatorActivityLogRepository.findTopByActuatorIdOrderByTimeStampDesc(1L)).thenReturn(blindRollerActuatorMostRecentLog);
        when(mockSensorRepository.findAllByDeviceId(anyLong())).thenReturn(List.of(scaleSensor));
        when(scaleSensor.getSensorModel()).thenReturn(sensorModel);
        when(sensorModel.getModel()).thenReturn("ScaleSensor");

        when(actuator.getActuatorModel()).thenReturn(model);
        when(model.getModel()).thenReturn(BLIND_ROLLER);
        when(measurement.getMeasurement()).thenReturn("45");
        when(blindRollerActuatorMostRecentLog.getMeasurement()).thenReturn(blindRollerActuatorMostRecentLogMeasurement);
        when(blindRollerActuatorMostRecentLogMeasurement.getMeasurement()).thenReturn("80");
        when(mockActivityLogFactory.createActuatorActivityLog(new ActivityLogId(1), actuatorId, null, measurement)).thenReturn(Optional.empty());

        ActuatorActService actuatorActService = new ActuatorActService(mockActuatorActivityLogRepository,
                mockActivityLogFactory, mockActuatorRepository, mockDeviceRepository,mockSensorRepository);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> actuatorActService.closeBlindRoller(deviceId, measurement, null, actuatorId)
        );
        String expectedMessage = "Log couldn't be saved";
        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }


    @Test
    void actuatorDoesntBelongToTheDeviceTest()
    {
        // Arrange
        DeviceID deviceId = mock(DeviceID.class);
        DeviceID anotherDeviceId = mock(DeviceID.class);
        Measurement measurement = mock(Measurement.class);
        TimeStamp timeStamp = mock(TimeStamp.class);
        ActuatorModel model = mock(ActuatorModel.class);
        Actuator actuator = mock(BlindRollerActuator.class);
        ActuatorId actuatorId = mock(ActuatorId.class);
        ScaleSensor scaleSensor = mock(ScaleSensor.class);
        SensorModel sensorModel = mock(SensorModel.class);

        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockActuatorRepository.findById(actuatorId)).thenReturn(Optional.of(actuator));
        when(mockSensorRepository.findAllByDeviceId(anyLong())).thenReturn(List.of(scaleSensor));
        when(scaleSensor.getSensorModel()).thenReturn(sensorModel);
        when(sensorModel.getModel()).thenReturn("ScaleSensor");

        when(actuator.getActuatorModel()).thenReturn(model);
        when(model.getModel()).thenReturn(BLIND_ROLLER);
        when(actuator.getDeviceID()).thenReturn(anotherDeviceId);
        when(deviceId.getId()).thenReturn(2L);
        when(actuator.getDeviceID().getId()).thenReturn(1L);

        ActuatorActService actuatorActService = new ActuatorActService(mockActuatorActivityLogRepository,
                mockActivityLogFactory, mockActuatorRepository, mockDeviceRepository, mockSensorRepository);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> actuatorActService.closeBlindRoller(deviceId, measurement, timeStamp, actuatorId)
        );
        String expectedMessage = "This blindRoller doesn't belong to this device";

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void actuatorIsNotABlindRollerTest()
    {
        // Arrange
        DeviceID deviceId = mock(DeviceID.class);
        Measurement measurement = mock(Measurement.class);
        TimeStamp timeStamp = mock(TimeStamp.class);
        ActuatorModel model = mock(ActuatorModel.class);
        Actuator actuator = mock(Actuator.class);
        ActuatorId actuatorId = mock(ActuatorId.class);
        ScaleSensor scaleSensor = mock(ScaleSensor.class);
        SensorModel sensorModel = mock(SensorModel.class);

        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockActuatorRepository.findById(actuatorId)).thenReturn(Optional.of(actuator));

        when(actuator.getActuatorModel()).thenReturn(model);
        when(model.getModel()).thenReturn("SwitchActuator");
        when(mockSensorRepository.findAllByDeviceId(anyLong())).thenReturn(List.of(scaleSensor));
        when(scaleSensor.getSensorModel()).thenReturn(sensorModel);
        when(sensorModel.getModel()).thenReturn("ScaleSensor");

        ActuatorActService actuatorActService = new ActuatorActService(mockActuatorActivityLogRepository,
              mockActivityLogFactory, mockActuatorRepository, mockDeviceRepository, mockSensorRepository);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> actuatorActService.closeBlindRoller(deviceId, measurement, timeStamp, actuatorId)
        );
        String expectedMessage = "The actuator is not a blind roller";

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void actuatorWithInvalidActuatorId()
    {
        // Arrange
        DeviceID deviceId = mock(DeviceID.class);
        Measurement measurement = mock(Measurement.class);
        TimeStamp timeStamp = mock(TimeStamp.class);
        ActuatorId actuatorId = mock(ActuatorId.class);
        ScaleSensor scaleSensor = mock(ScaleSensor.class);
        SensorModel sensorModel = mock(SensorModel.class);

        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockActuatorRepository.findById(actuatorId)).thenReturn(Optional.empty());
        when(mockSensorRepository.findAllByDeviceId(anyLong())).thenReturn(List.of(scaleSensor));
        when(scaleSensor.getSensorModel()).thenReturn(sensorModel);
        when(sensorModel.getModel()).thenReturn("ScaleSensor");

        ActuatorActService actuatorActService = new ActuatorActService(mockActuatorActivityLogRepository,
                mockActivityLogFactory, mockActuatorRepository, mockDeviceRepository, mockSensorRepository);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> actuatorActService.closeBlindRoller(deviceId, measurement, timeStamp, actuatorId)
        );
        String expectedMessage = "An actuator with this actuator Id doesn't exist";

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void deviceWithNoActuators()
    {
        // Arrange
        DeviceID deviceId = mock(DeviceID.class);
        Measurement measurement = mock(Measurement.class);
        TimeStamp timeStamp = mock(TimeStamp.class);
        ActuatorId actuatorId = mock(ActuatorId.class);
        ScaleSensor scaleSensor = mock(ScaleSensor.class);
        SensorModel sensorModel = mock(SensorModel.class);

        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockActuatorRepository.findById(actuatorId)).thenReturn(Optional.empty());
        when(mockSensorRepository.findAllByDeviceId(anyLong())).thenReturn(List.of(scaleSensor));
        when(scaleSensor.getSensorModel()).thenReturn(sensorModel);
        when(sensorModel.getModel()).thenReturn("ScaleSensor");

        ActuatorActService actuatorActService = new ActuatorActService(mockActuatorActivityLogRepository,
              mockActivityLogFactory, mockActuatorRepository, mockDeviceRepository, mockSensorRepository);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> actuatorActService.closeBlindRoller(deviceId, measurement, timeStamp, actuatorId)
        );
        String expectedMessage = "An actuator with this actuator Id doesn't exist";

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void deviceIdBelongsToInexistentDevice()
    {
        // Arrange
        DeviceID deviceId = mock(DeviceID.class);
        Measurement measurement = mock(Measurement.class);
        TimeStamp timeStamp = mock(TimeStamp.class);
        ActuatorId actuatorId = mock(ActuatorId.class);
        Actuator actuator = mock(BlindRollerActuator.class);

        when(mockActuatorRepository.findById(actuatorId)).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.existsById(deviceId)).thenReturn(false);

        ActuatorActService actuatorActService = new ActuatorActService(mockActuatorActivityLogRepository,
                mockActivityLogFactory, mockActuatorRepository, mockDeviceRepository, mockSensorRepository);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> actuatorActService.closeBlindRoller(deviceId, measurement, timeStamp, actuatorId)
        );
        String expectedMessage = "A device with this device Id doesn't exist";

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void deviceWithoutScaleSensor() {
        // Arrange
        DeviceID deviceId = mock(DeviceID.class);
        Measurement measurement = mock(Measurement.class);
        TimeStamp timeStamp = mock(TimeStamp.class);
        ActuatorId actuatorId = mock(ActuatorId.class);
        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockSensorRepository.findAllByDeviceId(1L)).thenReturn(List.of());
        ActuatorActService actuatorActService = new ActuatorActService(mockActuatorActivityLogRepository,
                mockActivityLogFactory, mockActuatorRepository, mockDeviceRepository, mockSensorRepository);

        // Act & Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> actuatorActService.closeBlindRoller(deviceId, measurement, timeStamp, actuatorId));

        assertEquals("Device doesn't have a scale sensor", e.getMessage());
    }

    @Test
    void closeValidPercentageOfABlindRoller_equalAmount()
    {
        // Arrange
        DeviceID deviceId = mock(DeviceID.class);
        Measurement measurement = mock(Measurement.class);
        Measurement mostRecentLog = mock(Measurement.class);
        TimeStamp timeStamp = mock(TimeStamp.class);
        ActuatorModel model = mock(ActuatorModel.class);
        Actuator actuator = mock(BlindRollerActuator.class);
        ActuatorId actuatorId = mock(ActuatorId.class);
        ActuatorActivityLog blindRollerActuatorMostRecentLog = mock(ActuatorActivityLog.class);
        ActuatorActivityLog blindRollerActuatorActivityLog = mock(ActuatorActivityLog.class);
        ScaleSensor scaleSensor = mock(ScaleSensor.class);
        SensorModel sensorModel = mock(SensorModel.class);

        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockActuatorRepository.findById(actuatorId)).thenReturn(Optional.of(actuator));
        when(mockActuatorRepository.save(any())).thenReturn(actuator);

        when(actuator.getActuatorModel()).thenReturn(model);
        when(model.getModel()).thenReturn(BLIND_ROLLER);
        when(actuator.getDeviceID()).thenReturn(deviceId);
        when(actuator.identity()).thenReturn(actuatorId);
        when(actuatorId.getId()).thenReturn(1L);
        when(mockActuatorActivityLogRepository.findTopByActuatorIdOrderByTimeStampDesc(1L)).thenReturn(blindRollerActuatorMostRecentLog);
        when(mockSensorRepository.findAllByDeviceId(anyLong())).thenReturn(List.of(scaleSensor));
        when(scaleSensor.getSensorModel()).thenReturn(sensorModel);
        when(sensorModel.getModel()).thenReturn("ScaleSensor");

        when(measurement.getMeasurement()).thenReturn("50");
        when(blindRollerActuatorMostRecentLog.getMeasurement()).thenReturn(mostRecentLog);
        when(mostRecentLog.getMeasurement()).thenReturn("50");
        when(mockActivityLogFactory.createActuatorActivityLog(new ActivityLogId(1), actuatorId,
                timeStamp, measurement)).thenReturn(Optional.of(blindRollerActuatorActivityLog));
        when(mockActuatorActivityLogRepository.save(blindRollerActuatorActivityLog))
                .thenReturn(blindRollerActuatorActivityLog);

        ActuatorActService actuatorActService = new ActuatorActService(mockActuatorActivityLogRepository,
                mockActivityLogFactory, mockActuatorRepository, mockDeviceRepository, mockSensorRepository);

        // Act
        ActuatorActivityLog actual = actuatorActService.closeBlindRoller(deviceId, measurement, timeStamp, actuatorId);

        // Assert
        assertEquals(blindRollerActuatorActivityLog, actual);
    }
}