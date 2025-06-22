package SmartHome.mapper;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActuatorActivityLogDTOTest
{
    @Test
    void validDTOActivityLogId()
    {
        // Arrange
        long activityLogId = 1L;
        long actuatorId = 1L;
        ZonedDateTime timeStamp = ZonedDateTime.parse("2024-01-01T00:00:00Z");
        String measurement = "24";
        ActuatorActivityLogDTO dto = new ActuatorActivityLogDTO(activityLogId, actuatorId, timeStamp.toString(),
                measurement);

        // Act & Assert
        assertEquals(activityLogId, dto.actuatorActivityLogId);
    }

    @Test
    void validDTOActuatorId()
    {
        // Arrange
        long activityLogId = 1L;
        long actuatorId = 1L;
        ZonedDateTime timeStamp = ZonedDateTime.parse("2024-01-01T00:00:00Z");
        String measurement = "24";
        ActuatorActivityLogDTO dto = new ActuatorActivityLogDTO(activityLogId, actuatorId, timeStamp.toString(),
                measurement);

        // Act & Assert
        assertEquals(actuatorId, dto.actuatorId);
    }

    @Test
    void validDTOTimeStamp()
    {
        // Arrange
        long activityLogId = 1L;
        long actuatorId = 1L;
        String timeStampS = "2024-01-01T00:00Z";
        ZonedDateTime timeStamp = ZonedDateTime.parse("2024-01-01T00:00:00Z");
        String measurement = "24";
        ActuatorActivityLogDTO dto = new ActuatorActivityLogDTO(activityLogId, actuatorId, timeStamp.toString(),
                measurement);

        // Act & Assert
        assertEquals(timeStampS, dto.timeStamp);
    }

    @Test
    void validDTOMeasurement()
    {
        // Arrange
        long activityLogId = 1L;
        long actuatorId = 1L;
        ZonedDateTime timeStamp = ZonedDateTime.parse("2024-01-01T00:00:00Z");
        String measurement = "24";
        ActuatorActivityLogDTO dto = new ActuatorActivityLogDTO(activityLogId, actuatorId, timeStamp.toString(),
                measurement);

        // Act & Assert
        assertEquals(measurement, dto.measurement);
    }

    @Test
    void validDto2ndConstructor()
    {
        // Arrange
        long activityLogId = 1L;
        long actuatorId = 1L;
        ZonedDateTime timeStamp = ZonedDateTime.parse("2024-01-01T00:00:00Z");
        String measurement = "24";
        ActuatorActivityLogDTO dto = new ActuatorActivityLogDTO(activityLogId, actuatorId, timeStamp.toString(),
                measurement);

        // Act & Assert
        assertEquals(measurement, dto.measurement);
    }
}
