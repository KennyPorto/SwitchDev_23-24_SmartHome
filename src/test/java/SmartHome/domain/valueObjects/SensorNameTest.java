package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class SensorNameTest {
    String SENSOR_NAME;
    SensorName sensorName;

    @BeforeEach
    void setUp() {
        SENSOR_NAME = "Blinds Actuator";
        sensorName = new SensorName(SENSOR_NAME);
    }
    @Test
    void validSensorName() {
        // Assert
        assertEquals(SENSOR_NAME, sensorName.name);
    }
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "     ", "  \n  "})
    @NullAndEmptySource
    void invalidSensorName(String name) {
        // Arrange
        String expectedMessage = "Sensor name cannot be null or empty";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new SensorName(name));
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void equalsSameName() {
        // Arrange
        SensorName sameSensorName = new SensorName(SENSOR_NAME);

        // Act & Assert
        assertEquals(sensorName, sameSensorName);
    }

    @Test
    void equalsSameObject() {
        // Act & Assert
        assertEquals(sensorName, sensorName);
    }

    @Test
    void notEqualsNullObject() {

        // Act & Assert
        assertNotEquals(sensorName, null);
    }

    @Test
    void shouldBeTheSameHashCode() {

        // Arrange
        SensorName sensorName1 = new SensorName("RoomTemperatureSensor");
        SensorName sensorName2 = new SensorName("RoomTemperatureSensor");

        // Act
        int hashCode1 = sensorName1.hashCode();
        int hashCode2 = sensorName2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }
}
