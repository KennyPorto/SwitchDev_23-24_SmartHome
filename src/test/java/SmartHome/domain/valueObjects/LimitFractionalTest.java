package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LimitFractionalTest {
    @Test
    public void createLimitFractional_success() {
        // Arrange
        double lowerLimit = -1.0;
        double upperLimit = 1.0;

        // Assert
        assertEquals(lowerLimit, new LimitFractional(lowerLimit, upperLimit).lowerLimit);
    }

    @Test
    public void createLimitFractional_invertedParams() {
        // Arrange
        double lowerLimit = -2.0;
        double upperLimit = -1.0;

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new LimitFractional(upperLimit, lowerLimit));
    }

    @Test
    public void createLimitFractional_invertedParamsPositiveSide() {
        // Arrange
        double lowerLimit = 1.0;
        double upperLimit = 2.0;

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new LimitFractional(upperLimit, lowerLimit));
    }

    @Test
    public void createLimitFractional_sameParams() {
        // Arrange
        double upperLimit = 1.0;

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new LimitFractional(upperLimit, upperLimit));
    }
}