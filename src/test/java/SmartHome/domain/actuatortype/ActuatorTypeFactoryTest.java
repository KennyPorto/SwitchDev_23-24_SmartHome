package SmartHome.domain.actuatortype;

import SmartHome.domain.valueObjects.ActuatorTypeId;
import SmartHome.domain.valueObjects.MeasurementUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActuatorTypeFactoryTest {
    @Test
    void validCreateActuatorType() {
        // Arrange
        String name = "actuator1";
        ActuatorTypeFactory actuatorTypeFactory = new ActuatorTypeFactory();
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId(name);

        // Act
        ActuatorType result = actuatorTypeFactory.createActuatorType(actuatorTypeId, MeasurementUnit.Binary);
        ActuatorType expected = new ActuatorType(actuatorTypeId, MeasurementUnit.Binary);

        // Assert
        assertEquals(expected.identity(), result.identity());
    }

    @Test
    void validCreateActuatorTypeName() {
        // Arrange
        String name = "actuator1";
        ActuatorTypeFactory actuatorTypeFactory = new ActuatorTypeFactory();
        Optional<ActuatorTypeId> actuatorTypeId = actuatorTypeFactory.createActuatorTypeName(name);

        // Act
        Optional<ActuatorTypeId> expected = Optional.of(new ActuatorTypeId(name));

        // Assert
        assertEquals(expected, actuatorTypeId);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " "})
    void createActuatorTypeNameWithEmptyParameters(String name) {
        // Arrange
        ActuatorTypeFactory actuatorTypeFactory = new ActuatorTypeFactory();

        // Assert
        assertEquals(Optional.empty(), actuatorTypeFactory.createActuatorTypeName(name));
    }
}
