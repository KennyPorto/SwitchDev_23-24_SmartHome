package SmartHome.domain.sensors.implementation;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TemperatureSensorTest
{
    private SensorModel sensorModel;

    @BeforeEach
    void setUp() {
        sensorModel = new SensorModel("TemperatureSensor");
    }

    @Test
    void validSensorCreation() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");

        //Act
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Assert
        assertEquals(name.toString(), temperatureSensor.getName().toString());
    }


    @Test
    void invalidConstructorWithNullDeviceId() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new TemperatureSensor(null, name, id, sensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullName() {
        // Arrange
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new TemperatureSensor(deviceId, null, id, sensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorId() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new TemperatureSensor(deviceId, name, null, sensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("SensorId cannot be null", exception.getMessage());
    }



    @Test
    void invalidConstructorWithNullSensorTypeId() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new TemperatureSensor(deviceId, name, id, null, sensorModel)
        );

        // Assert
        assertEquals("SensorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void getDeviceIdTest() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        DeviceID actual = temperatureSensor.getDeviceID();

        // Assert
        assertEquals(deviceId, actual);
    }

    @Test
    void getSensorNameTest() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorName actual = temperatureSensor.getName();

        // Assert
        assertEquals(name, actual);
    }

    @Test
    void getSensorIdTest() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = temperatureSensor.identity();

        // Assert
        assertEquals(id, actual);
    }

    @Test
    void getSensorTypeIdTest() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorTypeId actual = temperatureSensor.getSensorTypeId();

        // Assert
        assertEquals(sensorTypeId, actual);
    }



    @Test
    void dewPointSensorIsSameAsEqualObject(){
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);
        TemperatureSensor temperatureSensor2 = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        boolean result = temperatureSensor.sameAs(temperatureSensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void validSensorCreationWithIsolation() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        when(nameDouble.toString()).thenReturn("SensorName");

        //Act
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Assert
        assertEquals(nameDouble.toString(), temperatureSensor.getName().toString());
    }


    @Test
    void invalidConstructorWithNullDeviceIdWithIsolation() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new TemperatureSensor(null, nameDouble, idDouble, sensorTypeIdDouble, sensorModel)
        );

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullNameWithIsolation() {
        // Arrange
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new TemperatureSensor(deviceIdDouble, null, idDouble, sensorTypeIdDouble, sensorModel)
        );

        // Assert
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorIdWithIsolation() {

        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new TemperatureSensor(deviceIdDouble, nameDouble, null, sensorTypeIdDouble, sensorModel)
        );

        // Assert
        assertEquals("SensorId cannot be null", exception.getMessage());
    }



    @Test
    void invalidConstructorWithNullSensorTypeIdWithIsolation() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new TemperatureSensor(deviceIdDouble, nameDouble, idDouble, null, sensorModel)
        );

        // Assert
        assertEquals("SensorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void getDeviceIdTestWithIsolation() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        DeviceID actual = temperatureSensor.getDeviceID();

        // Assert
        assertEquals(deviceIdDouble, actual);
    }

    @Test
    void getSensorNameTestWithIsolation() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        SensorName actual = temperatureSensor.getName();

        // Assert
        assertEquals(nameDouble, actual);
    }

    @Test
    void getSensorIdTestWithIsolation() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        SensorId actual = temperatureSensor.identity();

        // Assert
        assertEquals(idDouble, actual);
    }

    @Test
    void getSensorTypeIdTestWithIsolation() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        SensorTypeId actual = temperatureSensor.getSensorTypeId();

        // Assert
        assertEquals(sensorTypeIdDouble, actual);
    }



    @Test
    void dewPointSensorIsSameAsEqualObjectWithIsolation(){
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        TemperatureSensor temperatureSensor2 = new TemperatureSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = temperatureSensor.sameAs(temperatureSensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_ReturnsFalse_ForDifferentValuesWithIsolation() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        DeviceID deviceId2Double = mock(DeviceID.class);
        TemperatureSensor sensor1 = new TemperatureSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        TemperatureSensor sensor2 = new TemperatureSensor(deviceId2Double, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = sensor1.sameAs(sensor2);

        // Assert
        assertFalse(result);
    }



    @Test
    void sameAs_ReturnsTrue_WhenSameObject() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = temperatureSensor.sameAs(temperatureSensor);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_ReturnsFalse_WhenNull() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = temperatureSensor.sameAs(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_ReturnsFalse_WhenDifferentClass() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = temperatureSensor.sameAs(new Object());

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_ReturnsTrue_WhenEqualTemperatureSensor() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        TemperatureSensor temperatureSensor1 = new TemperatureSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        TemperatureSensor temperatureSensor2 = new TemperatureSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = temperatureSensor1.sameAs(temperatureSensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_ReturnsFalse_WhenNotEqualTemperatureSensor() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        TemperatureSensor temperatureSensor1 = new TemperatureSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        TemperatureSensor temperatureSensor2 = new TemperatureSensor(deviceIdDouble, new SensorName("DifferentName"), idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = temperatureSensor1.sameAs(temperatureSensor2);

        // Assert
        assertFalse(result);
    }

    // unit test to aggregate Root

    @Test
    void sameAs_ReturnsFalse_WhenSensorIdIsDifferent() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorId id1 = new SensorId(1);
        SensorId id2 = new SensorId(2);
        TemperatureSensor temperatureSensor1 = new TemperatureSensor(deviceIdDouble, nameDouble, id1, sensorTypeIdDouble, sensorModel);
        TemperatureSensor temperatureSensor2 = new TemperatureSensor(deviceIdDouble, nameDouble, id2, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = temperatureSensor1.sameAs(temperatureSensor2);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_ReturnsFalse_WhenDeviceIdIsDifferent() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        DeviceID deviceId1 = new DeviceID(1);
        DeviceID deviceId2 = new DeviceID(2);
        TemperatureSensor temperatureSensor1 = new TemperatureSensor(deviceId1, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        TemperatureSensor temperatureSensor2 = new TemperatureSensor(deviceId2, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = temperatureSensor1.sameAs(temperatureSensor2);

        // Assert
        assertFalse(result);
    }

    @Test
    void testToAggregateRoot() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = temperatureSensor.identity();

        // Assert
        assertEquals(id, actual);
    }
    @Test
    void testToAggregateRootWithInvalidValues() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = temperatureSensor.identity();

        // Assert
        assertNotEquals(new SensorId(2), actual);
    }
    @Test
    void testAggregateRootWithoutValues() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = temperatureSensor.identity();

        // Assert
        assertNotNull(actual);
    }
    @Test
    void testAggregateRootWithNullValues() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = temperatureSensor.identity();

        // Assert
        assertNotNull(actual);
    }
    @Test
    void testAggregateRootWithSameValues() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = temperatureSensor.identity();

        // Assert
        assertEquals(id, actual);
    }
    @Test
    void testAggregateRootWithDifferentValues() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = temperatureSensor.identity();

        // Assert
        assertNotEquals(new SensorId(2), actual);
    }
    @Test
    void testAggregateRootWithSameValuesAndSameObject() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = temperatureSensor.identity();

        // Assert
        assertEquals(id, actual);
    }
    @Test
    void testAggregateRootWithDifferentValuesAndSameObject() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = temperatureSensor.identity();

        // Assert
        assertNotEquals(new SensorId(2), actual);
    }
    @Test
    void testAggregateRootWithSameValuesAndDifferentObject() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = temperatureSensor.identity();

        // Assert
        assertEquals(id, actual);
    }
    @Test
    void testAggregateRootWithDifferentValuesAndDifferentObject() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = temperatureSensor.identity();

        // Assert
        assertNotEquals(new SensorId(2), actual);
    }
    @Test
    void testAggregateRootWithSameValuesAndNullObject() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = temperatureSensor.identity();

        // Assert
        assertEquals(id, actual);
    }
    @Test
    void testAggregateRootWithDifferentValuesAndNullObject() {
        // Arrange
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        DeviceID deviceId = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = temperatureSensor.identity();

        // Assert
        assertNotEquals(new SensorId(2), actual);
    }

    @Test
    void getSensorModel()
    {
        // Arrange
        SensorName sensorName = new SensorName("TemperatureSensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");

        // Act
        TemperatureSensor temperatureSensor = new TemperatureSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        String result = temperatureSensor.getSensorModel().model;

        String expected = "TemperatureSensor";

        // Assert
        assertEquals(expected, result);
    }
}