package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class MeasurementTest
{
    @ParameterizedTest
    @NullAndEmptySource
    void constructorWithInvalidMeasurementUnit(String value)
    {
        // Arrange
        String expected = "Measurement cannot be null or empty.";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Measurement(value));
        String actual = exception.getMessage();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void constructorWithValidMeasurementUnit()
    {
        // Arrange
        String validMeasurement = "33";

        // Act
        Measurement measurement = new Measurement(validMeasurement);

        // Assert
        assertEquals(validMeasurement, measurement.measurement);
    }

    @Test
    void objectWithTheSameMeasurementEqualsTrue()
    {
        // Arrange
        String measurement = "33";
        Measurement newMeasurement = new Measurement(measurement);
        Measurement newMeasurement2 = new Measurement(measurement);

        // Act
        boolean actual = newMeasurement.equals(newMeasurement2);

        // Assert
        assertTrue(actual);
    }

    @Test
    void sameTypeObjectWithDifferentMeasurementEqualsFalse()
    {
        // Arrange
        String measurement = "33";
        String measurement2 = "44";
        Measurement newMeasurement = new Measurement(measurement);
        Measurement newMeasurement2 = new Measurement(measurement2);

        // Act
        boolean actual = newMeasurement.equals(newMeasurement2);

        // Assert
        assertFalse(actual);
    }

    @Test
    void sameObjectIsEqual()
    {
        // Arrange
        String measurement = "33";
        Measurement newMeasurement = new Measurement(measurement);

        // Act
        boolean result = newMeasurement.equals(newMeasurement);

        // Assert
        assertTrue(result);
    }

    @Test
    void differentObjectIsNotEqual()
    {
        // Arrange
        String measurement = "33";
        Measurement newMeasurement = new Measurement(measurement);
        Measurement newMeasurement2 = new Measurement("44");

        // Act
        boolean result = newMeasurement.equals(newMeasurement2);

        // Assert
        assertFalse(result);
    }

    @Test
    void differentTypeObjectWithSameMeasurementEqualsFalse()
    {
        // Arrange
        String measurement = "33";
        Measurement newMeasurement = new Measurement(measurement);
        Object newObject = new Object();

        // Act
        boolean actual = newMeasurement.equals(newObject);

        // Assert
        assertFalse(actual);
    }

    @Test
    void hashCodeWithSameMeasurement()
    {
        // Arrange
        String measurement = "33";
        Measurement newMeasurement = new Measurement(measurement);
        Measurement newMeasurement2 = new Measurement(measurement);

        // Act
        int hashCode1 = newMeasurement.hashCode();
        int hashCode2 = newMeasurement2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void hashCodeWithDifferentMeasurement()
    {
        // Arrange
        String measurement = "33";
        String measurement2 = "44";
        Measurement newMeasurement = new Measurement(measurement);
        Measurement newMeasurement2 = new Measurement(measurement2);

        // Act
        int hashCode1 = newMeasurement.hashCode();
        int hashCode2 = newMeasurement2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    void getMeasurement() {
        Measurement measurement = new Measurement("1.0");

        assertEquals("1.0", measurement.getMeasurement());
    }
}