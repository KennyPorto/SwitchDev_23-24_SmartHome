package SmartHome.domain.sensors.implementation;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AveragePowerConsumptionSensorTest
{
    private AveragePowerConsumptionSensor averagePowerConsumptionSensorMock;
    private SensorName mockSensorName;
    private SensorId id;
    private DeviceID deviceID;
    private SensorTypeId mockSensorTypeId;
    private SensorModel sensorModel;

    @BeforeEach
    void setUp()
    {
        sensorModel = new SensorModel("AveragePowerConsumptionSensor");
        deviceID = mock(DeviceID.class);
        when(deviceID.toString()).thenReturn("1");

        mockSensorName = mock(SensorName.class);
        when(mockSensorName.toString()).thenReturn("Average Power Consumption Sensor");

        id = mock(SensorId.class);
        when(id.toString()).thenReturn("12345");

        mockSensorTypeId = mock(SensorTypeId.class);
        when(mockSensorTypeId.toString()).thenReturn("SensorTypeName");

        averagePowerConsumptionSensorMock = new AveragePowerConsumptionSensor(deviceID, mockSensorName, id, mockSensorTypeId,
                sensorModel);

    }

    @Test
    void validSensorCreation()
    {
        //Arrange

        //Act
        averagePowerConsumptionSensorMock = new AveragePowerConsumptionSensor(deviceID, mockSensorName, id,
                mockSensorTypeId, sensorModel);

        //Assert
        assertEquals(mockSensorName.toString(), averagePowerConsumptionSensorMock.getName().toString());
    }

    @Test
    void invalidConstructorWithNullSensorID()
    {
        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new AveragePowerConsumptionSensor(deviceID, mockSensorName, null,
                        mockSensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("SensorId cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullDeviceID()
    {
        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new AveragePowerConsumptionSensor(null, mockSensorName, id,
                        mockSensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullName()
    {
        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new AveragePowerConsumptionSensor(deviceID, null, id, mockSensorTypeId, sensorModel)
        );

        // Assert
        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    void invalidConstructorWithNullSensorTypeName()
    {
        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new AveragePowerConsumptionSensor(deviceID, mockSensorName, id, null, sensorModel)
        );

        // Assert
        assertEquals("SensorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void validGetName()
    {
        //Act
        SensorName result = averagePowerConsumptionSensorMock.getName();

        //Assert
        assertEquals(mockSensorName, result);
    }

    @Test
    void validGetSensorTypeName()
    {
        //Act
        SensorTypeId result = averagePowerConsumptionSensorMock.getSensorTypeId();

        //Assert
        assertEquals(mockSensorTypeId, result);
    }

    @Test
    void validIdentity()
    {
        // Arrange
        AveragePowerConsumptionSensor consumptionSensor = new AveragePowerConsumptionSensor(deviceID, mockSensorName,
                id, mockSensorTypeId, sensorModel);

        // Assert
        assertEquals(id, consumptionSensor.identity());
    }

    @Test
    void averagePowerConsumptionActuatorIsSameAsEqualObject()
    {
        // Arrange
        AveragePowerConsumptionSensor consumptionSensor = new AveragePowerConsumptionSensor(deviceID, mockSensorName,
                id, mockSensorTypeId, sensorModel);
        AveragePowerConsumptionSensor consumptionSensor2 = new AveragePowerConsumptionSensor(deviceID, mockSensorName,
                id, mockSensorTypeId, sensorModel);

        // Act
        boolean result = consumptionSensor.sameAs(consumptionSensor2);

        // Assert
        assertTrue(result);
    }

    @Test
    void averagePowerConsumptionActuatorIsNotSameAsDifferentObject()
    {
        // Arrange
        AveragePowerConsumptionSensor consumptionSensor = new AveragePowerConsumptionSensor(deviceID, mockSensorName,
                id, mockSensorTypeId, sensorModel);
        DewPointSensor dewPointSensor = mock(DewPointSensor.class);

        // Act
        boolean result = consumptionSensor.sameAs(dewPointSensor);

        // Assert
        assertFalse(result);
    }

    @Test
    void validDeviceId()
    {
        // Arrange
        AveragePowerConsumptionSensor consumptionSensor = new AveragePowerConsumptionSensor(deviceID, mockSensorName,
                id, mockSensorTypeId, sensorModel);

        // Act
        DeviceID result = consumptionSensor.getDeviceID();

        // Assert
        assertEquals(deviceID, result);
    }

    @Test
    void sameAs_SameObject()
    {
        // Arrange
        AveragePowerConsumptionSensor consumptionSensor = new AveragePowerConsumptionSensor(deviceID, mockSensorName,
                id, mockSensorTypeId, sensorModel);

        // Act
        boolean result = consumptionSensor.sameAs(consumptionSensor);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_DifferentName()
    {
        // Arrange
        SensorName differentName = mock(SensorName.class);
        when(differentName.toString()).thenReturn("Different Name");
        AveragePowerConsumptionSensor consumptionSensor1 = new AveragePowerConsumptionSensor(deviceID, mockSensorName,
                id, mockSensorTypeId, sensorModel);
        AveragePowerConsumptionSensor consumptionSensor2 = new AveragePowerConsumptionSensor(deviceID, differentName,
                id, mockSensorTypeId, sensorModel);

        // Act
        boolean result = consumptionSensor1.sameAs(consumptionSensor2);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentSensorId()
    {
        // Arrange
        SensorId differentId = mock(SensorId.class);
        when(differentId.toString()).thenReturn("67890");
        AveragePowerConsumptionSensor consumptionSensor1 = new AveragePowerConsumptionSensor(deviceID, mockSensorName,
                id, mockSensorTypeId, sensorModel);
        AveragePowerConsumptionSensor consumptionSensor2 = new AveragePowerConsumptionSensor(deviceID, mockSensorName,
                differentId, mockSensorTypeId, sensorModel);

        // Act
        boolean result = consumptionSensor1.sameAs(consumptionSensor2);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentSensorTypeId()
    {
        // Arrange
        SensorTypeId differentSensorTypeId = mock(SensorTypeId.class);
        when(differentSensorTypeId.toString()).thenReturn("Different Sensor Type");
        AveragePowerConsumptionSensor consumptionSensor1 = new AveragePowerConsumptionSensor(deviceID, mockSensorName,
                id, mockSensorTypeId, sensorModel);
        AveragePowerConsumptionSensor consumptionSensor2 = new AveragePowerConsumptionSensor(deviceID, mockSensorName,
                id, differentSensorTypeId, sensorModel);

        // Act
        boolean result = consumptionSensor1.sameAs(consumptionSensor2);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentDeviceId()
    {
        // Arrange
        DeviceID differentDeviceId = mock(DeviceID.class);
        AveragePowerConsumptionSensor consumptionSensor1 = new AveragePowerConsumptionSensor(deviceID, mockSensorName,
                id, mockSensorTypeId, sensorModel);
        AveragePowerConsumptionSensor consumptionSensor2 = new AveragePowerConsumptionSensor(differentDeviceId,
                mockSensorName, id, mockSensorTypeId, sensorModel);

        // Act
        boolean result = consumptionSensor1.sameAs(consumptionSensor2);

        // Assert
        assertFalse(result);
    }


    //Aggregate Tests
    @Test
    void averagePowerConsumptionValidName_Aggregate()
    {
        //Arrange
        SensorName sensorName = new SensorName("Average Power Consumption Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        AveragePowerConsumptionSensor averagePowerConsumptionSensor = new AveragePowerConsumptionSensor(deviceID,
                sensorName, sensorId, sensorTypeId, sensorModel);
        String expected = sensorName.toString();

        //Act
        String result = averagePowerConsumptionSensor.getName().toString();

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void averagePowerConsumptionSensorId_Aggregate()
    {
        //Arrange
        SensorName sensorName = new SensorName("Average Power Consumption Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        AveragePowerConsumptionSensor averagePowerConsumptionSensor = new AveragePowerConsumptionSensor(deviceID,
                sensorName, sensorId, sensorTypeId, sensorModel);
        long expected = sensorId.id;

        //Act
        long result = averagePowerConsumptionSensor.identity().id;

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void averagePowerConsumptionDeviceId_Aggregate()
    {
        //Arrange
        SensorName sensorName = new SensorName("Average Power Consumption Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        AveragePowerConsumptionSensor averagePowerConsumptionSensor = new AveragePowerConsumptionSensor(deviceID,
                sensorName, sensorId, sensorTypeId, sensorModel);
        long expected = deviceID.id;

        //Act
        long result = averagePowerConsumptionSensor.getDeviceID().id;

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void averagePowerConsumptionSensorTypeId_Aggregate()
    {
        //Arrange
        SensorName sensorName = new SensorName("Average Power Consumption Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");
        AveragePowerConsumptionSensor averagePowerConsumptionSensor = new AveragePowerConsumptionSensor(deviceID,
                sensorName, sensorId, sensorTypeId, sensorModel);
        String expected = sensorTypeId.id;

        //Act
        String result = averagePowerConsumptionSensor.getSensorTypeId().id;

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void getSensorModel()
    {
        SensorName sensorName = new SensorName("Average Power Consumption Sensor");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(2);
        SensorTypeId sensorTypeId = new SensorTypeId("SensorTypeName");

        AveragePowerConsumptionSensor averagePowerConsumptionSensor = new AveragePowerConsumptionSensor(deviceID,
                sensorName, sensorId, sensorTypeId, sensorModel);
        String result = averagePowerConsumptionSensor.getSensorModel().model;

        String expected = "AveragePowerConsumptionSensor";

        assertEquals(expected, result);
    }
}
