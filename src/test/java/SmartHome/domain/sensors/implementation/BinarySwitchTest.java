package SmartHome.domain.sensors.implementation;

import SmartHome.domain.actuators.implementation.SwitchOnOffActuator;
import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BinarySwitchTest
{
    private SensorModel sensorModel;
    @BeforeEach
    void setUp() {
        sensorModel = new SensorModel("BinarySwitch");
    }

    @Test
    void isOn() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        boolean isOn = true;
        SwitchOnOffActuator switchTest = mock(SwitchOnOffActuator.class);
        when(switchTest.isOn()).thenReturn(isOn);
        switchTest.switchActuator();
        BinarySwitch binarySwitch = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        binarySwitch.configureSensor(switchTest);

        // Act
        boolean result = binarySwitch.readStatus();

        // Assert
        assertTrue(result);
    }

    @Test
    void isOff() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        boolean isOn = false;
        SwitchOnOffActuator switchTest = mock(SwitchOnOffActuator.class);
        when(switchTest.isOn()).thenReturn(isOn);
        BinarySwitch binarySwitch = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        binarySwitch.configureSensor(switchTest);

        // Act
        boolean result = binarySwitch.readStatus();

        // Assert
        assertFalse(result);
    }

    @Test
    void constructorNullName() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        String expected = "Name cannot be null or empty";

        // Act + assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new BinarySwitch(deviceIdDouble, null, idDouble, sensorTypeIdDouble, sensorModel));
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    @Test
    void constructorNullID() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        String expected = "SensorId cannot be null";

        // Act + assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new BinarySwitch(deviceIdDouble, nameDouble, null, sensorTypeIdDouble, sensorModel));
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    @Test
    void constructorNullDeviceID() {
        // Arrange
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        String expected = "DeviceID cannot be null";

        // Act + assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new BinarySwitch(null, nameDouble, idDouble, sensorTypeIdDouble, sensorModel));
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    @Test
    void constructorNullSensorTypeName() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        String expected = "SensorTypeId cannot be null";

        // Act + assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new BinarySwitch(deviceIdDouble, nameDouble, idDouble, null, sensorModel));
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    @Test
    void getName() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        SwitchOnOffActuator switchTest = mock(SwitchOnOffActuator.class);
        BinarySwitch binarySwitch = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        binarySwitch.configureSensor(switchTest);
        String expected = "IsOnSensor";

        // Act
        SensorName result = binarySwitch.getName();

        // Assert
        assertEquals(expected, result.toString());
    }

    @Test
    void getSensorTypeName() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        SwitchOnOffActuator switchTest = mock(SwitchOnOffActuator.class);
        BinarySwitch binarySwitch = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        binarySwitch.configureSensor(switchTest);

        // Act
        SensorTypeId result = binarySwitch.getSensorTypeId();

        // Assert
        assertEquals(sensorTypeIdDouble, result);
    }

    @Test
    void validIdentity() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        BinarySwitch binarySwitch = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Assert
        assertEquals(idDouble, binarySwitch.identity());
    }

    @Test
    void binarySwitchActuatorIsSameAsEqualObject(){
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        BinarySwitch binarySwitch = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        BinarySwitch binarySwitch2 = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = binarySwitch.sameAs(binarySwitch2);

        // Assert
        assertTrue(result);
    }

    @Test
    void  binarySwitchActuatorIsNotSameAsDifferentObject(){
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        BinarySwitch binarySwitch = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        DewPointSensor dewPointSensor = mock(DewPointSensor.class);

        // Act
        boolean result = binarySwitch.sameAs(dewPointSensor);

        // Assert
        assertFalse(result);
    }

    @Test
    void validDeviceId() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        BinarySwitch binarySwitch = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        DeviceID result = binarySwitch.getDeviceID();

        // Assert
        assertEquals(deviceIdDouble, result);
    }

    @Test
    void configureSensorIsNull() {

        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        BinarySwitch binarySwitch = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = binarySwitch.configureSensor(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void shouldConfigureSensor() {

        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        SwitchOnOffActuator switchTest = mock(SwitchOnOffActuator.class);
        BinarySwitch binarySwitch = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = binarySwitch.configureSensor(switchTest);

        // Assert
        assertTrue(result);
    }

    @Test
    void binarySwitchActuatorIsSameAsSameObject() {

        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        BinarySwitch binarySwitch = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = binarySwitch.sameAs(binarySwitch);

        // Assert
        assertTrue(result);
    }

    @Test
    void binarySwitchActuatorIsSameAsDifferentName() {

        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        BinarySwitch binarySwitch = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        SensorName name2 = mock(SensorName.class);
        BinarySwitch binarySwitch2 = new BinarySwitch(deviceIdDouble, name2, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = binarySwitch.sameAs(binarySwitch2);

        // Assert
        assertFalse(result);
    }

    @Test
    void binarySwitchActuatorIsSameAsDifferentId() {

        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        BinarySwitch binarySwitch = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        SensorId id2 = mock(SensorId.class);
        BinarySwitch binarySwitch2 = new BinarySwitch(deviceIdDouble, nameDouble, id2, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = binarySwitch.sameAs(binarySwitch2);

        // Assert
        assertFalse(result);
    }

    @Test
    void binarySwitchActuatorIsSameAsDifferentSensorType() {

        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        BinarySwitch binarySwitch = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        SensorTypeId mockSensorTypeId2 = mock(SensorTypeId.class);
        BinarySwitch binarySwitch2 = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, mockSensorTypeId2, sensorModel);

        // Act
        boolean result = binarySwitch.sameAs(binarySwitch2);

        // Assert
        assertFalse(result);
    }

    @Test
    void binarySwitchActuatorIsSameAsDifferentBinarySwitch() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        SwitchOnOffActuator switchTest1 = mock(SwitchOnOffActuator.class);
        SwitchOnOffActuator switchTest2 = mock(SwitchOnOffActuator.class);
        BinarySwitch binarySwitch1 = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        BinarySwitch binarySwitch2 = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        binarySwitch1.configureSensor(switchTest1);
        binarySwitch2.configureSensor(switchTest2);

        // Act
        boolean result = binarySwitch1.sameAs(binarySwitch2);

        // Assert
        assertFalse(result);
    }

    @Test
    void binarySwitchActuatorIsSameAsSameBinarySwitch() {
        // Arrange
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Binary Switch Sensor");
        SensorName nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("IsOnSensor");
        SensorId idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeName");
        SwitchOnOffActuator switchTest = mock(SwitchOnOffActuator.class);
        BinarySwitch binarySwitch = new BinarySwitch(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        binarySwitch.configureSensor(switchTest);

        // Act
        boolean result = binarySwitch.sameAs(binarySwitch);

        // Assert
        assertTrue(result);
    }

    // Aggregate tests

    @Test
    void isOnAggregate() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        boolean isOn = true;
        SwitchOnOffActuator switchTest = mock(SwitchOnOffActuator.class);
        when(switchTest.isOn()).thenReturn(isOn);
        switchTest.switchActuator();
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);
        binarySwitch.configureSensor(switchTest);

        // Act
        boolean result = binarySwitch.readStatus();

        // Assert
        assertTrue(result);
    }

    @Test
    void isOffAggregate() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        boolean isOn = false;
        SwitchOnOffActuator switchTest = mock(SwitchOnOffActuator.class);
        when(switchTest.isOn()).thenReturn(isOn);
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);
        binarySwitch.configureSensor(switchTest);

        // Act
        boolean result = binarySwitch.readStatus();

        // Assert
        assertFalse(result);
    }

    @Test
    void constructorAggregateNullName() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        String expected = "Name cannot be null or empty";

        // Act + assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new BinarySwitch(deviceId, null, id, sensorTypeId, sensorModel));
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    @Test
    void constructorAggregateNullID() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        String expected = "SensorId cannot be null";

        // Act + assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new BinarySwitch(deviceId, name, null, sensorTypeId, sensorModel));
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    @Test
    void constructorAggregateNullDeviceID() {
        // Arrange
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        String expected = "DeviceID cannot be null";

        // Act + assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new BinarySwitch(null, name, id, sensorTypeId, sensorModel));
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    @Test
    void constructorAggregateNullSensorTypeName() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        String expected = "SensorTypeId cannot be null";

        // Act + assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new BinarySwitch(deviceId, name, id, null, sensorModel));
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    @Test
    void getNameAggregate() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        SwitchOnOffActuator switchTest = mock(SwitchOnOffActuator.class);
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);
        binarySwitch.configureSensor(switchTest);

        // Act
        SensorName result = binarySwitch.getName();

        // Assert
        assertEquals(name, result);
    }

    @Test
    void getSensorTypeNameAggregate() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        SwitchOnOffActuator switchTest = mock(SwitchOnOffActuator.class);
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);
        binarySwitch.configureSensor(switchTest);

        // Act
        SensorTypeId result = binarySwitch.getSensorTypeId();

        // Assert
        assertEquals(sensorTypeId, result);
    }

    @Test
    void validIdentityAggregate() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);

        // Assert
        assertEquals(id, binarySwitch.identity());
    }

    @Test
    void binarySwitchActuatorAggregateIsSameAsEqualObject() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);
        BinarySwitch binarySwitch2 = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        boolean result = binarySwitch.sameAs(binarySwitch2);

        // Assert
        assertTrue(result);
    }

    @Test
    void binarySwitchActuatorAggregateIsNotSameAsDifferentObject() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);
        DewPointSensor dewPointSensor = mock(DewPointSensor.class);

        // Act
        boolean result = binarySwitch.sameAs(dewPointSensor);

        // Assert
        assertFalse(result);
    }

    @Test
    void validDeviceIdAggregate() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        DeviceID result = binarySwitch.getDeviceID();

        // Assert
        assertEquals(deviceId, result);
    }

    @Test
    void configureSensorAggregateIsNull() {

        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        boolean result = binarySwitch.configureSensor(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void shouldConfigureSensorAggregate() {

        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        SwitchOnOffActuator switchTest = mock(SwitchOnOffActuator.class);
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        boolean result = binarySwitch.configureSensor(switchTest);

        // Assert
        assertTrue(result);
    }

    @Test
    void binarySwitchActuatorAggregateIsSameAsSameObject() {

        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        boolean result = binarySwitch.sameAs(binarySwitch);

        // Assert
        assertTrue(result);
    }

    @Test
    void binarySwitchActuatorAggregateIsSameAsDifferentId() {

        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);
        SensorId id2 = new SensorId(2L);
        BinarySwitch binarySwitch2 = new BinarySwitch(deviceId, name, id2, sensorTypeId, sensorModel);

        // Act
        boolean result = binarySwitch.sameAs(binarySwitch2);

        // Assert
        assertFalse(result);
    }

    @Test
    void binarySwitchActuatorAggregateIsSameAsDifferentSensorType() {

        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);
        SensorTypeId sensorTypeId2 = new SensorTypeId("Switch3");
        BinarySwitch binarySwitch2 = new BinarySwitch(deviceId, name, id, sensorTypeId2, sensorModel);

        // Act
        boolean result = binarySwitch.sameAs(binarySwitch2);

        // Assert
        assertFalse(result);
    }

    @Test
    void binarySwitchActuatorAggregateIsSameAsDifferentDeviceId() {

        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);
        DeviceID deviceID2 = new DeviceID(435);
        BinarySwitch binarySwitch2 = new BinarySwitch(deviceID2, name, id, sensorTypeId, sensorModel);

        // Act
        boolean result = binarySwitch.sameAs(binarySwitch2);

        // Assert
        assertFalse(result);
    }

    @Test
    void binarySwitchActuatorAggregateIsSameAsDifferentBinarySwitch() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        SwitchOnOffActuator switchTest1 = mock(SwitchOnOffActuator.class);
        SwitchOnOffActuator switchTest2 = mock(SwitchOnOffActuator.class);
        BinarySwitch binarySwitch1 = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);
        BinarySwitch binarySwitch2 = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);
        binarySwitch1.configureSensor(switchTest1);
        binarySwitch2.configureSensor(switchTest2);

        // Act
        boolean result = binarySwitch1.sameAs(binarySwitch2);

        // Assert
        assertFalse(result);
    }

    @Test
    void binarySwitchActuatorAggregateIsSameAsSameBinarySwitch() {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId id = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        SwitchOnOffActuator switchTest = mock(SwitchOnOffActuator.class);
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, id, sensorTypeId, sensorModel);
        binarySwitch.configureSensor(switchTest);

        // Act
        boolean result = binarySwitch.sameAs(binarySwitch);

        // Assert
        assertTrue(result);
    }

    @Test
    void getSensorModel()
    {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        SensorName name = new SensorName("BinarySwitch");
        SensorId sensorId = new SensorId(1L);
        SensorTypeId sensorTypeId = new SensorTypeId("BinarySwitch");
        BinarySwitch binarySwitch = new BinarySwitch(deviceId, name, sensorId, sensorTypeId, sensorModel);
        SensorModel expected = new SensorModel("BinarySwitch");

        // Act
        SensorModel result = binarySwitch.getSensorModel();

        // Assert
        assertEquals(expected.toString(), result.toString());
    }
}
