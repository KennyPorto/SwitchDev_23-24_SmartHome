package SmartHome.domain.sensors.implementation;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScaleSensorTest {
    private ScaleSensor scaleSensorMock;
    private SensorName mockSensorName = mock(SensorName.class);
    private SensorId mockSensorID = mock(SensorId.class);
    private DeviceID mockDeviceID;
    private SensorTypeId mockSensorTypeId;
    private SensorModel sensorModel;

    @BeforeEach
    void setUp() {
        sensorModel = new SensorModel("ScaleSensor");
        mockSensorName = mock(SensorName.class);
        when(mockSensorName.toString()).thenReturn("Scale Sensor");

        mockSensorID = mock(SensorId.class);
        when(mockSensorID.toString()).thenReturn("12345");

        mockSensorTypeId = mock(SensorTypeId.class);
        when(mockSensorTypeId.toString()).thenReturn("SensorTypeName");

        mockDeviceID = mock(DeviceID.class);
        when(mockDeviceID.toString()).thenReturn("2");

        scaleSensorMock = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
    }

    @Test
    void validAggregateCreation()
    {
        // Arrange
        DeviceID device = new DeviceID(1);
        SensorName sensorName = new SensorName("Kitchen Scale");
        SensorId sensorID = new SensorId(1);
        SensorTypeId sensorTypeId = new SensorTypeId("Scale");
        ScaleSensor scaleSensor = new ScaleSensor(device, sensorName, sensorID, sensorTypeId, sensorModel);

        // Act
        SensorName actualSensorName = scaleSensor.getName();

        // Assert
        assertEquals(sensorName, actualSensorName);
    }

    @Test
    void validSensorCreation() {
        // Arrange

        //Act
        scaleSensorMock = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Assert
        assertEquals(mockSensorName.toString(), scaleSensorMock.getName().toString());
    }

    @Test
    void invalidConstructorWithNullDeviceID() {
        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ScaleSensor(null, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel));

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorID() {
        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ScaleSensor(mockDeviceID, mockSensorName, null, mockSensorTypeId, sensorModel));

        // Assert
        assertEquals("SensorId cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorName() {
        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ScaleSensor(mockDeviceID, null, mockSensorID, mockSensorTypeId, sensorModel));

        // Assert
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorTypeName() {
        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, null, sensorModel));

        // Assert
        assertEquals("SensorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void getName() {
        // Arrange
        ScaleSensor scaleSensor = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        SensorName name = scaleSensor.getName();

        // Assert
        assertEquals("Scale Sensor", name.toString());
    }

    @Test
    void getSensorTypeName() {
        // Arrange
        ScaleSensor scaleSensor = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        SensorTypeId name = scaleSensor.getSensorTypeId();

        // Assert
        assertEquals("SensorTypeName", name.toString());
    }

    @Test
    void getDeviceId() {
        // Arrange
        ScaleSensor scaleSensor = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        DeviceID deviceId = scaleSensor.getDeviceID();

        // Assert
        assertEquals("2", deviceId.toString());
    }

    @Test
    void scaleSensorIsSameAsEqualObject(){
        // Arrange
        ScaleSensor scaleSensor = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        ScaleSensor scaleSensor2 = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        boolean result = scaleSensor.sameAs(scaleSensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void scaleSensorIsSameAsSameObject(){
        // Arrange
        ScaleSensor scaleSensor = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        boolean result = scaleSensor.sameAs(scaleSensor);

        // Assert
        assertTrue(result);
    }

    @Test
    void  scaleSensorWithDifferentDeviceIdReturnsFalse(){
        // Arrange
        ScaleSensor scaleSensor = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        DeviceID mockDeviceID2 = mock(DeviceID.class);
        ScaleSensor scaleSensor2 = new ScaleSensor(mockDeviceID2, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        boolean result = scaleSensor.sameAs(scaleSensor2);

        // Assert
        assertFalse(result);
    }

    @Test
    void  scaleSensorWithDifferentSensorNameReturnsFalse(){
        // Arrange
        ScaleSensor scaleSensor = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        SensorName mockSensorName2 = mock(SensorName.class);
        ScaleSensor scaleSensor2 = new ScaleSensor(mockDeviceID, mockSensorName2, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        boolean result = scaleSensor.sameAs(scaleSensor2);

        // Assert
        assertFalse(result);
    }

    @Test
    void  scaleSensorWithDifferentSensorIDReturnsFalse(){
        // Arrange
        ScaleSensor scaleSensor = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        SensorId mockSensorID2 = mock(SensorId.class);
        ScaleSensor scaleSensor2 = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID2, mockSensorTypeId, sensorModel);

        // Act
        boolean result = scaleSensor.sameAs(scaleSensor2);

        // Assert
        assertFalse(result);
    }

    @Test
    void  scaleSensorWithDifferentSensorTypeIdReturnsFalse(){
        // Arrange
        ScaleSensor scaleSensor = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        SensorTypeId mockSensorTypeId2 = mock(SensorTypeId.class);
        ScaleSensor scaleSensor2 = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId2, sensorModel);

        // Act
        boolean result = scaleSensor.sameAs(scaleSensor2);

        // Assert
        assertFalse(result);
    }

    @Test
    void  rangeSensorIsNotSameAsDifferentObject(){
        // Arrange
        ScaleSensor scaleSensor = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        DewPointSensor dewPointSensor = mock(DewPointSensor.class);

        // Act
        boolean result = scaleSensor.sameAs(dewPointSensor);

        // Assert
        assertFalse(result);
    }

    @Test
    void validDeviceId() {
        // Arrange
        ScaleSensor scaleSensor = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        DeviceID result = scaleSensor.getDeviceID();

        // Assert
        assertEquals(mockDeviceID, result);
    }

    @Test
    void sameIdentity() {
        // Arrange
        ScaleSensor scaleSensor = new ScaleSensor(mockDeviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        SensorId result = scaleSensor.identity();

        // Assert
        assertEquals(mockSensorID, result);
    }

    @Test
    void getSensorModel()
    {
        // Arrange
        SensorName sensorName = new SensorName("Scale Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");

        // Act
        ScaleSensor scaleSensor = new ScaleSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        String result = scaleSensor.getSensorModel().model;

        String expected = "ScaleSensor";

        // Assert
        assertEquals(expected, result);
    }
}
