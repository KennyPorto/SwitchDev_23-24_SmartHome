package SmartHome.domain.sensors.implementation;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SunriseSensorTest
{
    private SensorName mockSensorName = mock(SensorName.class);
    private SensorId mockSensorID = mock(SensorId.class);
    private SensorTypeId mockSensorTypeId;
    private DeviceID deviceID;
    private SunriseSensor sunriseSensor;
    private SensorName sensorName;
    private SensorId sensorId;
    private SensorTypeId sensorTypeId;
    private SensorModel sensorModel;

    @BeforeEach
    void setUp()
    {
        sensorModel = new SensorModel("SunriseSensor");
        deviceID = mock(DeviceID.class);
        mockSensorName = mock(SensorName.class);
        when(mockSensorName.toString()).thenReturn("Sunrise Sensor");
        mockSensorTypeId = mock(SensorTypeId.class);
        when(mockSensorTypeId.toString()).thenReturn("SensorTypeName");
        mockSensorID = mock(SensorId.class);
        when(mockSensorID.toString()).thenReturn("12345");
    }

    @Test
    void invalidConstructorWithNullName()
    {
        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new SunriseSensor(deviceID, null, mockSensorID, mockSensorTypeId, sensorModel));

        // Assert
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorID()
    {
        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new SunriseSensor(deviceID, mockSensorName, null, mockSensorTypeId, sensorModel));

        // Assert
        assertEquals("SensorId cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullDeviceID()
    {
        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new SunriseSensor(null, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel));

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorTypeName()
    {
        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new SunriseSensor(deviceID, mockSensorName, mockSensorID, null, sensorModel));

        // Assert
        assertEquals("SensorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void getName_withInsulation()
    {
        // Arrange
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Sunrise Sensor");
        SunriseSensor sensor = new SunriseSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        SensorName name = sensor.getName();

        // Assert
        assertEquals(mockSensorName, name);
    }

    @Test
    void getName()
    {
        // Arrange
        deviceID = new DeviceID(1);
        sensorName = new SensorName("Sunrise Sensor");
        sensorId = new SensorId(1);
        sensorTypeId = new SensorTypeId("sunrise");
        sunriseSensor = new SunriseSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        SunriseSensor sensor = new SunriseSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);

        // Act
        SensorName name = sensor.getName();

        // Assert
        assertEquals(sensorName, name);
    }

    @Test
    void validDeviceId_withInsulation()
    {
        // Arrange
        SunriseSensor sunriseSensor = new SunriseSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        DeviceID result = sunriseSensor.getDeviceID();

        // Assert
        assertEquals(deviceID, result);
    }

    @Test
    void validDeviceId()
    {
        // Arrange
        deviceID = new DeviceID(1);
        sensorName = new SensorName("Sunrise Sensor");
        sensorId = new SensorId(1);
        sensorTypeId = new SensorTypeId("sunrise");
        sunriseSensor = new SunriseSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        SunriseSensor sunriseSensor = new SunriseSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);

        // Act
        DeviceID result = sunriseSensor.getDeviceID();

        // Assert
        assertEquals(deviceID, result);
    }

    @Test
    void checkIdentity_withInsulation()
    {
        // Arrange
        SensorId expectedSensorId = mock(SensorId.class);
        SunriseSensor sunriseSensor = new SunriseSensor(deviceID, mockSensorName, expectedSensorId, mockSensorTypeId, sensorModel);

        // Act
        SensorId actualSensorId = sunriseSensor.identity();

        // Assert
        assertEquals(expectedSensorId, actualSensorId);
    }

    @Test
    void checkIdentity()
    {
        // Arrange & Act
        deviceID = new DeviceID(1);
        sensorName = new SensorName("Sunrise Sensor");
        sensorId = new SensorId(1);
        sensorTypeId = new SensorTypeId("sunrise");
        sunriseSensor = new SunriseSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        SensorId actualSensorId = sunriseSensor.identity();

        // Assert
        assertEquals(sensorId, actualSensorId);
    }

    @Test
    void checkSensorTypeName_withInsulation()
    {
        // Arrange
        SunriseSensor sunriseSensor = new SunriseSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        SensorTypeId actualSensorTypeId = sunriseSensor.getSensorTypeId();

        // Assert
        assertEquals(mockSensorTypeId, actualSensorTypeId);
    }

    @Test
    void checkSensorTypeName()
    {
        // Arrange & Act
        deviceID = new DeviceID(1);
        sensorName = new SensorName("Sunrise Sensor");
        sensorId = new SensorId(1);
        sensorTypeId = new SensorTypeId("sunrise");
        sunriseSensor = new SunriseSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        SensorTypeId actualSensorTypeId = sunriseSensor.getSensorTypeId();

        // Assert
        assertEquals(sensorTypeId, actualSensorTypeId);
    }

    @Test
    void sameAs_ReturnsTrue_ForSameObjects_withInsulation()
    {
        // Arrange
        SunriseSensor sensor1 = new SunriseSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        SunriseSensor sensor2 = sensor1;

        // Act
        boolean result = sensor1.sameAs(sensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_ReturnsTrue_ForSameObjects()
    {
        // Arrange
        deviceID = new DeviceID(1);
        sensorName = new SensorName("Sunrise Sensor");
        sensorId = new SensorId(1);
        sensorTypeId = new SensorTypeId("sunrise");
        sunriseSensor = new SunriseSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        SunriseSensor sensor1 = new SunriseSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        SunriseSensor sensor2 = new SunriseSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);

        // Act
        boolean result = sensor1.sameAs(sensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_ReturnsTrue_ForEqualObjects_withInsulation()
    {
        // Arrange
        SunriseSensor sensor1 = new SunriseSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        SunriseSensor sensor2 = new SunriseSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        boolean result = sensor1.sameAs(sensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_ReturnsTrue_ForEqualObjects()
    {
        // Arrange
        deviceID = new DeviceID(1);
        sensorName = new SensorName("Sunrise Sensor");
        sensorId = new SensorId(1);
        sensorTypeId = new SensorTypeId("sunrise");
        sunriseSensor = new SunriseSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        SunriseSensor sensor1 = new SunriseSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        SunriseSensor sensor2 = new SunriseSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);

        // Act
        boolean result = sensor1.sameAs(sensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_ReturnsFalse_ForDifferentClasses_withInsulation()
    {
        // Arrange
        SunriseSensor sensor = new SunriseSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        Object otherObject = new Object();

        // Act
        boolean result = sensor.sameAs(otherObject);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_ReturnsFalse_ForDifferentClasses()
    {
        // Arrange
        deviceID = new DeviceID(1);
        sensorName = new SensorName("Sunrise Sensor");
        sensorId = new SensorId(1);
        sensorTypeId = new SensorTypeId("sunrise");
        sunriseSensor = new SunriseSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        SunriseSensor sensor = new SunriseSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        Object otherObject = new Object();

        // Act
        boolean result = sensor.sameAs(otherObject);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_ReturnsFalse_ForDifferentValues_withInsulation()
    {
        // Arrange
        SunriseSensor sensor1 = new SunriseSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        SunriseSensor sensor2 = new SunriseSensor(deviceID, mockSensorName, mockSensorID, new SensorTypeId("DifferentSensorType"), sensorModel);

        // Act
        boolean result = sensor1.sameAs(sensor2);

        // Assert
        assertFalse(result);
    }

    @Test
    void getSensorModel()
    {
        // Arrange
        SensorName sensorName = new SensorName("Sunrise Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");

        // Act
        SunriseSensor sunriseSensor = new SunriseSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        String result = sunriseSensor.getSensorModel().model;

        String expected = "SunriseSensor";

        // Assert
        assertEquals(expected, result);
    }
}