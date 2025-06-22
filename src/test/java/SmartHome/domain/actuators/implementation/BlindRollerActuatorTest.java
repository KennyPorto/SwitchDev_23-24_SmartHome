package SmartHome.domain.actuators.implementation;

import SmartHome.domain.valueObjects.*;
import SmartHome.domain.valueObjects.values.implementation.PercentageValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BlindRollerActuatorTest
{
    private ActuatorName name;
    private ActuatorId id;
    private DeviceID deviceID;
    private ActuatorTypeId actuatorTypeIdDouble;
    private ActuatorModel actuatorModel;

    @BeforeEach
    void setUp()
    {
        actuatorModel = new ActuatorModel("BlindRollerActuator");
        deviceID = mock(DeviceID.class);
        name = mock(ActuatorName.class);
        id = mock(ActuatorId.class);
        actuatorTypeIdDouble = mock(ActuatorTypeId.class);
    }

    @Test
    void actuatorNameNull()
    {
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new BlindRollerActuator(deviceID, null, id, actuatorTypeIdDouble, actuatorModel));

        // Assert
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    void actuatorIdNull()
    {
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new BlindRollerActuator(deviceID, name, null, actuatorTypeIdDouble, actuatorModel));

        // Assert
        assertEquals("Id cannot be null", exception.getMessage());
    }

    @Test
    void deviceIDNull()
    {
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new BlindRollerActuator(null, name, id, actuatorTypeIdDouble, actuatorModel));

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void actuatorTypeIdNull()
    {
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new BlindRollerActuator(deviceID, name, id, null, actuatorModel));

        // Assert
        assertEquals("ActuatorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void validGetName()
    {
        // Arrange
        BlindRollerActuator blindRollerActuator = new BlindRollerActuator(deviceID, name, id,
                actuatorTypeIdDouble, actuatorModel);

        // Assert
        assertEquals(name, blindRollerActuator.getName());
    }

    @Test
    void validIdentity()
    {
        // Arrange
        BlindRollerActuator blindRollerActuator = new BlindRollerActuator(deviceID, name, id, actuatorTypeIdDouble,
                actuatorModel);

        // Assert
        assertEquals(id, blindRollerActuator.identity());
    }

    @Test
    void validDeviceId()
    {
        // Arrange
        BlindRollerActuator blindRollerActuator = new BlindRollerActuator(deviceID, name, id, actuatorTypeIdDouble,
                actuatorModel);
        // Assert
        assertEquals(deviceID, blindRollerActuator.getDeviceID());
    }

    @Test
    void validGetSensorTypeName()
    {
        // Arrange
        BlindRollerActuator blindRollerActuator = new BlindRollerActuator(deviceID, name, id,
                actuatorTypeIdDouble, actuatorModel);

        // Assert
        assertEquals(actuatorTypeIdDouble, blindRollerActuator.getActuatorTypeId());
    }

    @Test
    void blindRollerIsSameAsSameObject()
    {
        // Arrange
        BlindRollerActuator blindRollerActuator = new BlindRollerActuator(deviceID, name, id,
                actuatorTypeIdDouble, actuatorModel);

        // Act
        boolean result = blindRollerActuator.sameAs(blindRollerActuator);

        // Assert
        assertTrue(result);
    }

    @Test
    void blindRollerIsSameAsEqualObject()
    {
        // Arrange
        BlindRollerActuator blindRollerActuator = new BlindRollerActuator(deviceID, name, id,
                actuatorTypeIdDouble, actuatorModel);
        BlindRollerActuator blindRollerActuator2 = new BlindRollerActuator(deviceID, name, id,
                actuatorTypeIdDouble, actuatorModel);

        // Act
        boolean result = blindRollerActuator.sameAs(blindRollerActuator2);

        // Assert
        assertTrue(result);
    }

    @Test
    void blindRollerIsNotSameAsDifferentObject()
    {
        // Arrange
        BlindRollerActuator blindRollerActuator = new BlindRollerActuator(deviceID, name, id,
                actuatorTypeIdDouble, actuatorModel);
        RangeActuatorDecimal rangeActuatorDecimal = mock(RangeActuatorDecimal.class);

        // Act
        boolean result = blindRollerActuator.sameAs(rangeActuatorDecimal);

        // Assert
        assertFalse(result);
    }

    @Test
    void blindRollerIsNotSameAsNullObject()
    {
        // Arrange
        BlindRollerActuator blindRollerActuator = new BlindRollerActuator(deviceID, name, id,
                actuatorTypeIdDouble, actuatorModel);

        // Act
        boolean result = blindRollerActuator.sameAs(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void blindRollerIsNotSameAsBlindRollerWithDifferentName()
    {
        // Arrange
        ActuatorName differentName = mock(ActuatorName.class);
        when(differentName.toString()).thenReturn("DifferentName");
        BlindRollerActuator blindRollerActuator = new BlindRollerActuator(deviceID, name, id,
                actuatorTypeIdDouble, actuatorModel);
        BlindRollerActuator blindRollerActuator2 = new BlindRollerActuator(deviceID, differentName, id,
                actuatorTypeIdDouble, actuatorModel);

        // Act
        boolean result = blindRollerActuator.sameAs(blindRollerActuator2);

        // Assert
        assertFalse(result);
    }

    @Test
    void blindRollerIsNotSameAsBlindRollerWithDifferentDeviceId()
    {
        // Arrange
        DeviceID differentDeviceID = mock(DeviceID.class);
        when(String.valueOf(differentDeviceID)).thenReturn("1L");
        BlindRollerActuator blindRollerActuator = new BlindRollerActuator(deviceID, name, id,
                actuatorTypeIdDouble, actuatorModel);
        BlindRollerActuator blindRollerActuator2 = new BlindRollerActuator(differentDeviceID, name, id,
                actuatorTypeIdDouble, actuatorModel);

        // Act
        boolean result = blindRollerActuator.sameAs(blindRollerActuator2);

        // Assert
        assertFalse(result);
    }

    @Test
    void blindRollerIsNotSameAsBlindRollerWithDifferentActuatorTypeId()
    {
        // Arrange
        ActuatorTypeId differentActuatorTypeId = mock(ActuatorTypeId.class);
        when(differentActuatorTypeId.toString()).thenReturn("DifferentActuatorTypeId");
        BlindRollerActuator blindRollerActuator = new BlindRollerActuator(deviceID, name, id,
                actuatorTypeIdDouble, actuatorModel);
        BlindRollerActuator blindRollerActuator2 = new BlindRollerActuator(deviceID, name, id,
                differentActuatorTypeId, actuatorModel);

        // Act
        boolean result = blindRollerActuator.sameAs(blindRollerActuator2);

        // Assert
        assertFalse(result);
    }

    @Test
    void blindRollerIsNotSameAsBlindRollerWithDifferentId()
    {
        // Arrange
        ActuatorId differentId = mock(ActuatorId.class);
        when(differentId.toString()).thenReturn("DifferentId");
        BlindRollerActuator blindRollerActuator = new BlindRollerActuator(deviceID, name, id,
                actuatorTypeIdDouble, actuatorModel);
        BlindRollerActuator blindRollerActuator2 = new BlindRollerActuator(deviceID, name, differentId,
                actuatorTypeIdDouble, actuatorModel);

        // Act
        boolean result = blindRollerActuator.sameAs(blindRollerActuator2);

        // Assert
        assertFalse(result);
    }

    @Test
    void blindRollerAggregateValidName()
    {
        // Arrange
        ActuatorName name = new ActuatorName("Blinds Living Room");
        ActuatorId id = new ActuatorId(1);
        ActuatorTypeId typeId = new ActuatorTypeId("BlindRoller");
        DeviceID deviceId = new DeviceID(1);
        BlindRollerActuator blindActuator = new BlindRollerActuator(deviceId, name, id, typeId, actuatorModel);
        String expected = name.name;

        // Act
        String result = blindActuator.getName().name;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void blindRollerAggregateValidDeviceId()
    {
        // Arrange
        ActuatorName name = new ActuatorName("Blinds Living Room");
        ActuatorId id = new ActuatorId(1);
        ActuatorTypeId typeId = new ActuatorTypeId("BlindRoller");
        DeviceID deviceId = new DeviceID(1);
        BlindRollerActuator blindActuator = new BlindRollerActuator(deviceId, name, id, typeId, actuatorModel);
        long expected = deviceId.id;

        // Act
        long result = blindActuator.getDeviceID().id;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void blindRollerAggregateValidTypeId()
    {
        // Arrange
        ActuatorName name = new ActuatorName("Blinds Living Room");
        ActuatorId id = new ActuatorId(1);
        ActuatorTypeId typeId = new ActuatorTypeId("BlindRoller");
        DeviceID deviceId = new DeviceID(1);
        BlindRollerActuator blindActuator = new BlindRollerActuator(deviceId, name, id, typeId, actuatorModel);
        String expected = typeId.name;

        // Act
        String result = blindActuator.getActuatorTypeId().name;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void blindRollerAggregateValidActuatorId()
    {
        // Arrange
        ActuatorName name = new ActuatorName("Blinds Living Room");
        ActuatorId id = new ActuatorId(1);
        ActuatorTypeId typeId = new ActuatorTypeId("BlindRoller");
        DeviceID deviceId = new DeviceID(1);
        BlindRollerActuator blindActuator = new BlindRollerActuator(deviceId, name, id, typeId, actuatorModel);
        long expected = id.id;

        // Act
        long result = blindActuator.identity().id;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getActuatorModel() {
        BlindRollerActuator blindRollerActuator1 = new BlindRollerActuator(new DeviceID(1), new ActuatorName("act"),
                new ActuatorId(1), new ActuatorTypeId("1"), actuatorModel);
        String result = blindRollerActuator1.getActuatorModel().model;

        String expected = "BlindRollerActuator";

        assertEquals(expected, result);
    }

    @Test
    void blindRollerAggregateDefaultCurrentPercentage()
    {
        // Arrange
        ActuatorName name = new ActuatorName("Blinds Living Room");
        ActuatorId id = new ActuatorId(1);
        ActuatorTypeId typeId = new ActuatorTypeId("BlindRoller");
        DeviceID deviceId = new DeviceID(1);
        BlindRollerActuator blindActuator = new BlindRollerActuator(deviceId, name, id, typeId, actuatorModel);
        int expected = 100;

        // Act
        int result = blindActuator.getCurrentPercentage().value;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void blindRollerAggregateValidCurrentPercentageAfterSet()
    {
        // Arrange
        ActuatorName name = new ActuatorName("Blinds Living Room");
        ActuatorId id = new ActuatorId(1);
        ActuatorTypeId typeId = new ActuatorTypeId("BlindRoller");
        DeviceID deviceId = new DeviceID(1);
        BlindRollerActuator blindActuator = new BlindRollerActuator(deviceId, name, id, typeId, actuatorModel);
        int expected = 50;

        // Act
        blindActuator.setCurrentPercentage(new PercentageValue(50));
        int result = blindActuator.getCurrentPercentage().value;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void blindRollerAggregateValidCurrentPercentageAfterSetToZero()
    {
        // Arrange
        ActuatorName name = new ActuatorName("Blinds Living Room");
        ActuatorId id = new ActuatorId(1);
        ActuatorTypeId typeId = new ActuatorTypeId("BlindRoller");
        DeviceID deviceId = new DeviceID(1);
        BlindRollerActuator blindActuator = new BlindRollerActuator(deviceId, name, id, typeId, actuatorModel);
        int expected = 0;

        // Act
        BlindRollerActuator actuator = blindActuator.setCurrentPercentage(new PercentageValue(0));

        // Assert
        assertEquals(expected, actuator.getCurrentPercentage().value);
    }

    @Test
    void blindRollerAggregateInvalidPercentage()
    {
        // Arrange
        ActuatorName name = new ActuatorName("Blinds Living Room");
        ActuatorId id = new ActuatorId(1);
        ActuatorTypeId typeId = new ActuatorTypeId("BlindRoller");
        DeviceID deviceId = new DeviceID(1);
        BlindRollerActuator blindActuator = new BlindRollerActuator(deviceId, name, id, typeId, actuatorModel);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
              () -> blindActuator.setCurrentPercentage(new PercentageValue(101)));

        // Assert
        assertEquals("Percentage must be between 0 and 100.", exception.getMessage());
    }
}