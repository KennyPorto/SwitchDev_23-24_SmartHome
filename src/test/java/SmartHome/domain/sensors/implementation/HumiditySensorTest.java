package SmartHome.domain.sensors.implementation;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class HumiditySensorTest {

    private SensorModel sensorModel;

    @BeforeEach
    void setUp() {
        sensorModel = new SensorModel("HumiditySensor");
    }

    @Test
    void validAggregateCreation()
    {
        // Arrange
        DeviceID device = new DeviceID(1);
        SensorName sensorName = new SensorName("Kitchen Scale");
        SensorId sensorID = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("Scale");
        HumiditySensor humiditySensor = new HumiditySensor(device, sensorName, sensorID, sensorTypeId, sensorModel);

        // Act
        SensorName actualSensorName = humiditySensor.getName();

        // Assert
        assertEquals(sensorName, actualSensorName);
    }

    @Test
    void validSensorCreation() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("Kitchen Scale");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("Scale");

        //Act
        HumiditySensor humiditySensor = new HumiditySensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Assert
        assertEquals(name.toString(), humiditySensor.getName().toString());
    }


    @Test
    void invalidConstructorWithNullDeviceId() {
        // Arrange
        SensorName name = new SensorName("Kitchen Scale");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("Scale");

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new HumiditySensor(null, name, id, sensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullName() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("Scale");

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new HumiditySensor(deviceId, null, id, sensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorId() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("Kitchen Scale");
        SensorTypeId sensorTypeId = new SensorTypeId("Scale");

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
        SensorName name = new SensorName("Kitchen Scale");
        SensorId id = new SensorId(1);

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new HumiditySensor(deviceId, name, id, null, sensorModel)
        );

        // Assert
        assertEquals("SensorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void getDeviceIdTest() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("Kitchen Scale");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("Scale");
        HumiditySensor humiditySensor = new HumiditySensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        DeviceID actual = humiditySensor.getDeviceID();

        // Assert
        assertEquals(deviceId, actual);
    }

    @Test
    void getSensorNameTest() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("Kitchen Scale");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("Scale");
        HumiditySensor humiditySensor = new HumiditySensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorName actual = humiditySensor.getName();

        // Assert
        assertEquals(name, actual);
    }

    @Test
    void getSensorIdTest() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("Kitchen Scale");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("Scale");
        HumiditySensor humiditySensor = new HumiditySensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorId actual = humiditySensor.identity();

        // Assert
        assertEquals(id, actual);
    }

    @Test
    void getSensorTypeIdTest() {
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("Kitchen Scale");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("Scale");
        HumiditySensor humiditySensor = new HumiditySensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        SensorTypeId actual = humiditySensor.getSensorTypeId();

        // Assert
        assertEquals(sensorTypeId, actual);
    }

    @Test
    void dewPointSensorIsSameAsEqualObject(){
        // Arrange
        DeviceID deviceId = new DeviceID(1);
        SensorName name = new SensorName("Kitchen Scale");
        SensorId id = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("Scale");
        HumiditySensor humiditySensor = new HumiditySensor(deviceId, name, id, sensorTypeId, sensorModel);
        HumiditySensor humiditySensor2 = new HumiditySensor(deviceId, name, id, sensorTypeId, sensorModel);

        // Act
        boolean result = humiditySensor.sameAs(humiditySensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void dewPointSensorIsSameAsSameObjectWithIsolation(){
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        DewPointSensor dewPointSensor = new DewPointSensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = dewPointSensor.sameAs(dewPointSensor);

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
        HumiditySensor humiditySensor = new HumiditySensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Assert
        assertEquals(nameDouble.toString(), humiditySensor.getName().toString());
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
                () -> new HumiditySensor(null, nameDouble, idDouble, sensorTypeIdDouble, sensorModel)
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
                () -> new HumiditySensor(deviceIdDouble, null, idDouble, sensorTypeIdDouble, sensorModel)
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
                () -> new HumiditySensor(deviceIdDouble, nameDouble, null, sensorTypeIdDouble, sensorModel)
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
                () -> new HumiditySensor(deviceIdDouble, nameDouble, idDouble, null, sensorModel)
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
        HumiditySensor humiditySensor = new HumiditySensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        DeviceID actual = humiditySensor.getDeviceID();

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
        HumiditySensor humiditySensor = new HumiditySensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        SensorName actual = humiditySensor.getName();

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
        HumiditySensor humiditySensor = new HumiditySensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        SensorId actual = humiditySensor.identity();

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
        HumiditySensor humiditySensor = new HumiditySensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        SensorTypeId actual = humiditySensor.getSensorTypeId();

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
        HumiditySensor humiditySensor = new HumiditySensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        HumiditySensor humiditySensor2 = new HumiditySensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = humiditySensor.sameAs(humiditySensor2);

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
        HumiditySensor sensor1 = new HumiditySensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);
        HumiditySensor sensor2 = new HumiditySensor(deviceId2Double, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = sensor1.sameAs(sensor2);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_ReturnsFalse_ForNullObjectWithIsolation() {
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        HumiditySensor sensor = new HumiditySensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = sensor.sameAs(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAsSameObjectWithIsolation(){
        // Arrange
        SensorName nameDouble = mock(SensorName.class);
        SensorId idDouble = mock(SensorId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        HumiditySensor humiditySensor = new HumiditySensor(deviceIdDouble, nameDouble, idDouble, sensorTypeIdDouble, sensorModel);

        // Act
        boolean result = humiditySensor.sameAs(humiditySensor);

        // Assert
        assertTrue(result);
    }

    @Test
    void getSensorModel()
    {
        // Arrange
        SensorName sensorName = new SensorName("Humidity Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");

        // Act
        HumiditySensor humiditySensor = new HumiditySensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        String result = humiditySensor.getSensorModel().model;

        String expected = "HumiditySensor";

        // Assert
        assertEquals(expected, result);
    }

}
