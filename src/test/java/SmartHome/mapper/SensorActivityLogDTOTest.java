package SmartHome.mapper;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SensorActivityLogDTOTest
{

    @Test
    void validSensorActivityLogDTO_FirstConstructor() {
        // Arrange
        long sensorId = 1L;
        ZonedDateTime timeStamp = ZonedDateTime.parse("2024-01-01T00:00:00Z");
        String measurement = "24";
        SensorActivityLogDTO dto = new SensorActivityLogDTO(sensorId, timeStamp.toString(), measurement);

        // Act & Assert
        assertEquals(sensorId, dto.sensorId);
        assertEquals(timeStamp, ZonedDateTime.parse(dto.timeStamp));
        assertEquals(measurement, dto.measurement);
    }

    @Test
    void validSensorActivityLogDTO()
    {
        // Arrange
        long sensorActivityLogId = 1L;
        long sensorId = 1L;
        ZonedDateTime timeStamp = ZonedDateTime.parse("2024-01-01T00:00:00Z");
        String measurement = "24";
        SensorActivityLogDTO dto = new SensorActivityLogDTO(sensorActivityLogId, sensorId, timeStamp.toString(), measurement);

        // Act & Assert
        assertEquals(sensorActivityLogId, dto.sensorActivityLogId);
        assertEquals(sensorId, dto.sensorId);
        assertEquals(timeStamp, ZonedDateTime.parse(dto.timeStamp));
        assertEquals(measurement, dto.measurement);
    }
}
