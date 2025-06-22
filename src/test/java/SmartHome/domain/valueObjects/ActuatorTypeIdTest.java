package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ActuatorTypeIdTest {

    @Test
    void validCreateActuatorTypeName() {
        //Arrange
        String name = "actuator";
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId(name);
        String expected = "actuator";

        //Act
        String result = actuatorTypeId.name;

        //Assert
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @NullAndEmptySource
    void createActuatorTypeNameWithInvalidParameters(String name) {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorTypeId(name));
    }

    @Test
    void validNameToString()
    {
        //Arrange
        String name = "actuator";
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId(name);

        //Act and Assert
        assertEquals(name, actuatorTypeId.toString());
    }

    @Test
    void validEquals()
    {
        //Arrange
        String name = "actuator";
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId(name);
        ActuatorTypeId actuatorTypeId2 = new ActuatorTypeId(name);

        //Act and Assert
        assertEquals(actuatorTypeId, actuatorTypeId2);
    }

    @Test
    void validHashCode()
    {
        //Arrange
        String name = "actuator";
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId(name);
        ActuatorTypeId actuatorTypeId2 = new ActuatorTypeId(name);

        //Act and Assert
        assertEquals(actuatorTypeId.hashCode(), actuatorTypeId2.hashCode());
    }

    @Test
    void validEqualsSameObject()
    {
        //Arrange
        String name = "actuator";
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId(name);

        //Act and Assert
        assertEquals(actuatorTypeId, actuatorTypeId);
    }

    @Test
    void invalidEqualsDifferentClass()
    {
        //Arrange
        String name = "actuator";
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId(name);

        //Act and Assert
        assertNotEquals(actuatorTypeId, name);
    }

    @Test
    void invalidEqualsNull()
    {
        //Arrange
        String name = "actuator";
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId(name);

        //Act and Assert
        assertNotEquals(actuatorTypeId, null);
    }

    @Test
    void getActuatorTypeId() {
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("actuator");

        assertEquals("actuator", actuatorTypeId.getName());
    }
}
