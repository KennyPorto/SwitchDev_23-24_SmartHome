package SmartHome.domain.sensors;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SensorFactoryTest {
    SensorFactory sensorFactory;
    DeviceID deviceID;

    @BeforeEach
    void setUp() {
        deviceID = mock(DeviceID.class);
        sensorFactory = new SensorFactory();
    }

    @Test
    void validCreateSensorId() {
        //Arrange
        SensorFactory sensorFactory = new SensorFactory();
        long id = 1;

        // Act
        Optional<SensorId> sensorId = sensorFactory.createSensorId(id);

        // Assert
        assertTrue(sensorId.isPresent());
    }

    @Test
    void validCreateSensorName() {
        //Arrange
        SensorFactory sensorFactory = new SensorFactory();
        String name = "Temperature Sensor";

        // Act
        Optional<SensorName> sensorName = sensorFactory.createSensorName(name);

        // Assert
        assertTrue(sensorName.isPresent());
    }

    @Test
    void validCreateSensorModel() {
        //Arrange
        SensorFactory sensorFactory = new SensorFactory();
        String model = "TemperatureSensor";

        // Act
        Optional<SensorModel> sensorModel = sensorFactory.createSensorModel(model);

        // Assert
        assertTrue(sensorModel.isPresent());
    }

    @Test
    void createSensorWithAllValidValueObjects() {
        //Arrange
        SensorModel model = new SensorModel("TemperatureSensor");
        SensorName sensorName = mock(SensorName.class);
        SensorId sensorId = mock(SensorId.class);
        SensorTypeId sensorTypeId = mock(SensorTypeId.class);

        // Act
        Sensor sensor = sensorFactory.createSensor(deviceID, model, sensorName, sensorId, sensorTypeId);

        // Assert
        assertNotNull(sensor);
    }


    @ParameterizedTest
    @NullAndEmptySource
    void invalidCreateSensorName(String name) {
        //Arrange
        SensorFactory sensorFactory = new SensorFactory();

        // Act
        Optional<SensorName> invalidSensor = sensorFactory.createSensorName(name);

        // Assert
        assertTrue(invalidSensor.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void invalidCreateSensorModel(String model) {
        //Arrange
        SensorFactory sensorFactory = new SensorFactory();

        // Act
        Optional<SensorModel> invalidSensor = sensorFactory.createSensorModel(model);

        // Assert
        assertTrue(invalidSensor.isEmpty());
    }



    @ParameterizedTest
    @ValueSource(longs = {0, - 1, Long.MIN_VALUE})
    void invalidCreateSensorId(long id) {
        //Arrange
        SensorFactory sensorFactory = new SensorFactory();

        // Act
        Optional<SensorId> invalidSensor = sensorFactory.createSensorId(id);

        // Assert
        assertTrue(invalidSensor.isEmpty());
    }

    @Test
    void invalidCreateSensorNullModel() {
        //Arrange
        SensorName sensorName = mock(SensorName.class);
        SensorId sensorID = mock(SensorId.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        SensorModel sensorModelNull = mock(SensorModel.class);
        when(sensorModelNull.toString()).thenReturn(null);

        // Act
        Sensor sensor = sensorFactory.createSensor(deviceID, sensorModelNull, sensorName, sensorID, sensorTypeIdDouble);

        // Assert
        assertNull(sensor);
    }

    @Test
    void invalidCreateSensorNullName() {
        //Arrange
        SensorModel sensorModel = mock(SensorModel.class);
        when(sensorModel.toString()).thenReturn("TemperatureSensor");
        SensorId sensorID = mock(SensorId.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);
        // Act
        Sensor sensor = sensorFactory.createSensor(deviceID, sensorModel, null, sensorID, sensorTypeIdDouble);

        // Assert
        assertNull(sensor);
    }

    @Test
    void invalidCreateSensorNullId() {
        //Arrange
        SensorModel sensorModel = mock(SensorModel.class);
        when(sensorModel.toString()).thenReturn("TemperatureSensor");
        SensorName sensorName = mock(SensorName.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);

        // Act
        Sensor sensor = sensorFactory.createSensor(deviceID, sensorModel, sensorName, null, sensorTypeIdDouble);

        // Assert
        assertNull(sensor);
    }

    @Test
    void invalidCreateSensorNullActuatorTypeName() {
        //Arrange
        SensorModel sensorModel = mock(SensorModel.class);
        when(sensorModel.toString()).thenReturn("TemperatureSensor");
        SensorName sensorName = mock(SensorName.class);
        SensorId sensorId = mock(SensorId.class);

        // Act
        Sensor sensor = sensorFactory.createSensor(deviceID, sensorModel, sensorName, sensorId, null);

        // Assert
        assertNull(sensor);
    }

    @Test
    void invalidCreateSensorNotFoundModel() {
        //Arrange
        SensorName sensorName = mock(SensorName.class);
        SensorModel sensorModel = mock(SensorModel.class);
        when(sensorModel.toString()).thenReturn("InvalidModel");
        SensorId sensorID = mock(SensorId.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);

        // Act
        Sensor sensor = sensorFactory.createSensor(deviceID, sensorModel, sensorName, sensorID, sensorTypeIdDouble);

        // Assert
        assertNull(sensor);
    }

    @Test
    void invalidCreateSensorNullDeviceId() {
        //Arrange
        SensorModel sensorModel = mock(SensorModel.class);
        when(sensorModel.toString()).thenReturn("TemperatureSensor");
        SensorName sensorName = mock(SensorName.class);
        SensorId sensorID = mock(SensorId.class);
        SensorTypeId sensorTypeIdDouble = mock(SensorTypeId.class);

        // Act
        Sensor sensor = sensorFactory.createSensor(null, sensorModel, sensorName, sensorID, sensorTypeIdDouble);

        // Assert
        assertNull(sensor);
    }
}

