package SmartHome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CloseBlindRollerTest
{
    @Test
    public void validCloseBlindRollerDTO() {
        // Arrange
        long expectedActuatorId = 1L;
        String expectedMeasurement = "measurement";

        // Act
        CloseBlindRollerDTO closeBlindRollerDTO = new CloseBlindRollerDTO(expectedActuatorId, expectedMeasurement);

        // Assert
        assertEquals(expectedActuatorId, closeBlindRollerDTO.actuatorId);
        assertEquals(expectedMeasurement, closeBlindRollerDTO.measurement);
    }
}
