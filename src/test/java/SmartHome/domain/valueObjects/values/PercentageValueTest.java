package SmartHome.domain.valueObjects.values;

import SmartHome.domain.valueObjects.values.implementation.PercentageValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PercentageValueTest {

    @Test
    void validPercentageValue() {
        // Arrange
        int validValue = 50;

        // Act
        PercentageValue value = new PercentageValue(validValue);

        // Assert
        assertEquals(validValue, value.value);
    }

    @Test
    void invalidPercentageValueNegative() {
        // Arrange
        int invalidValue = -50;
        String expectedMessage = "Percentage must be between 0 and 100.";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new PercentageValue(invalidValue));
        String exceptionMessage = exception.getMessage();

        // Assert
        assertTrue(exceptionMessage.contains(expectedMessage));
    }

    @Test
    void invalidPercentageValueOver100() {
        // Arrange
        int invalidValue = 150;
        String expectedMessage = "Percentage must be between 0 and 100.";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new PercentageValue(invalidValue));
        String exceptionMessage = exception.getMessage();

        // Assert
        assertTrue(exceptionMessage.contains(expectedMessage));
    }

    @Test
    void validZeroPercentageValue() {
        // Arrange
        int validValue = 0;

        // Act
        PercentageValue value = new PercentageValue(validValue);

        // Assert
        assertEquals(validValue, value.value);
    }

    @Test
    void valid100PercentageValue() {
        // Arrange
        int validValue = 100;

        // Act
        PercentageValue value = new PercentageValue(validValue);

        // Assert
        assertEquals(validValue, value.value);
    }

    @Test
    void sameValueEquals()
    {
        // Arrange
        int value = 50;
        PercentageValue percentageValue1 = new PercentageValue(value);
        PercentageValue percentageValue2 = new PercentageValue(value);

        // Act
        boolean equals = percentageValue1.equals(percentageValue2);

        // Assert
        assertTrue(equals);
    }

    @Test
    void sameValueSameHashCode()
    {
        // Arrange
        int value = 50;
        PercentageValue percentageValue1 = new PercentageValue(value);
        PercentageValue percentageValue2 = new PercentageValue(value);

        // Act
        int hashCode1 = percentageValue1.hashCode();
        int hashCode2 = percentageValue2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }
}
