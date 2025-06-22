package SmartHome.domain.actuators;

import SmartHome.domain.actuators.implementation.RangeActuatorDecimal;
import SmartHome.domain.actuators.implementation.RangeActuatorInt;
import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActuatorFactoryTest
{
    ActuatorFactory actuatorFactory;
    DeviceID deviceID;

    @BeforeEach
    void setUp()
    {
        deviceID = mock(DeviceID.class);
        actuatorFactory = new ActuatorFactory();
    }

    @Test
    void validCreateActuatorId()
    {
        //Arrange
        ActuatorFactory actuatorFactory = new ActuatorFactory();
        long id = 1;

        // Act
        Optional<ActuatorId> actuatorID = actuatorFactory.createActuatorId(id);

        // Assert
        assertTrue(actuatorID.isPresent());
    }

    @Test
    void validCreateActuatorName()
    {
        //Arrange
        ActuatorFactory actuatorFactory = new ActuatorFactory();
        String name = "BlindRoller";

        // Act
        Optional<ActuatorName> actuatorName = actuatorFactory.createActuatorName(name);

        // Assert
        assertTrue(actuatorName.isPresent());
    }

    @Test
    void validCreateActuatorModel()
    {
        //Arrange
        ActuatorFactory actuatorFactory = new ActuatorFactory();
        String model = "BlindRoller";

        // Act
        Optional<ActuatorModel> actuatorModel = actuatorFactory.createActuatorModel(model);

        // Assert
        assertTrue(actuatorModel.isPresent());
    }

    @Test
    void createActuatorWithAllValidValueObjects()
    {
        //Arrange
        ActuatorModel model = new ActuatorModel("BlindRollerActuator");
        ActuatorName actuatorName = mock(ActuatorName.class);
        ActuatorId actuatorID = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeId = mock(ActuatorTypeId.class);

        // Act
        Actuator actuator = actuatorFactory.createActuator(deviceID, model, actuatorName, actuatorID, actuatorTypeId);

        // Assert
        assertNotNull(actuator);
    }


    @ParameterizedTest
    @NullAndEmptySource
    void invalidCreateActuatorName(String name)
    {
        //Arrange
        ActuatorFactory actuatorFactory = new ActuatorFactory();

        // Act
        Optional<ActuatorName> invalidActuator = actuatorFactory.createActuatorName(name);

        // Assert
        assertTrue(invalidActuator.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void invalidCreateActuatorModel(String model)
    {
        //Arrange
        ActuatorFactory actuatorFactory = new ActuatorFactory();

        // Act
        Optional<ActuatorModel> invalidActuator = actuatorFactory.createActuatorModel(model);

        // Assert
        assertTrue(invalidActuator.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, Long.MIN_VALUE})
    void invalidCreateActuatorId(long id)
    {
        //Arrange
        ActuatorFactory actuatorFactory = new ActuatorFactory();

        // Act
        Optional<ActuatorId> invalidActuator = actuatorFactory.createActuatorId(id);

        // Assert
        assertTrue(invalidActuator.isEmpty());
    }

    @Test
    void invalidCreateActuatorNullModel()
    {
        //Arrange
        ActuatorName actuatorName = mock(ActuatorName.class);
        ActuatorId actuatorID = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);

        // Act
        Actuator actuator = actuatorFactory.createActuator(deviceID, null, actuatorName, actuatorID, actuatorTypeIdDouble);

        // Assert
        assertNull(actuator);
    }

    @Test
    void invalidCreateActuatorNullName()
    {
        //Arrange
        ActuatorModel actuatorModel = mock(ActuatorModel.class);
        when(actuatorModel.toString()).thenReturn("BlindRollerActuator");
        ActuatorId actuatorID = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        // Act
        Actuator actuator = actuatorFactory.createActuator(deviceID, actuatorModel, null, actuatorID, actuatorTypeIdDouble);

        // Assert
        assertNull(actuator);
    }

    @Test
    void invalidCreateActuatorNullId()
    {
        //Arrange
        ActuatorModel actuatorModel = mock(ActuatorModel.class);
        when(actuatorModel.toString()).thenReturn("BlindRollerActuator");
        ActuatorName actuatorName = mock(ActuatorName.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);

        // Act
        Actuator actuator = actuatorFactory.createActuator(deviceID, actuatorModel, actuatorName, null, actuatorTypeIdDouble);

        // Assert
        assertNull(actuator);
    }

    @Test
    void invalidCreateActuatorNullActuatorTypeName()
    {
        //Arrange
        ActuatorModel actuatorModel = mock(ActuatorModel.class);
        when(actuatorModel.toString()).thenReturn("BlindRollerActuator");
        ActuatorName actuatorName = mock(ActuatorName.class);
        ActuatorId actuatorID = mock(ActuatorId.class);

        // Act
        Actuator actuator = actuatorFactory.createActuator(deviceID, actuatorModel, actuatorName, actuatorID, null);

        // Assert
        assertNull(actuator);
    }

    @Test
    void invalidCreateActuatorNotFoundModel()
    {
        //Arrange
        ActuatorName actuatorName = mock(ActuatorName.class);
        ActuatorModel actuatorModel = mock(ActuatorModel.class);
        when(actuatorModel.toString()).thenReturn("InvalidModel");
        ActuatorId actuatorID = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);

        // Act
        Actuator actuator = actuatorFactory.createActuator(deviceID, actuatorModel, actuatorName, actuatorID, actuatorTypeIdDouble);

        // Assert
        assertNull(actuator);
    }

    @Test
    void invalidCreateActuatorNullDeviceId()
    {
        //Arrange
        ActuatorModel actuatorModel = mock(ActuatorModel.class);
        when(actuatorModel.toString()).thenReturn("BlindRollerActuator");
        ActuatorName actuatorName = mock(ActuatorName.class);
        ActuatorId actuatorID = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);

        // Act
        Actuator actuator = actuatorFactory.createActuator(null, actuatorModel, actuatorName, actuatorID, actuatorTypeIdDouble);

        // Assert
        assertNull(actuator);
    }

    @Test
    void createActuatorIntWithException()
    {
        //Arrange
        ActuatorModel actuatorModel = mock(ActuatorModel.class);
        when(actuatorModel.toString()).thenReturn("RangeActuatorInt");
        ActuatorName actuatorName = mock(ActuatorName.class);
        ActuatorId actuatorID = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        LimitInt limitInt = mock(LimitInt.class);

        // Act
        Actuator actuator = actuatorFactory.createActuator(deviceID, actuatorModel, actuatorName, actuatorID, actuatorTypeIdDouble, limitInt);

        // Assert
        assertNull(actuator);
    }

    @Test
    void createRangeActuatorDecimalWithException()
    {
        //Arrange
        ActuatorModel actuatorModel = mock(ActuatorModel.class);
        when(actuatorModel.toString()).thenReturn("RangeActuatorDecimal");
        ActuatorName actuatorName = mock(ActuatorName.class);
        ActuatorId actuatorID = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        LimitFractional limitFractional = mock(LimitFractional.class);

        // Act
        Actuator actuator = actuatorFactory.createActuator(deviceID, actuatorModel, actuatorName, actuatorID, actuatorTypeIdDouble, limitFractional);

        // Assert
        assertNull(actuator);
    }

    @Test
    void createRangeActuatorInt()
    {
        // Arrange
        ActuatorId actuatorId = new ActuatorId(1L);
        ActuatorName actuatorName = new ActuatorName("Act1");
        ActuatorModel actuatorModel = new ActuatorModel("RangeActuatorInt");
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Range");
        DeviceID deviceID1 = new DeviceID(1L);
        LimitInt limitInt = new LimitInt(-1, 1);
        Actuator expected = new RangeActuatorInt(deviceID1, actuatorName, actuatorId, actuatorTypeId, actuatorModel,
                limitInt);

        // Act
        ActuatorFactory actuatorFactory1 = new ActuatorFactory();
        Actuator rangeActuatorInt = actuatorFactory1.createActuator(deviceID1, actuatorModel, actuatorName,
                actuatorId, actuatorTypeId, limitInt);

        // Assert
        assertTrue(expected.sameAs(rangeActuatorInt));
    }

    @Test
    void createRangeActuatorDecimal()
    {
        // Arrange
        ActuatorId actuatorId = new ActuatorId(1L);
        ActuatorName actuatorName = new ActuatorName("Act1");
        ActuatorModel actuatorModel = new ActuatorModel("RangeActuatorDecimal");
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Range");
        DeviceID deviceID1 = new DeviceID(1L);
        LimitFractional limitFractional = new LimitFractional(-1.0, 1.0);
        Actuator expected = new RangeActuatorDecimal(deviceID1, actuatorName, actuatorId, actuatorTypeId,
                limitFractional, actuatorModel);

        // Act
        ActuatorFactory actuatorFactory1 = new ActuatorFactory();
        Actuator rangeActuatorFractional = actuatorFactory1.createActuator(deviceID1, actuatorModel, actuatorName,
                actuatorId, actuatorTypeId, limitFractional);

        // Assert
        assertTrue(expected.sameAs(rangeActuatorFractional));
    }
}
