package SmartHome.domain.sensors.implementation;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WindSensorTest {

    private final SensorName mockSensorName = mock(SensorName.class);
    private final SensorId mockSensorId = mock(SensorId.class);
    private final DeviceID mockDeviceId = mock(DeviceID.class);
    private final SensorTypeId mockSensorTypeId = mock(SensorTypeId.class);
    private SensorModel sensorModel;

    @BeforeEach
    void setUp() {
        sensorModel = new SensorModel("WindSensor");
        when(mockSensorName.toString()).thenReturn("Wind Sensor");
        when(mockDeviceId.toString()).thenReturn("DeviceID");
        when(mockSensorId.toString()).thenReturn("12345");
        when(mockSensorTypeId.toString()).thenReturn("SensorTypeName");
    }

    @Test
    void invalidConstructorWithNullName() {
        // Arrange & Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new WindSensor(mockDeviceId, null, mockSensorId, mockSensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorID() {
        // Arrange & Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new WindSensor(mockDeviceId, mockSensorName, null, mockSensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("SensorId cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullDeviceID() {
        // Arrange & Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new WindSensor(null, mockSensorName, mockSensorId, mockSensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorTypeName() {
        // Arrange & Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new WindSensor(mockDeviceId, mockSensorName, mockSensorId, null, sensorModel)
        );

        // Assert
        assertEquals("SensorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void getName() {
        // Arrange
        WindSensor windSensor = new WindSensor(mockDeviceId, mockSensorName, mockSensorId, mockSensorTypeId, sensorModel);

        // Act
        SensorName name = windSensor.getName();

        // Assert
        assertEquals(mockSensorName, name);
    }

    @Test
    void getSensorTypeName() {
        // Arrange
        WindSensor windSensor = new WindSensor(mockDeviceId, mockSensorName, mockSensorId, mockSensorTypeId, sensorModel);

        // Act
        SensorTypeId sensorTypeId = windSensor.getSensorTypeId();

        // Assert
        assertEquals(mockSensorTypeId, sensorTypeId);
    }

    @Test
    void rangeSensorIsSameAsEqualObject() {
        // Arrange
        WindSensor windSensor = new WindSensor(mockDeviceId, mockSensorName, mockSensorId, mockSensorTypeId, sensorModel);
        WindSensor windSensor2 = new WindSensor(mockDeviceId, mockSensorName, mockSensorId, mockSensorTypeId, sensorModel);

        // Act
        boolean result = windSensor.sameAs(windSensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void rangeSensorIsNotSameAsDifferentObject() {
        // Arrange
        WindSensor windSensor = new WindSensor(mockDeviceId, mockSensorName, mockSensorId, mockSensorTypeId, sensorModel);
        DewPointSensor dewPointSensor = mock(DewPointSensor.class);

        // Act
        boolean result = windSensor.sameAs(dewPointSensor);

        // Assert
        assertFalse(result);
    }

    @Test
    void validDeviceId() {
        // Arrange
        WindSensor windSensor = new WindSensor(mockDeviceId, mockSensorName, mockSensorId, mockSensorTypeId, sensorModel);

        // Act
        DeviceID result = windSensor.getDeviceID();

        // Assert
        assertEquals(mockDeviceId, result);
    }

    @Test
    void sameAsWithEqualObject() {
        // Arrange
        WindSensor windSensor = new WindSensor(mockDeviceId, mockSensorName, mockSensorId, mockSensorTypeId, sensorModel);
        WindSensor windSensor2 = new WindSensor(mockDeviceId, mockSensorName, mockSensorId, mockSensorTypeId, sensorModel);

        // Act
        boolean result = windSensor.sameAs(windSensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAsWithDifferentObject() {
        // Arrange
        WindSensor windSensor = new WindSensor(mockDeviceId, mockSensorName, mockSensorId, mockSensorTypeId, sensorModel);
        DewPointSensor dewPointSensor = mock(DewPointSensor.class);

        // Act
        boolean result = windSensor.sameAs(dewPointSensor);

        // Assert
        assertFalse(result);
    }

    @Test
    void sensorIdentity() {
        // Arrange
        WindSensor windSensor = new WindSensor(mockDeviceId, mockSensorName, mockSensorId, mockSensorTypeId, sensorModel);

        // Act
        SensorId result = windSensor.identity();

        // Assert
        assertEquals(mockSensorId, result);
    }

    @Test
    void sameAsReturnsTrueWhenObjectIsSameInstance() {
        // Arrange
        WindSensor windSensor = new WindSensor(mockDeviceId, mockSensorName, mockSensorId, mockSensorTypeId, sensorModel);

        // Act
        boolean result = windSensor.sameAs(windSensor);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAsReturnsFalseWhenObjectsAreNotEqual() {
        // Arrange
        WindSensor windSensor = new WindSensor(mockDeviceId, mockSensorName, mockSensorId, mockSensorTypeId, sensorModel);
        SensorName differentSensorName = mock(SensorName.class);
        when(differentSensorName.toString()).thenReturn("Different Sensor Name");
        WindSensor windSensor1 = new WindSensor(mockDeviceId, differentSensorName, mockSensorId, mockSensorTypeId, sensorModel);

        // Act
        boolean result = windSensor.sameAs(windSensor1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAsReturnsFalseWhenObjectIsNull() {
        // Arrange
        WindSensor windSensor = new WindSensor(mockDeviceId, mockSensorName, mockSensorId, mockSensorTypeId, sensorModel);

        // Act
        boolean result = windSensor.sameAs(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void validAggregateCreation()
    {
        // Arrange
        DeviceID device = new DeviceID(1);
        SensorName sensorName = new SensorName("WindSensor");
        SensorId sensorID = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("WindS");
        WindSensor windSensor = new WindSensor(device, sensorName, sensorID, sensorTypeId, sensorModel);

        // Act
        SensorName actualSensorName = windSensor.getName();

        // Assert
        assertEquals(sensorName, actualSensorName);
    }

    @Test
    void getSensorModel()
    {
        // Arrange
        SensorName sensorName = new SensorName("WindSensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");

        // Act
        WindSensor windSensor = new WindSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        String result = windSensor.getSensorModel().model;

        String expected = "WindSensor";

        // Assert
        assertEquals(expected, result);
    }
}
