package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LimitIntTest {
    @Test
    public void createLimitInt_success() {
        // Arrange
        int lowerLimit = -1;
        int upperLimit = 1;

        // Assert
        assertEquals(lowerLimit, new LimitInt(lowerLimit, upperLimit).lowerLimit);
    }

    @Test
    public void createLimitInt_invertedParams() {
        // Arrange
        int lowerLimit = -1;
        int upperLimit = 1;

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new LimitInt(upperLimit, lowerLimit));
    }

    @Test
    public void createLimitInt_sameParams() {
        // Arrange
        int upperLimit = 1;

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new LimitInt(upperLimit, upperLimit));
    }
}