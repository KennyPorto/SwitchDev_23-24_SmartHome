package SmartHome.domain.valueObjects.values;

import SmartHome.domain.valueObjects.values.implementation.Wm2Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Wm2ValueTest {

    @Test
    void constructorTest() {
        // Arrange
        double value = 3;
        // Act
        Wm2Value solarIrradianceSensor = new Wm2Value(value);

        // Assert
        assertNotNull(solarIrradianceSensor);
    }

    @Test
    void invalidWm2ValueNegative() {
        // Arrange
        double invalidValue = -50;
        String expectedMessage = "Invalid wm2 value.";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Wm2Value(invalidValue));
        String exceptionMessage = exception.getMessage();

        // Assert
        assertTrue(exceptionMessage.contains(expectedMessage));
    }

    @Test
    void validWm2ValueZero()
    {
        // Arrange
        double validValue = 0;

        // Act & Assert
        new Wm2Value(validValue);
    }

    @Test
    void validWm2ValueGreaterThanZero()
    {
        // Arrange
        double validValue = 1;

        // Act & Assert
        new Wm2Value(validValue);
    }


}
