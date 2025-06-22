package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class SensorIdTest {
    @ParameterizedTest
    @ValueSource(longs = {1L, Long.MAX_VALUE})
    void validPositiveSensorID(long value) {
        //Arrange
        SensorId sensorID = new SensorId(value);

        // Act
        long actualValue = sensorID.id;

        // Assert
        assertEquals(value, actualValue);
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, - 1L, Long.MIN_VALUE})
    void invalidNegativeSensorID(long value) {
        // Arrange
        String expectedMessage = "Sensor ID cannot be smaller than 0";

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new SensorId(value));
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void equalsSameObject() {
        // Arrange
        SensorId sensorID = new SensorId(1L);

        // Act & Assert
        assertEquals(sensorID, sensorID);
    }

    @Test
    void equalsSameID() {
        // Arrange
        SensorId sensorID = new SensorId(1L);
        SensorId sameSensorID = new SensorId(1L);

        // Act & Assert
        assertEquals(sensorID, sameSensorID);
    }

    @Test
    void notEqualsDifferentID() {
        // Arrange
        SensorId sensorID = new SensorId(1L);
        SensorId differentSensorID = new SensorId(2L);

        // Act & Assert
        assertNotEquals(sensorID, differentSensorID);
    }

    @Test
    void notEqualsDifferentObject() {
        // Arrange
        SensorId sensorID = new SensorId(1L);
        SensorName sensorName = new SensorName("Temperature Sensor");

        // Act & Assert
        assertNotEquals(sensorID, sensorName);
    }

    @Test
    void notEqualsNullObject() {
        SensorId sensorId = new SensorId(1L);

        // Act & Assert
        assertNotEquals(sensorId, null);
    }

    @Test
    void shouldHaveSameHashCode() {
        // Arrange
        SensorId sensorId = new SensorId(1L);
        SensorId sensorId1 = new SensorId(1L);

        // Act
        int hashCode1 = sensorId.hashCode();
        int hashCode2 = sensorId1.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void shouldHaveDifferentHashCode() {
        // Arrange
        SensorId sensorId = new SensorId(1L);
        SensorId sensorId2 = new SensorId(2L);

        // Act
        int hashCode1 = sensorId.hashCode();
        int hashCode2 = sensorId2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2);
    }
}
