package SmartHome.domain.activitylog.implementation;

import SmartHome.domain.valueObjects.ActivityLogId;
import SmartHome.domain.valueObjects.Measurement;
import SmartHome.domain.valueObjects.SensorId;
import SmartHome.domain.valueObjects.TimeStamp;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SensorActivityLogTest
{
    @Test
    void validConstructor()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        SensorId sensorId = new SensorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");

        // Act
        SensorActivityLog sensorActivityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, value);

        //Assert
        assertNotNull(sensorActivityLog);
    }

    @Test
    void invalidConstructorNullActivityLogId()
    {
        // Arrange
        SensorId sensorId = new SensorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SensorActivityLog(null, timeStamp, sensorId, value));

        // Assert
        assertEquals("ActivityLogId cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorNullSensorId()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SensorActivityLog(activityLogId, timeStamp, null, value));

        // Assert
        assertEquals("SensorId cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorNullTimeStamp() {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        Measurement value = new Measurement("24");

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SensorActivityLog(activityLogId, null, null, value));

        // Assert
        assertEquals("TimeStamp cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorNullValue()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        SensorId sensorId = new SensorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SensorActivityLog(activityLogId, timeStamp, sensorId, null));

        // Assert
        assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    void getLogTime()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        SensorId sensorId = new SensorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        SensorActivityLog activityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, value);

        // Act
        TimeStamp result = activityLog.getLogTime();

        // Assert
        assertNotNull(result);
    }

    @Test
    void getSensorId() {
        //Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        SensorId sensorId = new SensorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        SensorActivityLog activityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, value);

        //Act
        SensorId result = activityLog.getSensorId();

        //Assert
        assertNotNull(result);
    }

    @Test
    void getMeasurement()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        SensorId sensorId = new SensorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        SensorActivityLog activityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, value);

        // Act
        Measurement result = activityLog.getMeasurement();

        // Assert
        assertEquals(value, result);
    }

    @Test
    void getIdentity()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        SensorId sensorId = new SensorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        SensorActivityLog activityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, value);

        // Act
        ActivityLogId result = activityLog.identity();

        // Assert
        assertEquals(activityLogId, result);
    }

    @Test
    void sameAsSameObject()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        SensorId sensorId = new SensorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        SensorActivityLog activityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, value);

        // Act
        boolean result = activityLog.sameAs(activityLog);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAsDifferentObject()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        SensorId sensorId = new SensorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        SensorActivityLog activityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, value);

        TimeStamp timeStamp2 = new TimeStamp(ZonedDateTime.now());
        SensorActivityLog activityLog2 = new SensorActivityLog(activityLogId, timeStamp2, sensorId, value);

        // Act
        boolean result = activityLog.sameAs(activityLog2);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAsNullObject()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        SensorId sensorId = new SensorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        SensorActivityLog activityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, value);

        // Act
        boolean result = activityLog.sameAs(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAsDifferentClass()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        SensorId sensorId = new SensorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        SensorActivityLog activityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, value);

        // Act
        boolean result = activityLog.sameAs(new Object());

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAsDifferentActivityLogId()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        SensorId sensorId = new SensorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        SensorActivityLog activityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, value);

        ActivityLogId activityLogId2 = new ActivityLogId(2L);
        SensorActivityLog activityLog2 = new SensorActivityLog(activityLogId2, timeStamp, sensorId, value);

        // Act
        boolean result = activityLog.sameAs(activityLog2);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAsDifferentSensorId()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        SensorId sensorId = new SensorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        SensorActivityLog activityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, value);

        SensorId sensorId2 = new SensorId(2L);
        SensorActivityLog activityLog2 = new SensorActivityLog(activityLogId, timeStamp, sensorId2, value);

        // Act
        boolean result = activityLog.sameAs(activityLog2);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAsDifferentValue()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        SensorId sensorId = new SensorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        SensorActivityLog activityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, value);

        Measurement value2 = new Measurement("240");
        SensorActivityLog activityLog2 = new SensorActivityLog(activityLogId, timeStamp, sensorId, value2);

        // Act
        boolean result = activityLog.sameAs(activityLog2);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAsWithSameValue()
    {
        //Arrange
        ActivityLogId activityLogId = new ActivityLogId(1);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        SensorId sensorId = new SensorId(2);
        Measurement value = new Measurement("24");
        Measurement value2 = new Measurement("24");

        SensorActivityLog activityLog1 = new SensorActivityLog(activityLogId, timeStamp, sensorId, value);
        SensorActivityLog activityLog2 = new SensorActivityLog(activityLogId, timeStamp, sensorId, value2);

        //Act
        boolean result = activityLog1.sameAs(activityLog2);

        //Assert
        assertTrue(result);
    }
}
