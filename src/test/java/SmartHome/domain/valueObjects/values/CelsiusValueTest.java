package SmartHome.domain.valueObjects.values;

import SmartHome.domain.valueObjects.values.implementation.CelsiusValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CelsiusValueTest {
    @Test
    void constructorTest() {
        // Arrange
        double value = 2;

        // Act
        CelsiusValue celsiusValue = new CelsiusValue(value);

        // Assert
        assertNotNull(celsiusValue);
    }

}
