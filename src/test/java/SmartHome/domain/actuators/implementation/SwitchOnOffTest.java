package SmartHome.domain.actuators.implementation;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class SwitchOnOffTest {
    private ActuatorModel actuatorModel;

    @BeforeEach
    void setUp() {
        actuatorModel = new ActuatorModel("SwitchOnOffActuator");
    }

    @Test
    void actuatorNameNull() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new SwitchOnOffActuator(deviceIdDouble, null, idDouble, actuatorTypeIdDouble, actuatorModel));

        // Assert
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    void actuatorIdNull() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new SwitchOnOffActuator(deviceIdDouble, nameDouble, null, actuatorTypeIdDouble, actuatorModel));

        // Assert
        assertEquals("Id cannot be null", exception.getMessage());
    }

    @Test
    void actuatorDeviceIDNull() {
        // Arrange
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new SwitchOnOffActuator(null, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel));

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void actuatorActuatorTypeNameNull() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble, null, actuatorModel));

        // Assert
        assertEquals("ActuatorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void switchOn() {
        // arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator switchTest = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);

        // act
        boolean result = switchTest.switchActuator();

        // assert
        assertTrue(result);
    }

    @Test
    void switchOff() {
        // arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator switchTest = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);
        switchTest.switchActuator();

        // act
        boolean result = switchTest.switchActuator();

        // assert
        assertFalse(result);
    }

    @Test
    void isSwitchOff() {
        // arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator switchTest = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);

        // act
        boolean result = switchTest.isOn();

        // assert
        assertFalse(result);
    }

    @Test
    void isSwitchOn() {
        // arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator switchTest = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);
        switchTest.switchActuator();

        // act
        boolean result = switchTest.isOn();

        // assert
        assertTrue(result);
    }

    @Test
    void getName() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator switchOnOffActuator = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);

        // Assert
        assertEquals(nameDouble, switchOnOffActuator.getName());
    }

    @Test
    void getActuatorTypeName() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator switchOnOffActuator = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);

        // Assert
        assertEquals(actuatorTypeIdDouble, switchOnOffActuator.getActuatorTypeId());
    }

    @Test
    void getReading() {
        // arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator switchTest = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);
        switchTest.switchActuator();

        // act
        String result = String.valueOf(switchTest.isOn());

        // assert
        assertEquals("true", result);
    }

    @Test
    void getReadingFalse() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);

        // Act
        SwitchOnOffActuator switchOnOffActuator = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);
        String result = switchOnOffActuator.getReading();

        // Assert
        assertEquals("false", result, "The initial state of the switch should be off (false)");
    }

    @Test
    void getReadingTrue() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);

        // Act
        SwitchOnOffActuator switchOnOffActuator = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);
        switchOnOffActuator.switchActuator();
        String result = switchOnOffActuator.getReading();

        // Assert
        assertEquals("true", result, "After switching, the state of the switch should be on (true)");
    }


    @Test
    void validIdentity() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);

        // Assert
        assertEquals(idDouble, onOffActuator.identity());
    }

    @Test
    void switchOnOfActuatorIsSameAsEqualObject() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);
        SwitchOnOffActuator onOffActuator2 = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);

        // Act
        boolean result = onOffActuator.sameAs(onOffActuator2);

        // Assert
        assertTrue(result);
    }

    @Test
    void switchOnOfActuatorIsNotSameAsDifferentObject() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        BlindRollerActuator blindRollerActuator = mock(BlindRollerActuator.class);
        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);


        // Act
        boolean result = onOffActuator.sameAs(blindRollerActuator);

        // Assert
        assertFalse(result);
    }

    @Test
    void validDeviceId() {
        // Act
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator switchOnOffActuator = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);
        DeviceID result = switchOnOffActuator.getDeviceID();
        DeviceID expected = deviceIdDouble;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void switchOnOfActuatorIsSameAsEqualSameObject() {

        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);

        // Act
        boolean result = onOffActuator.sameAs(onOffActuator);

        // Assert
        assertTrue(result);
    }

    @Test
    void switchOnOfActuatorIsSameAsEqualObjectIsOnDifferent() {

        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);
        SwitchOnOffActuator onOffActuator2 = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);
        onOffActuator2.switchActuator();

        // Act
        boolean result = onOffActuator.sameAs(onOffActuator2);

        // Assert
        assertFalse(result);
    }

    @Test
    void switchOnOfActuatorIsSameAsEqualObjectDifferentName() {

        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);
        ActuatorName name2 = mock(ActuatorName.class);
        SwitchOnOffActuator onOffActuator2 = new SwitchOnOffActuator(deviceIdDouble, name2, idDouble,
                actuatorTypeIdDouble, actuatorModel);

        // Act
        boolean result = onOffActuator.sameAs(onOffActuator2);

        // Assert
        assertFalse(result);
    }

    @Test
    void switchOnOfActuatorIsSameAsEqualObjectDifferentId() {

        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);
        ActuatorId id2 = mock(ActuatorId.class);
        SwitchOnOffActuator onOffActuator2 = new SwitchOnOffActuator(deviceIdDouble, nameDouble, id2,
                actuatorTypeIdDouble, actuatorModel);

        // Act
        boolean result = onOffActuator.sameAs(onOffActuator2);

        // Assert
        assertFalse(result);
    }

    @Test
    void switchOnOfActuatorIsSameAsEqualObjectDifferentActuatorType() {

        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);
        ActuatorTypeId actuatorTypeIdDouble2 = mock(ActuatorTypeId.class);
        SwitchOnOffActuator onOffActuator2 = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble2, actuatorModel);

        // Act
        boolean result = onOffActuator.sameAs(onOffActuator2);

        // Assert
        assertFalse(result);
    }

    @Test
    void switchOnOfActuatorIsSameAsEqualObjectDifferentDeviceId() {

        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        ActuatorName nameDouble = mock(ActuatorName.class);
        ActuatorId idDouble = mock(ActuatorId.class);
        ActuatorTypeId actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);
        DeviceID deviceID2double = mock(DeviceID.class);
        SwitchOnOffActuator onOffActuator2 = new SwitchOnOffActuator(deviceID2double, nameDouble, idDouble,
                actuatorTypeIdDouble, actuatorModel);

        // Act
        boolean result = onOffActuator.sameAs(onOffActuator2);

        // Assert
        assertFalse(result);
    }

    // Aggregate tests

    @Test
    void actuatorNameAggregateNull() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new SwitchOnOffActuator(deviceId, null, id, actuatorTypeId, actuatorModel));

        // Assert
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    void actuatorIdAggregateNull() {

        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");


        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new SwitchOnOffActuator(deviceId, name, null, actuatorTypeId, actuatorModel));

        // Assert
        assertEquals("Id cannot be null", exception.getMessage());
    }

    @Test
    void actuatorDeviceIDAggregateNull() {
        // Arrange
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");


        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new SwitchOnOffActuator(null, name, id, actuatorTypeId, actuatorModel));

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void actuatorActuatorTypeNameAggregateNull() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new SwitchOnOffActuator(deviceId, name, id, null, actuatorModel));

        // Assert
        assertEquals("ActuatorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void switchOnAggregate() {
        // arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");

        SwitchOnOffActuator switchTest = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);

        // act
        boolean result = switchTest.switchActuator();

        // assert
        assertTrue(result);
    }

    @Test
    void switchOffAggregate() {
        // arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");

        SwitchOnOffActuator switchTest = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);
        switchTest.switchActuator();

        // act
        boolean result = switchTest.switchActuator();

        // assert
        assertFalse(result);
    }

    @Test
    void isSwitchOffAggregate() {
        // arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");

        SwitchOnOffActuator switchTest = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);

        // act
        boolean result = switchTest.isOn();

        // assert
        assertFalse(result);
    }

    @Test
    void isSwitchOnAggregate() {
        // arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");

        SwitchOnOffActuator switchTest = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);
        switchTest.switchActuator();

        // act
        boolean result = switchTest.isOn();

        // assert
        assertTrue(result);
    }

    @Test
    void getNameAggregate() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");

        SwitchOnOffActuator switchOnOffActuator = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId,
                actuatorModel);

        // Assert
        assertEquals(name, switchOnOffActuator.getName());
    }

    @Test
    void getActuatorTypeNameAggregate() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");

        SwitchOnOffActuator switchOnOffActuator = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId,
                actuatorModel);

        // Assert
        assertEquals(actuatorTypeId, switchOnOffActuator.getActuatorTypeId());
    }

    @Test
    void getReadingAggregate() {
        // arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");

        SwitchOnOffActuator switchTest = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);
        switchTest.switchActuator();

        // act
        String result = String.valueOf(switchTest.isOn());

        // assert
        assertEquals("true", result);
    }

    @Test
    void getReadingFalseAggregate() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");

        SwitchOnOffActuator switchOnOffActuator = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId,
                actuatorModel);

        // Act
        String result = switchOnOffActuator.getReading();

        // Assert
        assertEquals("false", result, "The initial state of the switch should be off (false)");
    }

    @Test
    void getReadingTrueAggregate() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");

        SwitchOnOffActuator switchOnOffActuator = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);
        switchOnOffActuator.switchActuator();

        // Act
        String result = switchOnOffActuator.getReading();

        // Assert
        assertEquals("true", result, "After switching, the state of the switch should be on (true)");
    }


    @Test
    void validIdentityAggregate() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");

        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);

        // Assert
        assertEquals(id, onOffActuator.identity());
    }

    @Test
    void switchOnOfActuatorAggregateIsSameAsEqualObject() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");

        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);
        SwitchOnOffActuator onOffActuator2 = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);

        // Act
        boolean result = onOffActuator.sameAs(onOffActuator2);

        // Assert
        assertTrue(result);
    }

    @Test
    void switchOnOfActuatorAggregateIsNotSameAsDifferentObject() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");

        BlindRollerActuator blindRollerActuator = mock(BlindRollerActuator.class);
        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);


        // Act
        boolean result = onOffActuator.sameAs(blindRollerActuator);

        // Assert
        assertFalse(result);
    }

    @Test
    void validDeviceIdAggregate() {
        // Act
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");
        DeviceID expected = deviceId;
        SwitchOnOffActuator switchOnOffActuator = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);

        // Act
        DeviceID result = switchOnOffActuator.getDeviceID();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void switchOnOfActuatorAggregateIsSameAsEqualSameObject() {

        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");
        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);

        // Act
        boolean result = onOffActuator.sameAs(onOffActuator);

        // Assert
        assertTrue(result);
    }

    @Test
    void switchOnOfActuatorAggregateIsSameAsEqualObjectIsOnDifferent() {

        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");
        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);
        SwitchOnOffActuator onOffActuator2 = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);
        onOffActuator2.switchActuator();

        // Act
        boolean result = onOffActuator.sameAs(onOffActuator2);

        // Assert
        assertFalse(result);
    }

    @Test
    void switchOnOfActuatorAggregateIsSameAsEqualObjectDifferentId() {

        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");

        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);
        ActuatorId id2 = new ActuatorId(2L);
        SwitchOnOffActuator onOffActuator2 = new SwitchOnOffActuator(deviceId, name, id2, actuatorTypeId, actuatorModel);

        // Act
        boolean result = onOffActuator.sameAs(onOffActuator2);

        // Assert
        assertFalse(result);
    }

    @Test
    void getModel() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorName name = new ActuatorName("SwitchOnOff");
        ActuatorId id = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("Switch1");
        SwitchOnOffActuator onOffActuator = new SwitchOnOffActuator(deviceId, name, id, actuatorTypeId, actuatorModel);

        // Act & Assert
        assertEquals("SwitchOnOffActuator", onOffActuator.getActuatorModel().model);
    }

}
