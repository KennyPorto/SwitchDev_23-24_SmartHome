package SmartHome.mapper;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimePeriodDTOTest
{
    @Test
    void validConstructorStart()
    {
        // Arrange
        String start = "2024-04-24T00:00:00Z";
        String end = "2024-04-24T00:05:00Z";
        TimePeriodDTO timePeriodDTO = new TimePeriodDTO(start, end);
        ZonedDateTime expected = ZonedDateTime.parse(start);

        // Act & Assert
        assertEquals(expected, ZonedDateTime.parse(timePeriodDTO.startDate));
    }

    @Test
    void validConstructorEnd()
    {
        // Arrange
        String start = "2024-04-24T00:00:00Z";
        String end = "2024-04-24T00:05:00Z";
        ZonedDateTime endS = ZonedDateTime.parse("2024-04-24T00:05:00Z");
        TimePeriodDTO timePeriodDTO = new TimePeriodDTO(start, end);

        // Act & Assert
        assertEquals(endS, ZonedDateTime.parse(timePeriodDTO.endDate));
    }
}
