package SmartHome.domain.sensors.implementation;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ElectricEnergyConsumptionSensorTest
{
    private ElectricEnergyConsumptionSensor electricEnergyConsumptionSensorMock;
    private SensorName mockSensorName = mock(SensorName.class);
    private SensorId mockSensorID = mock(SensorId.class);
    private DeviceID deviceID;
    private SensorTypeId mockSensorTypeId;
    private SensorModel sensorModel;

    @BeforeEach
    void setUp()
    {
        sensorModel = new SensorModel("ElectricEnergyConsumptionSensor");
        deviceID = mock(DeviceID.class);

        mockSensorName = mock(SensorName.class);
        when(mockSensorName.toString()).thenReturn("Electric Consumption Sensor");

        mockSensorID = mock(SensorId.class);
        when(mockSensorID.toString()).thenReturn("12345");

        mockSensorTypeId = mock(SensorTypeId.class);
        when(mockSensorTypeId.toString()).thenReturn("SensorTypeName");

        electricEnergyConsumptionSensorMock = new ElectricEnergyConsumptionSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
    }

    @Test
    void validSensorCreation()
    {
        // Arrange

        // Act
        electricEnergyConsumptionSensorMock = new ElectricEnergyConsumptionSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Assert
        assertEquals(mockSensorName.toString(), electricEnergyConsumptionSensorMock.getName().toString());
    }

    @Test
    void invalidConstructorWithNullSensorName()
    {
        // Arrange

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ElectricEnergyConsumptionSensor(deviceID, null, mockSensorID, mockSensorTypeId, sensorModel));

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
                () -> new ElectricEnergyConsumptionSensor(deviceID, mockSensorName, null, mockSensorTypeId, sensorModel));

        // Assert
        assertEquals("SensorId cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullDeviceID()
    {
        // Arrange

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ElectricEnergyConsumptionSensor(null, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel));

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorTypeName()
    {
        // Arrange

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ElectricEnergyConsumptionSensor(deviceID, mockSensorName, mockSensorID, null, sensorModel));

        // Assert
        assertEquals("SensorTypeId cannot be null", exception.getMessage());
    }



    @Test
    void validGetSensorName()
    {
        // Arrange
        electricEnergyConsumptionSensorMock = new ElectricEnergyConsumptionSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        SensorName result = electricEnergyConsumptionSensorMock.getName();

        // Assert
        assertEquals(mockSensorName, result);
    }

    @Test
    void sensorHasSameID()
    {
        // Arrange
        ElectricEnergyConsumptionSensor electricEnergyConsumptionSensor = new ElectricEnergyConsumptionSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        SensorId result = electricEnergyConsumptionSensor.identity();

        // Assert
        assertEquals(mockSensorID, result);
    }

    @Test
    void rangeSensorIsSameAsEqualObject()
    {
        // Arrange
        ElectricEnergyConsumptionSensor electricEnergyConsumptionSensor = new ElectricEnergyConsumptionSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        ElectricEnergyConsumptionSensor electricEnergyConsumptionSensor2 = new ElectricEnergyConsumptionSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        boolean result = electricEnergyConsumptionSensor.sameAs(electricEnergyConsumptionSensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void rangeSensorIsNotSameAsDifferentObject()
    {
        // Arrange
        ElectricEnergyConsumptionSensor electricEnergyConsumptionSensor = new ElectricEnergyConsumptionSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        DewPointSensor dewPointSensor = mock(DewPointSensor.class);

        // Act
        boolean result = electricEnergyConsumptionSensor.sameAs(dewPointSensor);

        // Assert
        assertFalse(result);
    }

    @Test
    void validDeviceId()
    {
        // Arrange
        ElectricEnergyConsumptionSensor electricEnergyConsumptionSensor = new ElectricEnergyConsumptionSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        DeviceID result = electricEnergyConsumptionSensor.getDeviceID();

        // Assert
        assertEquals(deviceID, result);
    }

    @Test
    void validSensorTypeName()
    {
        // Arrange
        ElectricEnergyConsumptionSensor electricEnergyConsumptionSensor = new ElectricEnergyConsumptionSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        SensorTypeId result = electricEnergyConsumptionSensor.getSensorTypeId();

        // Assert
        assertEquals(mockSensorTypeId, result);
    }

    @Test
    void sameAs_SameObject()
    {
        // Arrange
        ElectricEnergyConsumptionSensor consumptionSensor = new ElectricEnergyConsumptionSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        boolean result = consumptionSensor.sameAs(consumptionSensor);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_EqualName()
    {
        // Arrange
        ElectricEnergyConsumptionSensor consumptionSensor1 = new ElectricEnergyConsumptionSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        ElectricEnergyConsumptionSensor consumptionSensor2 = new ElectricEnergyConsumptionSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        boolean result = consumptionSensor1.sameAs(consumptionSensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_DifferentName()
    {
        // Arrange
        SensorName differentName = mock(SensorName.class);
        when(differentName.toString()).thenReturn("Different Name");
        ElectricEnergyConsumptionSensor consumptionSensor1 = new ElectricEnergyConsumptionSensor(deviceID, mockSensorName, mockSensorID, mockSensorTypeId, sensorModel);
        ElectricEnergyConsumptionSensor consumptionSensor2 = new ElectricEnergyConsumptionSensor(deviceID, differentName, mockSensorID, mockSensorTypeId, sensorModel);

        // Act
        boolean result = consumptionSensor1.sameAs(consumptionSensor2);

        // Assert
        assertFalse(result);
    }

    //Aggregate

    @Test
    void electricEnergyConsumptionValidName_Aggregate()
    {
        //Arrange
        SensorName sensorName = new SensorName("Electric Consumption Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        ElectricEnergyConsumptionSensor electricEnergyConsumptionSensor = new ElectricEnergyConsumptionSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        String expected = sensorName.toString();

        //Act
        String result = electricEnergyConsumptionSensor.getName().toString();

        //Arrange
        assertEquals(expected, result);
    }

    @Test
    void electricEnergyConsumptionValidSensorId_Aggregate()
    {
        //Arrange
        SensorName sensorName = new SensorName("Electric Consumption Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        ElectricEnergyConsumptionSensor electricEnergyConsumptionSensor = new ElectricEnergyConsumptionSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        long expected = sensorId.id;

        //Act
        long result = electricEnergyConsumptionSensor.identity().id;

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void electricEnergyConsumptionValidDeviceId_Aggregate()
    {
        //Arrange
        SensorName sensorName = new SensorName("Electric Consumption Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        ElectricEnergyConsumptionSensor electricEnergyConsumptionSensor = new ElectricEnergyConsumptionSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        long expected = deviceID.id;

        //Act
        long result = electricEnergyConsumptionSensor.getDeviceID().id;

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void electricEnergyConsumptionSensorValidSensorTypeId_Aggregate()
    {
        //Arrange
        SensorName sensorName = new SensorName("Electric Consumption Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        ElectricEnergyConsumptionSensor electricEnergyConsumptionSensor = new ElectricEnergyConsumptionSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        String expected = sensorTypeId.id;

        //Act
        String result = electricEnergyConsumptionSensor.getSensorTypeId().id;

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void getSensorModel()
    {
        // Arrange
        SensorName sensorName = new SensorName("Electric Energy Consumption Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");

        // Act
        ElectricEnergyConsumptionSensor electricEnergyConsumptionSensor = new ElectricEnergyConsumptionSensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);
        String result = electricEnergyConsumptionSensor.getSensorModel().model;

        String expected = "ElectricEnergyConsumptionSensor";

        // Assert
        assertEquals(expected, result);
    }
}
