package SmartHome.domain.activitylog;

import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ActivityLogFactoryTest
{
    ActivityLogId activityLogId;
    ActuatorId actuatorId;
    SensorId sensorId;
    TimeStamp timeStamp;
    Measurement value;
    ActivityLogFactory activityLogFactory;

    @BeforeEach
    void setUp()
    {
        activityLogId = new ActivityLogId(1);
        actuatorId = new ActuatorId(1);
        sensorId = new SensorId(2);
        timeStamp = new TimeStamp(ZonedDateTime.now());
        value = new Measurement("33");
        activityLogFactory = new ActivityLogFactory();
    }

    @Test
    void createActuatorActivityLog()
    {
        // Arrange

        // Act
        Optional<ActuatorActivityLog> actuatorActivityLog = activityLogFactory.createActuatorActivityLog(
              activityLogId, actuatorId, timeStamp, value
        );

        // Assert
        assertTrue(actuatorActivityLog.isPresent());
    }

    @Test
    void validActuatorActivityLogShouldReturnExpectedValues()
    {
        // Arrange

        // Act
        Optional<ActuatorActivityLog> actuatorActivityLog = activityLogFactory.createActuatorActivityLog(
              activityLogId, actuatorId, timeStamp, value
        );

        // Assert
        assertTrue(actuatorActivityLog.isPresent());
        assertEquals(activityLogId, actuatorActivityLog.get().identity());
        assertEquals(timeStamp, actuatorActivityLog.get().getLogTime());
        assertEquals(value, actuatorActivityLog.get().getMeasurement());
    }

    @Test
    void createActuatorActivityLogWithNullActivityLogId()
    {
        // Arrange

        // Act
        Optional<ActuatorActivityLog> actuatorActivityLog = activityLogFactory.createActuatorActivityLog(
              null, actuatorId, timeStamp, value
        );

        // Assert
        assertTrue(actuatorActivityLog.isEmpty());
    }

    @Test
    void createActuatorActivityLogWithNullActuatorId()
    {
        // Arrange

        // Act
        Optional<ActuatorActivityLog> actuatorActivityLog = activityLogFactory.createActuatorActivityLog(
              activityLogId, null, timeStamp, value
        );

        // Assert
        assertTrue(actuatorActivityLog.isEmpty());
    }

    @Test
    void createActuatorActivityLogWithNullValue()
    {
        // Arrange

        // Act
        Optional<ActuatorActivityLog> actuatorActivityLog = activityLogFactory.createActuatorActivityLog(
              activityLogId, actuatorId, timeStamp, null
        );

        // Assert
        assertTrue(actuatorActivityLog.isEmpty());
    }

    @Test
    void createSensorActivityLog()
    {
        // Arrange

        // Act
        Optional<SensorActivityLog> sensorActivityLog = activityLogFactory.createSensorActivityLog(
              activityLogId, sensorId, timeStamp, value
        );

        // Assert
        assertTrue(sensorActivityLog.isPresent());
    }

    @Test
    void validSensorActivityLogShouldReturnExpectedValues()
    {
        // Arrange

        // Act
        Optional<SensorActivityLog> sensorActivityLog = activityLogFactory.createSensorActivityLog(
              activityLogId, sensorId, timeStamp, value
        );

        // Assert
        assertTrue(sensorActivityLog.isPresent());
        assertEquals(activityLogId, sensorActivityLog.get().identity());
        assertEquals(timeStamp, sensorActivityLog.get().getLogTime());
        assertEquals(value, sensorActivityLog.get().getMeasurement());
    }

    @Test
    void createSensorActivityLogWithNullActivityLogId()
    {
        // Arrange

        // Act
        Optional<SensorActivityLog> sensorActivityLog = activityLogFactory.createSensorActivityLog(
              null, sensorId, timeStamp, value
        );

        // Assert
        assertTrue(sensorActivityLog.isEmpty());
    }

    @Test
    void createSensorActivityLogWithNullSensorId()
    {
        // Arrange

        // Act
        Optional<SensorActivityLog> sensorActivityLog = activityLogFactory.createSensorActivityLog(
              activityLogId, null, timeStamp, value
        );

        // Assert
        assertTrue(sensorActivityLog.isEmpty());
    }

    @Test
    void createSensorActivityLogWithNullValue()
    {
        // Arrange

        // Act
        Optional<SensorActivityLog> sensorActivityLog = activityLogFactory.createSensorActivityLog(
              activityLogId, sensorId, timeStamp, null
        );

        // Assert
        assertTrue(sensorActivityLog.isEmpty());
    }

    @Test
    void createTimeStamp()
    {
        // Arrange

        // Act
        Optional<TimeStamp> timeStamp = activityLogFactory.createTimeStamp(ZonedDateTime.now());

        // Assert
        assertTrue(timeStamp.isPresent());
    }

    @Test
    void createTimeStamp_Exception()
    {
        // Arrange

        // Act
        Optional<TimeStamp> result = activityLogFactory.createTimeStamp(null);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void createMeasurement()
    {
        // Arrange

        // Act
        Optional<Measurement> measurement = activityLogFactory.createMeasurement("33");

        // Assert
        assertTrue(measurement.isPresent());
    }

    @Test
    void createMeasurementWithNullValue()
    {
        // Arrange

        // Act
        Optional<Measurement> measurement = activityLogFactory.createMeasurement(null);

        // Assert
        assertTrue(measurement.isEmpty());
    }

    @Test
    void createActivityLogIdId()
    {
        // Arrange

        // Act
        Optional<ActivityLogId> activityLogId = activityLogFactory.createActivityLogId(1);

        // Assert
        assertTrue(activityLogId.isPresent());
    }

    @Test
    void createActivityLogIdIdWithInvalidValue()
    {
        // Arrange

        // Act
        Optional<ActivityLogId> activityLogId = activityLogFactory.createActivityLogId(-1);

        // Assert
        assertTrue(activityLogId.isEmpty());
    }
}