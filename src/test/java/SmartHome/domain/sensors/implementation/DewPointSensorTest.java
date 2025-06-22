package SmartHome.domain.sensors.implementation;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DewPointSensorTest {
    private DeviceID deviceIdDouble;
    private SensorName nameDouble;
    private SensorId idDouble;
    private SensorTypeId sensorTypeIdDouble;
    private SensorModel sensorModel;

    @BeforeEach
    void setUp() {
        sensorModel = new SensorModel("DewPointSensor");
        deviceIdDouble = mock(DeviceID.class);
        when(deviceIdDouble.toString()).thenReturn("123");

        nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("SensorName");

        idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("12345");

        sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeId");
    }

    @Test
    void validSensorCreation() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("Dew Point Sensor");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");

        //Act
        DewPointSensor dewPointSensor = new DewPointSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Assert
        assertEquals(name.toString(), dewPointSensor.getName().toString());
    }

    @Test
    void invalidConstructorWithNullDeviceId() {
        // Arrange
        SensorName name = new SensorName("Dew Point Sensor");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new DewPointSensor(null, name, id, sensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullName() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new DewPointSensor(deviceId, null, id, sensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorId() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("Dew Point Sensor");
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new DewPointSensor(deviceId, name, null, sensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("SensorId cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorTypeId() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("Dew Point Sensor");
        SensorId id = new SensorId(1);

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new DewPointSensor(deviceId, name, id, null, sensorModel)
        );

        // Assert
        assertEquals("SensorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void getDeviceIdTest() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("Dew Point Sensor");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        DewPointSensor dewPointSensor = new DewPointSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        DeviceID actual = dewPointSensor.getDeviceID();

        // Assert
        assertEquals(deviceId, actual);
    }

    @Test
    void getSensorNameTest() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("Dew Point Sensor");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        DewPointSensor dewPointSensor = new DewPointSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorName actual = dewPointSensor.getName();

        // Assert
        assertEquals(name, actual);
    }

    @Test
    void getSensorIdTest() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("Dew Point Sensor");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        DewPointSensor dewPointSensor = new DewPointSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = dewPointSensor.identity();

        // Assert
        assertEquals(id, actual);
    }

    @Test
    void getSensorTypeIdTest() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("Dew Point Sensor");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        DewPointSensor dewPointSensor = new DewPointSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorTypeId actual = dewPointSensor.getSensorTypeId();

        // Assert
        assertEquals(sensorTypeId, actual);
    }

    @Test
    void dewPointSensorIsSameAsEqualObject() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("Dew Point Sensor");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        DewPointSensor dewPointSensor = new DewPointSensor(deviceId, name, id, sensorTypeId, sensorModel);
        DewPointSensor dewPointSensor2 = new DewPointSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        boolean result = dewPointSensor.sameAs(dewPointSensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void validSensorCreationWithIsolation() {
        // Arrange
        when(nameDouble.toString()).thenReturn("SensorName");
        //Act
        DewPointSensor dewPointSensor = new DewPointSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Assert
        assertEquals(nameDouble.toString(), dewPointSensor.getName().toString());
    }

    @Test
    void invalidConstructorWithNullDeviceIdWithIsolation() {
        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new DewPointSensor(null, nameDouble, idDouble, sensorTypeIdDouble, sensorModel)
        );

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullNameWithIsolation() {
        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new DewPointSensor(deviceIdDouble, null, idDouble, sensorTypeIdDouble, sensorModel)
        );

        // Assert
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorIdWithIsolation() {
        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new DewPointSensor(deviceIdDouble, nameDouble, null, sensorTypeIdDouble, sensorModel)
        );

        // Assert
        assertEquals("SensorId cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorTypeIdWithIsolation() {
        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new DewPointSensor(deviceIdDouble, nameDouble, idDouble, null, sensorModel)
        );

        // Assert
        assertEquals("SensorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void getDeviceIdTestWithIsolation() {
        // Arrange
        DewPointSensor dewPointSensor = new DewPointSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        DeviceID actual = dewPointSensor.getDeviceID();

        // Assert
        assertEquals(deviceIdDouble, actual);
    }

    @Test
    void getSensorNameTestWithIsolation() {
        // Arrange
        DewPointSensor dewPointSensor = new DewPointSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        SensorName actual = dewPointSensor.getName();

        // Assert
        assertEquals(nameDouble, actual);
    }

    @Test
    void getSensorIdTestWithIsolation() {
        // Arrange
        DewPointSensor dewPointSensor = new DewPointSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        SensorId actual = dewPointSensor.identity();

        // Assert
        assertEquals(idDouble, actual);
    }

    @Test
    void getSensorTypeIdTestWithIsolation() {
        // Arrange
        DewPointSensor dewPointSensor = new DewPointSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        SensorTypeId actual = dewPointSensor.getSensorTypeId();

        // Assert
        assertEquals(sensorTypeIdDouble, actual);
    }

    @Test
    void dewPointSensorIsSameAsEqualObjectWithIsolation() {
        // Arrange
        DewPointSensor dewPointSensor = new DewPointSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        DewPointSensor dewPointSensor2 = new DewPointSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = dewPointSensor.sameAs(dewPointSensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void dewPointSensorIsSameAsSameObjectWithIsolation() {
        // Arrange
        DewPointSensor dewPointSensor = new DewPointSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = dewPointSensor.sameAs(dewPointSensor);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_ReturnsFalse_ForDifferentValuesWithIsolation() {
        // Arrange
        DeviceID deviceId2Double = mock(DeviceID.class);
        DewPointSensor sensor1 = new DewPointSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        DewPointSensor sensor2 = new DewPointSensor(deviceId2Double, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = sensor1.sameAs(sensor2);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_ReturnsFalse_ForNullObjectWithIsolation() {
        // Arrange
        DewPointSensor sensor1 = new DewPointSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = sensor1.sameAs(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void getSensorModel() {
        SensorName sensorName = new SensorName("Dew Point Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");

        DewPointSensor dewPointSensor = new DewPointSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        String result = dewPointSensor.getSensorModel().model;

        String expected = "DewPointSensor";

        assertEquals(expected, result);
    }
}
