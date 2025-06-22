package SmartHome.domain.valueObjects.values;

import SmartHome.domain.valueObjects.values.implementation.WhValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhValueTest {

    @Test
    void validConsumptionValue() {
        // Arrange
        double validValue = 100.5;

        // Act
        WhValue result = new WhValue(validValue);

        // Assert
        assertEquals(validValue, result.currentValue);
    }

    @Test
    void invalidConsumptionValueNegative() {
        // Arrange
        double invalidValue = -10.0;
        String expected = "Invalid Wh value.";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new WhValue(invalidValue));
        String exceptionMessage = exception.getMessage();

        // Assert
        assertTrue(exceptionMessage.contains(expected));
    }

    @Test
    void validZeroConsumptionValue() {
        // Arrange
        double edgeValue = 0;

        // Act
        WhValue result = new WhValue(edgeValue);

        // Assert
        assertEquals(edgeValue, result.currentValue);
    }
}
