package SmartHome.domain.actuatortype;

import SmartHome.domain.valueObjects.ActuatorTypeId;
import SmartHome.domain.valueObjects.MeasurementUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ActuatorTypeTest {

    @Test
    void createActuatorType() {
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);

        new ActuatorType(actuatorTypeIdDouble, MeasurementUnit.Binary);
    }

    @Test
    void createActuatorType_identity() {
        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorType actuatorType = new ActuatorType(actuatorTypeIdDouble, MeasurementUnit.Binary);

        // Act
        ActuatorTypeId result = actuatorType.identity();

        // Assert
        assertEquals(actuatorTypeIdDouble, result);
    }

    @Test
    void createActuatorType_getFunctionality() {
        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorType actuatorType = new ActuatorType(actuatorTypeIdDouble, MeasurementUnit.Binary);

        // Act
        MeasurementUnit result = actuatorType.getMeasurementUnit();

        // Assert
        assertEquals(MeasurementUnit.Binary, result);
    }

    @Test
    void createActuatorType_sameAs_True() {
        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorType actuatorType = new ActuatorType(actuatorTypeIdDouble, MeasurementUnit.Binary);
        ActuatorType actuatorType1 = new ActuatorType(actuatorTypeIdDouble, MeasurementUnit.Binary);

        // Assert
        assertTrue(actuatorType.sameAs(actuatorType1));
    }

    @Test
    void createActuatorType_sameAs_SameObject_True()
    {
        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorType actuatorType = new ActuatorType(actuatorTypeIdDouble, MeasurementUnit.Binary);

        // Assert
        assertTrue(actuatorType.sameAs(actuatorType));
    }

    @Test
    void createActuatorType_sameAs_False() {
        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorType actuatorType = new ActuatorType(actuatorTypeIdDouble, MeasurementUnit.Binary);
        ActuatorType actuatorType1 = new ActuatorType(actuatorTypeIdDouble, MeasurementUnit.Speed);

        // Assert
        assertFalse(actuatorType.sameAs(actuatorType1));
    }

    @Test
    void createActuatorType_sameAs_DifferentType_False() {
        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorType actuatorType = new ActuatorType(actuatorTypeIdDouble, MeasurementUnit.Binary);
        int differentType = 2;

        // Act and Assert
        assertFalse(actuatorType.sameAs(differentType));
    }

    @Test
    void createActuatorType_sameAs_DifferentTypeObject() {
        // Arrange
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        ActuatorType actuatorType = new ActuatorType(actuatorTypeIdDouble, MeasurementUnit.Binary);
        int differentTypeObject = 2;

        // Assert
        assertFalse(actuatorType.sameAs(differentTypeObject));
    }



    //Aggregate tests
    @Test
    void actuatorTypeValidNAme_Aggregate() {
        //Arrange
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("actuatorTypeId");
        MeasurementUnit measurementUnit = MeasurementUnit.Binary;

        ActuatorType actuatorType = new ActuatorType(actuatorTypeId, measurementUnit);
        String expectedName = actuatorTypeId.toString();

        //Act
        String result = actuatorType.identity().toString();

        //Assert
        assertEquals(expectedName, result);
    }

    @Test
    void actuatorTypeValidMeasurementUnit_Aggregate() {
        //Arrange
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("actuatorTypeId");
        MeasurementUnit measurementUnit = MeasurementUnit.Binary;

        ActuatorType actuatorType = new ActuatorType(actuatorTypeId, measurementUnit);


        //Act
        MeasurementUnit result = actuatorType.getMeasurementUnit();

        //Assert
        assertEquals(measurementUnit, result);
    }

}
