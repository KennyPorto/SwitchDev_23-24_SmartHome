package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static SmartHome.utils.Constants.BLIND_ROLLER;
import static org.junit.jupiter.api.Assertions.*;

class ActuatorModelTest
{
    private final static String ACTUATOR_MODEL = "MEOWI-1000";
    private final ActuatorModel actuatorModel = new ActuatorModel(ACTUATOR_MODEL);

    @Test
    void validActuatorModel()
    {
        // Act
        String model = actuatorModel.model;

        // Assert
        assertEquals(ACTUATOR_MODEL, model);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "     ", "  \n  "})
    @NullAndEmptySource
    void invalidActuatorModel(String name)
    {
        // Arrange
        String expectedMessage = "Actuator model cannot be null or empty";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new ActuatorModel(name));
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void equalsSameObject()
    {
        // Act & Assert
        assertEquals(actuatorModel, actuatorModel);
    }

    @Test
    void equalsSameModel()
    {
        // Arrange
        ActuatorModel sameActuatorModel = new ActuatorModel(ACTUATOR_MODEL);

        // Act & Assert
        assertEquals(actuatorModel, sameActuatorModel);
    }

    @Test
    void shouldBeTheSameHashCode()
    {

        // Arrange
        ActuatorModel actuatorModel1 = new ActuatorModel(BLIND_ROLLER);
        ActuatorModel actuatorModel2 = new ActuatorModel(BLIND_ROLLER);

        // Act
        int hashCode1 = actuatorModel1.hashCode();
        int hashCode2 = actuatorModel2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void getActuatorModel() {
        ActuatorModel actuatorModel1 = new ActuatorModel(BLIND_ROLLER);

        assertEquals(BLIND_ROLLER, actuatorModel1.getModel());
    }
}