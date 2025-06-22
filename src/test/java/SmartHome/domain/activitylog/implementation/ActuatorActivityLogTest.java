package SmartHome.domain.activitylog.implementation;

import SmartHome.domain.valueObjects.ActivityLogId;
import SmartHome.domain.valueObjects.ActuatorId;
import SmartHome.domain.valueObjects.Measurement;
import SmartHome.domain.valueObjects.TimeStamp;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ActuatorActivityLogTest
{
    @Test
    void validConstructor()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        ActuatorId actuatorId = new ActuatorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");

        // Act
        new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, value);
    }

    @Test
    void invalidConstructorNullActivityLogId()
    {
        // Arrange
        ActuatorId actuatorId = new ActuatorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new ActuatorActivityLog(null, actuatorId, timeStamp, value));

        // Assert
        assertEquals("Id cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorNullActuatorId()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new ActuatorActivityLog(activityLogId, null, timeStamp, value));

        // Assert
        assertEquals("ActuatorId cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorNullValue()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        ActuatorId actuatorId = new ActuatorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, null));

        // Assert
        assertEquals("Measurement cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorNullTimeStamp()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        ActuatorId actuatorId = new ActuatorId(1L);
        Measurement value = new Measurement("24");

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new ActuatorActivityLog(activityLogId, actuatorId, null, value));

        // Assert
        assertEquals("TimeStamp cannot be null", exception.getMessage());
    }

    @Test
    void getLogTime()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        ActuatorId actuatorId = new ActuatorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        ActuatorActivityLog activityLog = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, value);

        // Act
        TimeStamp result = activityLog.getLogTime();

        // Assert
        assertNotNull(result);
    }

    @Test
    void getMeasurement()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        ActuatorId actuatorId = new ActuatorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        ActuatorActivityLog activityLog = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, value);

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
        ActuatorId actuatorId = new ActuatorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        ActuatorActivityLog activityLog = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, value);

        // Act
        ActivityLogId result = activityLog.identity();

        // Assert
        assertEquals(activityLogId, result);
    }

    @Test
    void getActuatorId()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        ActuatorId actuatorId = new ActuatorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        ActuatorActivityLog activityLog = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, value);

        // Act
        ActuatorId result = activityLog.getActuatorId();

        // Assert
        assertEquals(actuatorId, result);
    }

    @Test
    void sameAsSameObject()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        ActuatorId actuatorId = new ActuatorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        ActuatorActivityLog activityLog = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, value);

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
        ActuatorId actuatorId = new ActuatorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        ActuatorActivityLog activityLog = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, value);

        TimeStamp timeStamp2 = new TimeStamp(ZonedDateTime.now());
        ActuatorActivityLog activityLog2 = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp2, value);

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
        ActuatorId actuatorId = new ActuatorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        ActuatorActivityLog activityLog = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, value);

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
        ActuatorId actuatorId = new ActuatorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        ActuatorActivityLog activityLog = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, value);

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
        ActuatorId actuatorId = new ActuatorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        ActuatorActivityLog activityLog = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, value);

        ActivityLogId activityLogId2 = new ActivityLogId(2L);
        ActuatorActivityLog activityLog2 = new ActuatorActivityLog(activityLogId2, actuatorId, timeStamp, value);

        // Act
        boolean result = activityLog.sameAs(activityLog2);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAsDifferentActuatorId()
    {
        // Arrange
        ActivityLogId activityLogId = new ActivityLogId(1L);
        ActuatorId actuatorId = new ActuatorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        ActuatorActivityLog activityLog = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, value);

        ActuatorId actuatorId2 = new ActuatorId(2L);
        ActuatorActivityLog activityLog2 = new ActuatorActivityLog(activityLogId, actuatorId2, timeStamp, value);

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
        ActuatorId actuatorId = new ActuatorId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.now());
        Measurement value = new Measurement("24");
        ActuatorActivityLog activityLog = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, value);

        Measurement value2 = new Measurement("274");
        ActuatorActivityLog activityLog2 = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, value2);

        // Act
        boolean result = activityLog.sameAs(activityLog2);

        // Assert
        assertFalse(result);
    }

}
