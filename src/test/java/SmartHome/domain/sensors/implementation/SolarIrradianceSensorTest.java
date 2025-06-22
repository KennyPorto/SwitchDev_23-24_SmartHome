package SmartHome.domain.sensors.implementation;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SolarIrradianceSensorTest
{

    private SensorName nameDouble;
    private SensorId idDouble;
    private DeviceID deviceIdDouble;
    private SensorTypeId sensorTypeIdDouble;
    private SensorModel sensorModel;

    @BeforeEach
    void setUp() {
        sensorModel = new SensorModel("SolarIrradianceSensor");
        nameDouble = mock(SensorName.class);
        when(nameDouble.toString()).thenReturn("SensorName");

        idDouble = mock(SensorId.class);
        when(idDouble.toString()).thenReturn("123");

        deviceIdDouble = mock(DeviceID.class);
        when(deviceIdDouble.toString()).thenReturn("1234");

        sensorTypeIdDouble = mock(SensorTypeId.class);
        when(sensorTypeIdDouble.toString()).thenReturn("SensorTypeId");
    }

    @Test
    void validSensorCreation() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");

        //Act
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Assert
        assertEquals(name.toString(), solarIrradianceSensor.getName().toString());
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
                () -> new SolarIrradianceSensor(null, name, id, sensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullName() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new SolarIrradianceSensor(deviceId, null, id, sensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorId() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("SensorName");
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");


        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new SolarIrradianceSensor(deviceId, name, null, sensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("SensorId cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorTypeId() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new SolarIrradianceSensor(deviceId, name, id, null, sensorModel)
        );

        // Assert
        assertEquals("SensorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void getDeviceIdTest() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");

        // Arrange
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        DeviceID actual = solarIrradianceSensor.getDeviceID();

        // Assert
        assertEquals(deviceId, actual);
    }

    @Test
    void getSensorNameTest() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");

        // Arrange
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorName actual = solarIrradianceSensor.getName();

        // Assert
        assertEquals(name, actual);
    }

    @Test
    void getSensorIdTest() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");

        // Arrange
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = solarIrradianceSensor.identity();

        // Assert
        assertEquals(id, actual);
    }

    @Test
    void getSensorTypeIdTest() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");

        // Arrange
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorTypeId actual = solarIrradianceSensor.getSensorTypeId();

        // Assert
        assertEquals(sensorTypeId, actual);
    }

    @Test
    void dewPointSensorIsSameAsEqualObject(){
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("SensorName");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeId");

        // Arrange
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceId, name, id, sensorTypeId, sensorModel);
        SolarIrradianceSensor solarIrradianceSensor2 = new SolarIrradianceSensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        boolean result = solarIrradianceSensor.sameAs(solarIrradianceSensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void validSensorCreationWithIsolation() {
        // Arrange
        when(nameDouble.toString()).thenReturn("SensorName");
        //Act
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Assert
        assertEquals(nameDouble.toString(), solarIrradianceSensor.getName().toString());
    }

    @Test
    void invalidConstructorWithNullDeviceIdWithIsolation() {
        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new SolarIrradianceSensor(null, nameDouble, idDouble, sensorTypeIdDouble, sensorModel)
        );

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullNameWithIsolation() {
        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new SolarIrradianceSensor(deviceIdDouble, null, idDouble, sensorTypeIdDouble, sensorModel)
        );

        // Assert
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorIdWithIsolation() {
        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new SolarIrradianceSensor(deviceIdDouble, nameDouble, null, sensorTypeIdDouble, sensorModel)
        );

        // Assert
        assertEquals("SensorId cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorTypeIdWithIsolation() {
        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new SolarIrradianceSensor(deviceIdDouble, nameDouble, idDouble, null, sensorModel)
        );

        // Assert
        assertEquals("SensorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void getDeviceIdTestWithIsolation() {
        // Arrange
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        DeviceID actual = solarIrradianceSensor.getDeviceID();

        // Assert
        assertEquals(deviceIdDouble, actual);
    }

    @Test
    void getSensorNameTestWithIsolation() {
        // Arrange
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        SensorName actual = solarIrradianceSensor.getName();

        // Assert
        assertEquals(nameDouble, actual);
    }

    @Test
    void getSensorIdTestWithIsolation() {
        // Arrange
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        SensorId actual = solarIrradianceSensor.identity();

        // Assert
        assertEquals(idDouble, actual);
    }

    @Test
    void getSensorTypeIdTestWithIsolation() {
        // Arrange
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        SensorTypeId actual = solarIrradianceSensor.getSensorTypeId();

        // Assert
        assertEquals(sensorTypeIdDouble, actual);
    }

    @Test
    void dewPointSensorIsSameAsEqualObjectWithIsolation(){
        // Arrange
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        SolarIrradianceSensor solarIrradianceSensor2 = new SolarIrradianceSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = solarIrradianceSensor.sameAs(solarIrradianceSensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_ReturnsFalse_ForDifferentValuesWithIsolation() {
        // Arrange
        DeviceID deviceId2Double = mock(DeviceID.class);
        SolarIrradianceSensor sensor1 = new SolarIrradianceSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        SolarIrradianceSensor sensor2 = new SolarIrradianceSensor(deviceId2Double, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = sensor1.sameAs(sensor2);

        // Assert
        assertFalse(result);
    }

    @Test
    void getSensorModel()
    {
        // Arrange
        SensorName sensorName = new SensorName("Solar Irradiance Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");

        // Act
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        String result = solarIrradianceSensor.getSensorModel().model;

        String expected = "SolarIrradianceSensor";

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void sameAs_nullObject_integration()
    {
        // Arrange
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceIdDouble, nameDouble, idDouble,
                sensorTypeIdDouble, sensorModel);
        // Act
        boolean result = solarIrradianceSensor.sameAs(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_sameObject_integration()
    {
        // Arrange
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceIdDouble, nameDouble, idDouble,
                sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = solarIrradianceSensor.sameAs(solarIrradianceSensor);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_DifferentName_integration()
    {
        // Arrange
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceIdDouble, nameDouble, idDouble,
                sensorTypeIdDouble, sensorModel);
        SolarIrradianceSensor solarIrradianceSensor1 =
                new SolarIrradianceSensor(deviceIdDouble, new SensorName("new S1 name"), idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = solarIrradianceSensor.sameAs(solarIrradianceSensor1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentId_integration()
    {
        // Arrange
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceIdDouble, nameDouble, idDouble,
                sensorTypeIdDouble, sensorModel);
        SolarIrradianceSensor solarIrradianceSensor1 =
                new SolarIrradianceSensor(deviceIdDouble, nameDouble, new SensorId(2L), sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = solarIrradianceSensor.sameAs(solarIrradianceSensor1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentActuatorTypeId_integration()
    {
        // Arrange
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(deviceIdDouble, nameDouble, idDouble,
                sensorTypeIdDouble, sensorModel);
        SolarIrradianceSensor solarIrradianceSensor1 =
                new SolarIrradianceSensor(deviceIdDouble, nameDouble, idDouble, new SensorTypeId("d"), sensorModel);

        // Act
        boolean result = solarIrradianceSensor.sameAs(solarIrradianceSensor1);

        // Assert
        assertFalse(result);
    }
}