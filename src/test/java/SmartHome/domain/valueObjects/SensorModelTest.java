package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class SensorModelTest
{
    private final static String SENSOR_MODEL = "MEOWI-1000";
    private final SensorModel sensorModel = new SensorModel(SENSOR_MODEL);

    @Test
    void validSensorModel()
    {
        // Assert
        assertEquals(SENSOR_MODEL, sensorModel.model);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "     ", "  \n  "})
    @NullAndEmptySource
    void invalidSensorModel(String name)
    {
        // Arrange
        String expectedMessage = "Sensor model cannot be null or empty";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new SensorModel(name));
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void equalsSameObject()
    {
        // Act & Assert
        assertEquals(sensorModel, sensorModel);
    }

    @Test
    void equalsSameModel()
    {
        // Arrange
        SensorModel sameSensorModel = new SensorModel(SENSOR_MODEL);

        // Act & Assert
        assertEquals(sensorModel, sameSensorModel);
    }

    @Test
    void shouldBeTheSameHashCode()
    {

        // Arrange
        SensorModel sensorModel1 = new SensorModel("TemperatureSensor");
        SensorModel sensorModel2 = new SensorModel("TemperatureSensor");

        // Act
        int hashCode1 = sensorModel1.hashCode();
        int hashCode2 = sensorModel2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void getModel() {
        SensorModel sensorModel1 = new SensorModel("TemperatureSensor");

        assertEquals("TemperatureSensor", sensorModel1.getModel());
    }
}
