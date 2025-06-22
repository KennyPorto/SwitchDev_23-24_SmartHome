package SmartHome.domain.sensortype;

import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.domain.valueObjects.SensorTypeId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SensorTypeFactoryTest {
    @Test
    void createSensorTypeName_success() {
        // Arrange
        String name = "s1";
        SensorTypeFactory sensorTypeFactory = new SensorTypeFactory();
        Optional<SensorTypeId> sensorTypeId = sensorTypeFactory.createSensorTypeName(name);

        if (sensorTypeId.isPresent()) {
            // Act
            SensorTypeId result = sensorTypeId.get();
            SensorTypeId expected = new SensorTypeId(name);

            // Assert
            assertEquals(expected.id, result.id);
        }
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void createSensorTypeName_fail(String name) {
        // Arrange
        SensorTypeFactory sensorTypeFactory = new SensorTypeFactory();

        // Assert
        assertEquals(Optional.empty(), sensorTypeFactory.createSensorTypeName(name));
    }

    @Test
    void createSensorType() {
        // Arrange
        String name = "s1";
        SensorTypeFactory sensorTypeFactory = new SensorTypeFactory();
        SensorTypeId sensorTypeId = new SensorTypeId(name);

        // Act
        SensorType result = sensorTypeFactory.createSensorType(sensorTypeId, MeasurementUnit.Percentage);
        SensorType expected = new SensorType(sensorTypeId, MeasurementUnit.Percentage);

        // Assert
        assertEquals(expected.identity(), result.identity());
    }

    @Test
    void createSensorTypeId_successful() {
        String id = "Temp";
        SensorTypeFactory sensorTypeFactory = new SensorTypeFactory();

        // Act
        Optional<SensorTypeId> result = sensorTypeFactory.createSensorTypeName(id);
        Optional<SensorTypeId> expected = Optional.of(new SensorTypeId(id));

        // Assert
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void createSensorTypeId_fail(String id) {
        SensorTypeFactory sensorTypeFactory = new SensorTypeFactory();

        // Act
        Optional<SensorTypeId> result = sensorTypeFactory.createSensorTypeName(id);
        Optional<SensorTypeId> expected = Optional.empty();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void createSensorTypeName_fail() {
        // Arrange
        String name = "";
        SensorTypeFactory sensorTypeFactory = new SensorTypeFactory();

        // Act
        Optional<SensorTypeId> sensorTypeId = sensorTypeFactory.createSensorTypeName(name);

        // Assert
        assertFalse(sensorTypeId.isPresent());
    }
}