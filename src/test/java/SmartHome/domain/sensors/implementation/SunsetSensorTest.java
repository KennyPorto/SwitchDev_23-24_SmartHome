package SmartHome.domain.sensors.implementation;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SunsetSensorTest {
    private final SensorName mockSensorName = mock(SensorName.class);
    private SensorId mockSensorID = mock(SensorId.class);
    private SensorTypeId mockSensorTypeId;

    private DeviceID deviceID = mock(DeviceID.class);

    private SensorName sensorName;
    private SensorId sensorId;
    private SensorTypeId sensorTypeId;
    private SensorModel sensorModel;


    @BeforeEach
    void setUp() {
        sensorModel = new SensorModel("SunsetSensor");
        when(mockSensorName.toString()).thenReturn("Sunset Sensor");
        mockSensorID = mock(SensorId.class);
        when(mockSensorID.toString()).thenReturn("12345");
        mockSensorTypeId = mock(SensorTypeId.class);
        when(mockSensorTypeId.toString()).thenReturn("SensorTypeName");
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Sunset Sensor");

    }

    @Test
    void invalidConstructorWithNullName() {
        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new SunsetSensor(deviceID, null, mockSensorID, mockSensorTypeId, sensorModel));

        // Assert
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorID() {
        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new SunsetSensor(deviceID, mockSensorName, null, mockSensorTypeId, sensorModel));

        // Assert
        assertEquals("SensorId cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullDeviceID() {
        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new SunsetSensor(null, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel));

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorTypeName() {
        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new SunsetSensor(deviceID, mockSensorName, mockSensorID, null, sensorModel));

        // Assert
        assertEquals("SensorTypeId cannot be null", exception.getMessage());
    }


    @Test
    void getName_withInsulation() {
        // Arrange
        SensorModel mockSensorModel = mock(SensorModel.class);
        when(mockSensorModel.toString()).thenReturn("Sunset Sensor");
        SunsetSensor sensor = new SunsetSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        SensorName name = sensor.getName();

        // Assert
        assertEquals(mockSensorName, name);
    }

    @Test
    void getName() {
        // Arrange
        deviceID = new DeviceID(1);
        sensorName = new SensorName("Sunset Sensor");
        sensorId = new SensorId(1);
        sensorTypeId = new SensorTypeId("sunrise");
        SunsetSensor sensor = new SunsetSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);

        // Act
        SensorName name = sensor.getName();

        // Assert
        assertEquals(sensorName, name);
    }


    @Test
    void validDeviceId_withInsulation() {
        // Arrange
        SunsetSensor sunsetSensor = new SunsetSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        DeviceID result = sunsetSensor.getDeviceID();

        // Assert
        assertEquals(deviceID, result);
    }

    @Test
    void validDeviceId() {
        // Arrange
        deviceID = new DeviceID(1);
        sensorName = new SensorName("Sunset Sensor");
        sensorId = new SensorId(1);
        sensorTypeId = new SensorTypeId("sunrise");
        SunsetSensor sunsetSensor = new SunsetSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);

        // Act
        DeviceID result = sunsetSensor.getDeviceID();

        // Assert
        assertEquals(deviceID, result);
    }


    @Test
    void checkIdentity_withInsulation() {
        // Arrange
        SensorId expectedSensorId = mock(SensorId.class);
        SunsetSensor sunsetSensor = new SunsetSensor(deviceID, mockSensorName, expectedSensorId, mockSensorTypeId, sensorModel);

        // Act
        SensorId actualSensorId = sunsetSensor.identity();

        // Assert
        assertEquals(expectedSensorId, actualSensorId);
    }

    @Test
    void checkIdentity() {
        // Arrange
        deviceID = new DeviceID(1);
        sensorName = new SensorName("Sunset Sensor");
        sensorId = new SensorId(1);
        sensorTypeId = new SensorTypeId("sunrise");
        SunsetSensor sunsetSensor = new SunsetSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);

        // Act
        SensorId actualSensorId = sunsetSensor.identity();

        // Assert
        assertEquals(sensorId, actualSensorId);
    }


    @Test
    void checkSensorTypeName_withInsulation() {
        // Arrange
        SunsetSensor sunsetSensor = new SunsetSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        SensorTypeId actualSensorTypeId = sunsetSensor.getSensorTypeId();

        // Assert
        assertEquals(mockSensorTypeId, actualSensorTypeId);
    }

    @Test
    void checkSensorTypeName() {
        // Arrange
        deviceID = new DeviceID(1);
        sensorName = new SensorName("Sunset Sensor");
        sensorId = new SensorId(1);
        sensorTypeId = new SensorTypeId("sunrise");
        SunsetSensor sunsetSensor = new SunsetSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);

        // Act
        SensorTypeId actualSensorTypeId = sunsetSensor.getSensorTypeId();

        // Assert
        assertEquals(sensorTypeId, actualSensorTypeId);
    }

    @Test
    void sameAs_ReturnsTrue_ForSameObjects_withInsulation() {
        // Arrange
        SunsetSensor sensor1 = new SunsetSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        SunsetSensor sensor2 = sensor1;

        // Act
        boolean result = sensor1.sameAs(sensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_ReturnsTrue_ForSameObjects() {
        // Arrange
        deviceID = new DeviceID(1);
        sensorName = new SensorName("Sunset Sensor");
        sensorId = new SensorId(1);
        sensorTypeId = new SensorTypeId("sunrise");
        SunsetSensor sensor = new SunsetSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);

        // Act
        boolean result = sensor.sameAs(sensor);

        // Assert
        assertTrue(result);
    }


    @Test
    void sameAs_ReturnsTrue_ForEqualObjects_withInsulation() {
        // Arrange
        SunsetSensor sensor1 = new SunsetSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        SunsetSensor sensor2 = new SunsetSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        boolean result = sensor1.sameAs(sensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_ReturnsTrue_ForEqualObjects() {
        // Arrange
        deviceID = new DeviceID(1);
        sensorName = new SensorName("Sunset Sensor");
        sensorId = new SensorId(1);
        sensorTypeId = new SensorTypeId("sunrise");
        SunsetSensor sensor1 = new SunsetSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        SunsetSensor sensor2 = new SunsetSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);

        // Act
        boolean result = sensor1.sameAs(sensor2);

        // Assert
        assertTrue(result);
    }


    @Test
    void sameAs_ReturnsFalse_ForDifferentClasses_withInsulation() {
        // Arrange
        SunsetSensor sensor = new SunsetSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        Object otherObject = new Object();

        // Act
        boolean result = sensor.sameAs(otherObject);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_ReturnsFalse_ForDifferentClasses() {
        // Arrange
        deviceID = new DeviceID(1);
        sensorName = new SensorName("Sunset Sensor");
        sensorId = new SensorId(1);
        sensorTypeId = new SensorTypeId("sunrise");
        SunsetSensor sensor = new SunsetSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        Object otherObject = new Object();

        // Act
        boolean result = sensor.sameAs(otherObject);

        // Assert
        assertFalse(result);
    }


    @Test
    void sameAs_ReturnsFalse_ForDifferentValues_withInsulation() {
        // Arrange
        SunsetSensor sensor1 = new SunsetSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        SunsetSensor sensor2 = new SunsetSensor(deviceID, mockSensorName, mockSensorID, new SensorTypeId("DifferentSensorType"), sensorModel);

        // Act
        boolean result = sensor1.sameAs(sensor2);

        // Assert
        assertFalse(result);
    }

    @Test
    void getSensorModel()
    {
        // Arrange
        SensorName sensorName = new SensorName("Sunset Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");

        // Act
        SunsetSensor sunsetSensor = new SunsetSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        String result = sunsetSensor.getSensorModel().model;

        String expected = "SunsetSensor";

        // Assert
        assertEquals(expected, result);
    }

}
