package SmartHome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActuatorDTOTest {
    @Test
    void validActuatorDTO() {
        // Act
        ActuatorDTO actuatorDTO = new ActuatorDTO("Lamp", "SwitchOnOffActuator", 1, 1,
                "SwitchOnOffActuator");

        // Assert
        assertEquals("Lamp", actuatorDTO.name);
        assertEquals("SwitchOnOffActuator", actuatorDTO.model);
        assertEquals(1, actuatorDTO.actuatorId);
        assertEquals(1, actuatorDTO.deviceId);
        assertEquals("SwitchOnOffActuator", actuatorDTO.actuatorType);
    }

    @Test
    void validActuatorDTO_withoutActuatorId()
    {
        // Act
        ActuatorDTO actuatorDTO = new ActuatorDTO("Lamp", "SwitchOnOffActuator", 1,
                "SwitchOnOffActuator");

        // Assert
        assertEquals("Lamp", actuatorDTO.name);
        assertEquals("SwitchOnOffActuator", actuatorDTO.model);
        assertEquals(1, actuatorDTO.deviceId);
        assertEquals("SwitchOnOffActuator", actuatorDTO.actuatorType);
    }

    @Test
    void validActuatorDTO_withIntParams() {
        // Act
        ActuatorDTO actuatorDTO = new ActuatorDTO("Lamp", "SwitchOnOffActuator", 1, 1,
                "SwitchOnOffActuator", -1, 1);
        assertEquals("Lamp", actuatorDTO.name);

        // Assert
        assertEquals("SwitchOnOffActuator", actuatorDTO.model);
        assertEquals(1, actuatorDTO.actuatorId);
        assertEquals(1, actuatorDTO.deviceId);
        assertEquals("SwitchOnOffActuator", actuatorDTO.actuatorType);
    }

    @Test
    void validActuatorDTO_withDoubleParams() {
        // Act
        ActuatorDTO actuatorDTO = new ActuatorDTO("Lamp", "SwitchOnOffActuator", 1, 1,
                "SwitchOnOffActuator", -1.0, 1.0);

        // Assert
        assertEquals("Lamp", actuatorDTO.name);
        assertEquals("SwitchOnOffActuator", actuatorDTO.model);
        assertEquals(1, actuatorDTO.actuatorId);
        assertEquals(1, actuatorDTO.deviceId);
        assertEquals("SwitchOnOffActuator", actuatorDTO.actuatorType);
    }

    @Test
    void validActuatorDTO_withPercentageValue()
    {
        // Act
        ActuatorDTO actuatorDTO = new ActuatorDTO("Blind", "BlindRollerActuator", 1, 1,
              "BlindRollerActuator", 5);

        // Assert
        assertEquals("Blind", actuatorDTO.name);
        assertEquals("BlindRollerActuator", actuatorDTO.model);
        assertEquals(1, actuatorDTO.actuatorId);
        assertEquals(1, actuatorDTO.deviceId);
        assertEquals("BlindRollerActuator", actuatorDTO.actuatorType);
    }

    @Test
    void validActuatorDTO_withPercentageValue_AutoId()
    {
        // Act
        ActuatorDTO actuatorDTO = new ActuatorDTO("Blind", "BlindRollerActuator", 1,
              "BlindRollerActuator", 5);

        // Assert
        assertEquals("Blind", actuatorDTO.name);
        assertEquals("BlindRollerActuator", actuatorDTO.model);
        assertEquals(1, actuatorDTO.deviceId);
        assertEquals("BlindRollerActuator", actuatorDTO.actuatorType);
    }
}
