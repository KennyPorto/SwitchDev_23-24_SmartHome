package SmartHome.domain.sensors.implementation;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InstantPowerConsumptionSensorTest
{
    private final SensorName mockSensorName = mock(SensorName.class);
    private final SensorId mockSensorID = mock(SensorId.class);
    private final DeviceID mockedDeviceID = mock(DeviceID.class);
    private final SensorTypeId mockSensorTypeId = mock(SensorTypeId.class);
    private SensorModel sensorModel;


    @BeforeEach
    void setUp()
    {
        sensorModel = new SensorModel("InstantPowerConsumptionSensor");
        when(mockedDeviceID.toString()).thenReturn("DeviceID");
        when(mockSensorName.toString()).thenReturn("Instant Power Consumption Sensor");
        when(mockSensorID.toString()).thenReturn("12345");
        when(mockSensorTypeId.toString()).thenReturn("SensorTypeName");
    }

    @Test
    void invalidConstructorWithNullDeviceID()
    {
        // Arrange

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new InstantPowerConsumptionSensor(null, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel));

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorName()
    {
        // Arrange

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new InstantPowerConsumptionSensor(mockedDeviceID, null, mockSensorID, mockSensorTypeId, sensorModel));

        // Assert
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorID()
    {
        // Arrange

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new InstantPowerConsumptionSensor(mockedDeviceID, mockSensorName, null, mockSensorTypeId, sensorModel));

        // Assert
        assertEquals("SensorId cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorTypeName()
    {
        // Arrange

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new InstantPowerConsumptionSensor(mockedDeviceID, mockSensorName, mockSensorID, null, sensorModel));

        // Assert
        assertEquals("SensorTypeId cannot be null", exception.getMessage());
    }


    @Test
    void getName()
    {
        // Arrange
        InstantPowerConsumptionSensor instantPowerConsumptionSensor =
                new InstantPowerConsumptionSensor(mockedDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act + Assert
        assertEquals(mockSensorName, instantPowerConsumptionSensor.getName());
    }

    @Test
    void getIdentity()
    {
        // Arrange
        InstantPowerConsumptionSensor instantPowerConsumptionSensor =
                new InstantPowerConsumptionSensor(mockedDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act + Assert
        assertEquals(mockSensorID, instantPowerConsumptionSensor.identity());
    }

    @Test
    void rangeSensorIsSameAsEqualObject()
    {
        // Arrange
        InstantPowerConsumptionSensor instantPowerConsumptionSensor = new InstantPowerConsumptionSensor(mockedDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        InstantPowerConsumptionSensor instantPowerConsumptionSensor2 = new InstantPowerConsumptionSensor(mockedDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        boolean result = instantPowerConsumptionSensor.sameAs(instantPowerConsumptionSensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void rangeSensorIsNotSameAsDifferentObject()
    {
        // Arrange
        InstantPowerConsumptionSensor instantPowerConsumptionSensor = new InstantPowerConsumptionSensor(mockedDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        DewPointSensor dewPointSensor = mock(DewPointSensor.class);

        // Act
        boolean result = instantPowerConsumptionSensor.sameAs(dewPointSensor);

        // Assert
        assertFalse(result);
    }

    @Test
    void validDeviceID()
    {
        // Arrange
        InstantPowerConsumptionSensor instantPowerConsumptionSensor = new InstantPowerConsumptionSensor(mockedDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        DeviceID result = instantPowerConsumptionSensor.getDeviceID();

        // Assert
        assertEquals(mockedDeviceID, result);
    }

    @Test
    void validSensorTypeName()
    {
        // Arrange
        InstantPowerConsumptionSensor instantPowerConsumptionSensor = new InstantPowerConsumptionSensor(mockedDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        SensorTypeId result = instantPowerConsumptionSensor.getSensorTypeId();

        // Assert
        assertEquals(mockSensorTypeId, result);
    }

    @Test
    void sameAsReturnsTrueWhenObjectIsSameInstance()
    {
        // Arrange
        InstantPowerConsumptionSensor instantPowerConsumptionSensor = new InstantPowerConsumptionSensor(mockedDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        boolean result = instantPowerConsumptionSensor.sameAs(instantPowerConsumptionSensor);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAsReturnsFalseWhenObjectsAreNotEqual()
    {
        // Arrange
        InstantPowerConsumptionSensor instantPowerConsumptionSensor1 = new InstantPowerConsumptionSensor(mockedDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        SensorName differentSensorName = mock(SensorName.class);
        when(differentSensorName.toString()).thenReturn("Different Sensor Name");
        InstantPowerConsumptionSensor instantPowerConsumptionSensor2 = new InstantPowerConsumptionSensor(mockedDeviceID, differentSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        boolean result = instantPowerConsumptionSensor1.sameAs(instantPowerConsumptionSensor2);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAsReturnsFalseWhenObjectIsNull()
    {
        // Arrange
        InstantPowerConsumptionSensor instantPowerConsumptionSensor = new InstantPowerConsumptionSensor(mockedDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        boolean result = instantPowerConsumptionSensor.sameAs(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void instantPowerConsumptionSensorAggregateValidName()
    {
        // Arrange
        DeviceID deviceID = new DeviceID(1);
        SensorName sensorName = new SensorName("Instant Power Consumption Sensor");
        SensorId sensorID = new SensorId(12345);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        InstantPowerConsumptionSensor instantPowerConsumptionSensor = new InstantPowerConsumptionSensor(deviceID, sensorName, sensorID, sensorTypeId, sensorModel);

        // Act
        SensorName result = instantPowerConsumptionSensor.getName();

        // Assert
        assertEquals(sensorName, result);
    }

    @Test
    void getSensorModel()
    {
        // Arrange
        DeviceID deviceID = new DeviceID(1);
        SensorName sensorName = new SensorName("Instant Power Consumption Sensor");
        SensorId sensorID = new SensorId(12345);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        InstantPowerConsumptionSensor instantPowerConsumptionSensor = new InstantPowerConsumptionSensor(deviceID, sensorName, sensorID, sensorTypeId, sensorModel);
        SensorModel expected = new SensorModel("InstantPowerConsumptionSensor");

        // Act
        SensorModel result = instantPowerConsumptionSensor.getSensorModel();

        // Assert
        assertEquals(expected, result);
    }
}
