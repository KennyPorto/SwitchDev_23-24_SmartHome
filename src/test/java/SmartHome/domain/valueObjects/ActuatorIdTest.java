package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ActuatorIdTest {
    @ParameterizedTest
    @ValueSource(longs = {1L, Long.MAX_VALUE, 0})
    void validPositiveActuatorID(long value) {
        //Arrange
        ActuatorId actuatorID = new ActuatorId(value);

        // Act
        long id = actuatorID.id;

        // Assert
        assertEquals(value, id);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, Long.MIN_VALUE})
    void invalidNegativeActuatorID(long value) {
        // Arrange
        String expectedMessage = "Actuator ID cannot be smaller than 0";

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new ActuatorId(value));
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void equalsSameObject() {
        // Arrange
        ActuatorId actuatorID = new ActuatorId(1L);

        // Act & Assert
        assertEquals(actuatorID, actuatorID);
    }

    @Test
    void equalsSameID() {
        // Arrange
        ActuatorId actuatorID = new ActuatorId(1L);
        ActuatorId sameActuatorId = new ActuatorId(1L);

        // Act & Assert
        assertEquals(actuatorID, sameActuatorId);
    }

    @Test
    void notEqualsDifferentID() {
        // Arrange
        ActuatorId actuatorID = new ActuatorId(1L);
        ActuatorId differentActuatorId = new ActuatorId(2L);

        // Act & Assert
        assertNotEquals(actuatorID, differentActuatorId);
    }

    @Test
    void notEqualsDifferentObject() {
        // Arrange
        ActuatorId actuatorID = new ActuatorId(1L);
        ActuatorName actuatorName = new ActuatorName("Light Actuator");

        // Act & Assert
        assertNotEquals(actuatorID, actuatorName);
    }

    @Test
    void notEqualsNullObject() {
        ActuatorId actuatorID = new ActuatorId(1L);

        // Act & Assert
        assertNotEquals(actuatorID, null);
    }

    @Test
    void shouldHaveSameHashCode() {
        // Arrange
        ActuatorId actuatorID = new ActuatorId(1L);
        ActuatorId actuatorID2 = new ActuatorId(1L);

        // Act
        int hashCode1 = actuatorID.hashCode();
        int hashCode2 = actuatorID2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void shouldHaveDifferentHashCode() {
        // Arrange
        ActuatorId actuatorID = new ActuatorId(1L);
        ActuatorId actuatorID2 = new ActuatorId(2L);

        // Act
        int hashCode1 = actuatorID.hashCode();
        int hashCode2 = actuatorID2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    void getId() {
        ActuatorId actuatorId = new ActuatorId(1L);

        assertEquals(1, actuatorId.getId());
    }
}