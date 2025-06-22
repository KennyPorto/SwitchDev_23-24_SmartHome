package SmartHome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActuatorTypeDTOTest {

    @Test
    void validActuatorTypeDTO() {
        //Arrange
        String name = "actuator1";
        String measurementUnit = "Binary";

        //Act
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO(name, measurementUnit);

        //Assert
        assertEquals(name, actuatorTypeDTO.name);
        assertEquals(measurementUnit, actuatorTypeDTO.measurementUnit);

    }

}
