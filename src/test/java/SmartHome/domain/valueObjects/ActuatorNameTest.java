package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ActuatorNameTest {
    String ACTUATOR_NAME;
    ActuatorName actuatorName;

    @BeforeEach
    void setUp() {
        ACTUATOR_NAME = "Blinds Actuator";
        actuatorName = new ActuatorName(ACTUATOR_NAME);
    }

    @Test
    void validActuatorName() {
        // Act
        String name = actuatorName.name;

        // Assert
        assertEquals(ACTUATOR_NAME, name);
    }

    @ParameterizedTest
    @ValueSource(strings = {"     ", "  \n  "})
    @NullAndEmptySource
    void invalidActuatorName(String name) {
        // Arrange
        String expectedMessage = "Actuator name cannot be null or empty";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new ActuatorName(name));
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void equalsSameName() {
        // Arrange
        ActuatorName sameActuatorName = new ActuatorName(ACTUATOR_NAME);

        // Act & Assert
        assertEquals(actuatorName, sameActuatorName);
    }

    @Test
    void equalsSameObject() {
        // Act & Assert
        assertEquals(actuatorName, actuatorName);
    }

    @Test
    void shouldBeTheSameHashCode() {

        // Arrange
        ActuatorName actuatorName1 = new ActuatorName("RoomBlindroller");
        ActuatorName actuatorName2 = new ActuatorName("RoomBlindroller");

        // Act
        int hashCode1 = actuatorName1.hashCode();
        int hashCode2 = actuatorName2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }
}