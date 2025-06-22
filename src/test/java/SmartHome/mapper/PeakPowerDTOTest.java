package SmartHome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PeakPowerDTOTest {
    @Test
    void validPeakPowerDTO() {
        // Arrange
        double expected = 10.0;

        // Act
        PeakPowerDTO peakPowerDTO = new PeakPowerDTO(expected);

        // Assert
        assertEquals(expected, peakPowerDTO.getPeakPower());
    }
}